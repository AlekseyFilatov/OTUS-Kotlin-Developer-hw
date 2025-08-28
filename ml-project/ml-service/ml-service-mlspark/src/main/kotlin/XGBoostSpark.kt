package api.kotlinproject.ml.mlspark

import api.kotlinproject.common.trainmodel.*
import api.kotlinproject.common.trainmodel.exceptions.TrainModelMlException
import com.esotericsoftware.kryo.Kryo
import ml.dmlc.xgboost4j.scala.spark.XGBoostClassifier
import org.apache.spark.SparkConf
import org.apache.spark.serializer.KryoRegistrator
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.DataTypes
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType
import org.json4s.CustomSerializer
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.ObjectOutputStream
import java.io.Serializable
import java.nio.charset.Charset


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
           //.config("spark.kryo.registrationRequired","false")
           //.config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
           //.config("spark.kryo.registrator", "api.kotlinproject.ml.mlspark.MyKryoRegistrator")
           .orCreate

       var result : String = ""

       override suspend fun usingmodelMl(rq: TrainModelMlRequest): ITrainModelMlResponse = tryTrainModelMlMethod {
           runCatching {
               val conf = SparkConf()
               //conf.set("spark.kryo.registrationRequired","false")
                   .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")

                   //.set("spark.kryo.registrator", "api.kotlinproject.ml.mlspark.MyKryoRegistrator")

              spark = SparkSession.builder()
               .appName("XGBoostTraining ${rq.ml.id}")
               .master("local[*]")
               //.config(conf)
               //.config("spark.kryo.registrationRequired","false")
               //.config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
               //.config("spark.kryo.registrator", "api.kotlinproject.ml.mlspark.MyKryoRegistrator")
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

               val schema = StructType(arrayOf(
                       StructField("feature1", DataTypes.DoubleType, true, org.apache.spark.sql.types.Metadata.empty()),
                       StructField("feature2", DataTypes.DoubleType, true, org.apache.spark.sql.types.Metadata.empty()),
                       StructField("label", DataTypes.DoubleType, true, org.apache.spark.sql.types.Metadata.empty())
                   )
               )

               val data = listOf(
                   org.apache.spark.sql.RowFactory.create(1.0, 2.0, 0.0),
                   org.apache.spark.sql.RowFactory.create(3.0, 4.0, 1.0),
                   org.apache.spark.sql.RowFactory.create(5.0, 6.0, 0.0)
               )

               val df = spark.createDataFrame(data, schema)

               val schema1 = StructType(arrayOf(
                   StructField("sepal_length", DataTypes.DoubleType, true, org.apache.spark.sql.types.Metadata.empty()),
                   StructField("sepal_width", DataTypes.DoubleType, true,  org.apache.spark.sql.types.Metadata.empty()),
                   StructField("petal_length", DataTypes.DoubleType, true, org.apache.spark.sql.types.Metadata.empty()),
                   StructField("petal_width", DataTypes.DoubleType, true, org.apache.spark.sql.types.Metadata.empty()),
                   StructField("class", DataTypes.StringType, true, org.apache.spark.sql.types.Metadata.empty())
               ))

               val rawData = spark.read()
                   .option("header", "false") // If no header in CSV
                   .schema(schema1)
                   .csv(File(
                       this::class.java.classLoader.getResource("iris.csv")?.toURI()
                           ?: throw RuntimeException("Can't read file")
                   ).absolutePath)

               val indexer = org.apache.spark.ml.feature.StringIndexer()
                   .setInputCol("class")
                   .setOutputCol("label")
                   .fit(rawData)

               //val indexedData = indexer.transform(rawData)

               val featureCols = arrayOf("sepal_length", "sepal_width", "petal_length", "petal_width")




              /* val assembler = VectorAssembler()
                   .setInputCols(featureCols)
                   .setOutputCol("class")

               val xgbInput = assembler.transform(rawData)*/
               //val assembledData = assembler.transform(df)
               val xgbParam :Map<String, Any> = mapOf(
                   "num_round" to 100,
                   "objective" to "binary:logistic",
                   "num_workers" to 1 // Adjust based on your cluster setup
               )

               val xgbClassifier = XGBoostClassifier()
                   .setFeaturesCol("class")
                   .setLabelCol("label")
                   .setNumWorkers(2)
                   //.setObjective("binary:logistic")

               val model = xgbClassifier.train(rawData)
               val outputStream = ByteArrayOutputStream()
               ObjectOutputStream(outputStream).use { oos ->
                   oos.writeObject(model)
               }

               return@tryTrainModelMlMethod TrainModelMlResponseOk(rq.ml.copy(description = outputStream.readText()))


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

    inline fun ByteArrayOutputStream.readText(charset: Charset = Charsets.UTF_8): String {
        return String(this.toByteArray(), charset)
    }
   }

class MyKryoRegistrator : KryoRegistrator, Serializable {
    override fun registerClasses(kryo: Kryo) {
        // Product POJO associated to a product Row from the DataFrame
        kryo.register(CustomSerializer::class.java)
    }
}