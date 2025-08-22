package api.kotlinproject.ml.mlspark

import api.kotlinproject.common.trainmodel.*
import api.kotlinproject.common.trainmodel.exceptions.TrainModelMlException
import ml.dmlc.xgboost4j.scala.spark.XGBoostRegressionModel
import ml.dmlc.xgboost4j.scala.spark.XGBoostRegressor
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.SparkSession
import java.io.File


class XGBoostSpark () : MlTrainModelBase(), ITrainModelMl {
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

       var spark: SparkSession = SparkSession
           .builder()
           .master("local[*]")
           .orCreate

       var result : String = ""

       override suspend fun usingmodelMl(rq: TrainModelMlRequest): ITrainModelMlResponse = tryTrainModelMlMethod {
           runCatching {
              spark = SparkSession.builder()
               .appName("XGBoostTraining ${rq.ml.id}")
               .master("local[*]")
               .getOrCreate()

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


               val labelName = "fare_amount"
               val featureColumns = arrayOf(
                   "passenger_count", "trip_distance", "pickup_longitude", "pickup_latitude",
                   "rate_code", "dropoff_longitude", "dropoff_latitude", "hour", "day_of_week", "is_weekend", "h_distance"
               )

               val assembler = VectorAssembler()
                   .setInputCols(arrayOf("passenger_count", "trip_distance", "pickup_longitude", "pickup_latitude", "rate_code", "dropoff_longitude", "dropoff_latitude", "hour", "day_of_week", "is_weekend", "h_distance"))
                   .setOutputCol("features")
               val assembled_df = assembler.transform(train_df)

               val map :Map<String, Any> = HashMap<String, Any>()

               val regressor = XGBoostRegressor()


               val model: XGBoostRegressionModel = regressor.fit(assembled_df)

               result = model.transform(test_df)?.toJSON().toString()
               /*val map: Map<String, Any> = HashMap()
                map.plus( "learning_rate" to 0.05)
                map.plus("max_depth" to 8)
                map.plus("subsample" to 0.8)
                map.plus("gamma" to 1)
                map.plus("num_round" to 10)
                   //.updated("tree_method", "gpu_hist")
                map.plus("num_workers" to 2)
                map.plus("features_col" to featureColumns)
                map.plus("label_col" to labelName)*/
               /*val map: HashMap<String?, Any?>? = HashMap<String, Any>()
                   .updated("learning_rate", 0.05)
                   .updated("max_depth", 8)
                   .updated("subsample", 0.8)
                   .updated("gamma", 1)
                   .updated("num_round", 10)
                  // .updated("tree_method", 2)

               val regressor = XGBoostRegressor(map)
               regressor.setLabelCol(labelName)
               regressor.setFeaturesCol(featureColumns)

               val model: PredictionModel<Vector, XGBoostRegressionModel> = regressor.fit(train_df)
               val predict_df = model.transform(test_df)

               result = predict_df
                   .withColumn("error", col("prediction")
                       .minus(col(labelName)))
                   .toJSON().toString()*/

               // result.select(labelName, "prediction", "error").show()
               // result.describe(labelName, "prediction", "error").show()
            }.fold(
                onFailure = { exception ->
                        spark.close()
                        throw TrainModelMlException(
                            msg = exception.message?.takeIf { it.isNotEmpty() } ?: "XGBoostSpark error using model",
                            mlId = rq.ml.id
                        )
                },
                onSuccess = {
                       spark.close()
                       TrainModelMlResponseOk(
                        rq.ml.copy(
                            description = result
                            )
                       )
                   }
                )
           }

      /* private fun xgBoostRegressor(modelParams: Map<String?, Any?>?, labelName: String, featureColumns: Array<String>): XGBoostRegressor {
           val regressor = XGBoostRegressor(modelParams)
           regressor.setLabelCol(labelName)
           regressor.setFeaturesCol(featureColumns)
           return regressor
       }*/

       /*private fun createModelParams(modelParameters: MdlMlModelParameters): HashMap<String, Any> {
           val paramsMap: HashMap<String, Any> = HashMap<String, Any>()
               .updated("learning_rate", modelParameters.learningRate)
               .updated("max_depth", modelParameters.maxDepth)
               .updated("subsample", modelParameters.subSample)
               .updated("gamma", modelParameters.gamma)
               .updated("num_round", modelParameters.numRound)
               .updated("tree_method", modelParameters.treeMethod)
               .updated("refresh_leaf", modelParameters.refreshLeaf)
               .updated("process_type", modelParameters.processType)
           if (modelParameters.updater != null) {
               paramsMap?.updated("updater", modelParameters.updater)
           }
           return paramsMap
       }*/

       override fun close() {
           spark.close()
       }
   }

