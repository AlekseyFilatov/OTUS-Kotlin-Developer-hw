package api.kotlinproject.ml.mlspark

import api.kotlinproject.common.models.MdlMlModelParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import ml.dmlc.xgboost4j.scala.spark.XGBoostRegressor
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import scala.collection.immutable.HashMap
import scala.collection.immutable.Map
import java.io.File


class XGBoostSpark (
    private val trainFile: String = "train.parquet",
    private val modelParams: MdlMlModelParameters = MdlMlModelParameters()
) : MlTrainModelBase(), ITrainModelMl {
   {
       //https://habr.com/ru/companies/otus/articles/508024/
       //https://github.com/dmlc/xgboost/blob/master/doc/tutorials/spark_estimator.rst
       /*
            Extreme Gradient Boosting или экстремальный градиентный бустинг.
            Это своего рода градиентный бустинг на стероидах,
             который используется в основном для классификации,
              но также порой для регрессии и ранжирования.
            По сравнению со стандартным градиентным бустингом,
             новый метод существенно увеличивает производительность за счет
              гиперпараметров, поддержки GPU, кроссвалидации
               и регуляризации алгоритмов.
            В целом модель получается более эффективной,
             быстрее обучается и менее подвержена переобучению.
       */

       private val spark: SparkSession = SparkSession.builder()
                               .appName("XGBoostTraining")
                               .master("local[*]")
                               .getOrCreate()

       fun xgBoost() :Unit = runBlocking(Dispatchers.IO) {
           runCatching {
               val trainPath = File(
                   this::class.java.classLoader.getResource("train.parquet")?.toURI()
                       ?: throw RuntimeException("Can't read file")
               ).absolutePath

               val evalPath = File(
                   this::class.java.classLoader.getResource("eval.parquet")?.toURI()
                       ?: throw RuntimeException("Can't read file")
               ).absolutePath


               val train_df = spark.read().parquet(trainPath)
               val test_df = spark.read().parquet(evalPath)


               val featureColumns = arrayOf("fare_amount","feature1","feature2")

               val labelName = "fare_amount"
               val featureColumns1 = arrayOf(
                   "passenger_count",
                   "trip_distance",
                   "pickup_longitude",
                   "pickup_latitude",
                   "rate_code",
                   "dropoff_longitude",
                   "dropoff_latitude",
                   "hour",
                   "day_of_week",
                   "is_weekend",
                   "h_distance"
               )

               val map: Map<String, Any> = HashMap<String, Any>()
                   .updated("learning_rate", 0.05)
                   .updated("max_depth", 8)
                   .updated("subsample", 0.8)
                   .updated("gamma", 1)
                   .updated("num_round", 10)
                   //.updated("tree_method", "gpu_hist")
                   .updated("num_workers", 2)
                   .updated("features_col", featureColumns)
                   .updated("label_col", labelName)


               val regressor = XGBoostRegressor(map)

               val assembler = VectorAssembler()
                   .setInputCols(featureColumns)
                   .setOutputCol("features")
               val assembledDf = assembler.transform(train_df)

               val model = regressor.fit(assembledDf)
               val predict_df = model.transform(test_df)
               val result = predict_df.withColumn("error", col("prediction").minus(col(labelName)))
               result.select(labelName, "prediction", "error").show()
               result.describe(labelName, "prediction", "error").show()
              //model.save(this::class.java.classLoader.getResource("")?.toURI().toString())
           }.getOrThrow()
       }

       private fun xgBoostRegressor(modelParams: Map<String, Any>, labelName: String, featureColumns: Array<String>): XGBoostRegressor {
           val regressor = XGBoostRegressor(modelParams)
           regressor.setLabelCol(labelName)
           regressor.setFeaturesCol(featureColumns)
           return regressor
       }

       private fun createModelParams(modelParameters: MdlMlModelParameters): Map<String, Any> {
           val paramsMap: Map<String, Any> = HashMap<String, Any>()
               .updated("learning_rate", modelParameters.learningRate)
               .updated("max_depth", modelParameters.maxDepth)
               .updated("subsample", modelParameters.subSample)
               .updated("gamma", modelParameters.gamma)
               .updated("num_round", modelParameters.numRound)
               .updated("tree_method", modelParameters.treeMethod)
               .updated("refresh_leaf", modelParameters.refreshLeaf)
               .updated("process_type", modelParameters.processType)
           if (modelParameters.updater != null) {
               paramsMap.updated("updater", modelParameters.updater)
           }
           return paramsMap
       }

       override fun close() {
           spark.close()
       }
   }
