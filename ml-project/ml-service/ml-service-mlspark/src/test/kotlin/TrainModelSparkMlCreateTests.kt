package api.kotlinproject.ml.mlspark

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.trainmodel.ITrainModelMl
import api.kotlinproject.common.trainmodel.TrainModelMlRequest
import api.kotlinproject.common.trainmodel.TrainModelMlResponseOk
import api.kotlinproject.mlmodel.mlxgboost.BaseTrainModelInitMls
import api.kotlinproject.mlmodel.mlxgboost.runMlModelTest
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotEquals

abstract class TrainModelMlSparkCreateTest {
    abstract val model: ITrainModelMl
    protected open val uuidNew = MdlMlId("10000000-0000-0000-0000-000000000001")

    private val createObj = MdlMl(
        title = "rapids",
        description = "create model rapids",
        id = uuidNew
    )

    //@Test
    fun createSuccess() = runMlModelTest {
        val result = model.usingmodelMl(TrainModelMlRequest(createObj))
        val expected = createObj
        assertIs<TrainModelMlResponseOk>(result)
        assertEquals(uuidNew, result.data.id)
        assertEquals(expected.title, result.data.title)
        //assertEquals(true, result.data.description.contains("learn"))
        assertNotEquals(MdlMlId.Companion.NONE, result.data.id)
    }

    companion object : BaseTrainModelInitMls("create") {
        override val initObjects: List<MdlMl> = emptyList()
    }
}

class MlTrainModelCreateTest : TrainModelMlSparkCreateTest() {
    override val model = XGBoostSpark()
}



    /*@Test
    fun testXGBoost() {
         val spark = SparkSession.builder()
                .appName("XGBoostTraining")
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


            val tdf = spark.read().parquet(trainPath)
            val edf = spark.read().parquet(evalPath)

            val labelName = "fare_amount"
            val featureColumns = arrayOf(
                "passenger_count", "trip_distance", "pickup_longitude", "pickup_latitude",
                "rate_code", "dropoff_longitude", "dropoff_latitude", "hour", "day_of_week", "is_weekend", "h_distance"
            )

            //val featureColumns = arrayOf("vendor_id", "passenger_count", "trip_distance", "pickup_longitude", "pickup_latitude", "rate_code", "store_and_fwd", "dropoff_longitude", "dropoff_latitude", "fare_amount", "hour", "year", "month", "day", "day_of_week", "is_weekend", "h_distance" )

            val map: Map<String, Any> = HashMap<String, Any>()
                .updated("learning_rate", 0.05)
                .updated("max_depth", 8)
                .updated("subsample", 0.8)
                .updated("gamma", 1)
                .updated("num_round", 500)
                //.updated("tree_method", "gpu_hist")
                .updated("num_workers", 1)

            val regressor = XGBoostRegressor(map)
            regressor.setLabelCol(labelName)
            regressor.setFeaturesCol(featureColumns)

        val schema = StructType(
            arrayOf(
                StructField("feature1", DoubleType, true, null),
                StructField("feature2", DoubleType, true, null),
                StructField("label", DoubleType, true, null)
            )
        )





        val assembler = VectorAssembler()
            .setInputCols(featureColumns)
            .setOutputCol("features")


       val assembledData = assembler.transform(tdf).select("features", "fare_amount")
      // val lr = LogisticRegression(labelCol = "label", featuresCol = "features")
      // val model = lr.fit(assembled_df)


        val model = regressor.fit(assembledData)
        val predictions = model.transform(assembledData)
        // val predictions = assembler.transform(edf)

            val result = predictions
                .withColumn("error", org.apache.spark.sql.Column("prediction").minus(org.apache.spark.sql.Column(labelName)))
            result.select(labelName, "prediction", "error").show()
            result.describe(labelName, "prediction", "error").show()
        }*/

    /*@Test
    fun dataframeTest() {

        val df1 = DataFrame.read(
            File(
                this::class.java.classLoader.getResource("train.csv")?.toURI()
                    ?: throw RuntimeException("Can't read file")
            ).absolutePath)
            //"C:/Users/AlexFil/IdeaProjects/kotlinproject_test/kotlinproject/ml-service/ml-service-ml/src/test/resources/train.csv")

        // You can then work with the loaded DataFrame
        println(df1.schema()) // Print the schema of the DataFrame
        println(df1.head(5))



        val df = dataFrameOf(
            "feature1" to listOf(1.0, 2.0, 3.0, 4.0, 5.0),
            "feature2" to listOf(0.5, 1.2, 2.1, 3.0, 4.8),
            "target" to listOf(10.0, 15.0, 22.0, 28.0, 35.0)
        )

        val featuresDf = df.select { cols("feature1", "feature2") }
        val targetDf = df.select { cols("target") }
        val fdata = featuresDf.rows().flatMap { it.values().map { v -> (v as Number).toFloat() } }.toFloatArray()
        val tdata = targetDf.rows().map { (it[0] as Number).toFloat() }.toFloatArray()
        val numRows = featuresDf.rowsCount()
        val numCols = featuresDf.columnsCount()
        val trainDmatrix = DMatrix(fdata, numRows, numCols)
        trainDmatrix.label = tdata
        val params = HashMap<String, Any>()
        params["objective"] = "reg:squarederror"
        params["eta"] = 0.1
        params["max_depth"] = 3
        params["eval_metric"] = "rmse"
        //val model = XGBoostClassifier(params)
        val watches = hashMapOf(
            "train" to trainDmatrix,
            "test" to trainDmatrix
        )
        val booster = XGBoost.train(trainDmatrix, params, 100, watches, null, null)
        val predictions = booster.predict(trainDmatrix)


        booster.saveModel("C:/Users/AlexFil/IdeaProjects/kotlinproject_test/kotlinproject/ml-service/ml-service-ml/src/test/resources/savemode.model")
        val loadBooster = XGBoost.loadModel("C:/Users/AlexFil/IdeaProjects/kotlinproject_test/kotlinproject/ml-service/ml-service-ml/src/test/resources/savemode.model")
        val outputStream = ByteArrayOutputStream()
        booster.saveModel(outputStream)
        val modelBytes = outputStream.toByteArray()
        val inputStream = ByteArrayInputStream(modelBytes)
        val loadModelByte = XGBoost.loadModel(inputStream)



        // Example data (replace with your actual data)
        val features = arrayOf(
            floatArrayOf(1.0f, 2.0f),
            floatArrayOf(3.0f, 4.0f)
        )
        val labels = floatArrayOf(0.0f, 1.0f)


        try {
            // Create DMatrix from dense arrays
            //val dMatrix = DMatrix(features, labels)

            // You can also create from a file:
            // val dMatrixFromFile = DMatrix("path/to/your/data.libsvm")
            val dMatrixFromFile =
                DMatrix("""C:/Users/AlexFil/IdeaProjects/kotlinproject_test/kotlinproject/ml-service/ml-service-ml/src/test/resources/train.csv""")
            // Use dMatrix for training your XGBoost model
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // Extract features and target as arrays
        //val f = df.getColumns { colsOf<Double>() }
        //val features = df["feature1"]["feature2"] //.map { it.values() }.toTypedArray()
        //val target = df["target"].cast<Double>().toDoubleArray()
        //features.forEach { println(it.toString()) }
        // Flatten features array to a 1D array for DMatrix
        /*val flatFeatures = features.flatMap { it. }.toFloatArray()
        val numRows = df.rowsCount()
        val numCols = features.size // Number of feature columns

        // 2. Create DMatrix for XGBoost

        val dMatrix = DMatrix(flatFeatures, numRows, numCols)
        dMatrix.label = target.map { it.toFloat() }.toFloatArray()

        // 3. Set XGBoost parameters
        val params = hashMapOf<String, Any>(
            "objective" to "reg:squarederror",
            "eta" to 0.1,
            "max_depth" to 3
        )

        // 4. Train the XGBoost model
        val numBoostRounds = 10
        val model = XGBoost.train(dMatrix, params, numBoostRounds, null, null)

        // 5. Make predictions (example with the same data for simplicity)
        val predictions = model.predict(dMatrix)

        // Print some predictions
        println("Predictions:")
        predictions.forEachIndexed { index, predArray ->
            println("Row ${index + 1}: ${predArray[0]}")
        }*/
    }

    */
        /*val tdf = session.read().parquet(trainPath)
        val edf = session.read().parquet(evalPath)

        val labelName = "fare_amount"
        val featureColumns = arrayOf(
            "passenger_count", "trip_distance", "pickup_longitude", "pickup_latitude",
            "rate_code", "dropoff_longitude", "dropoff_latitude", "hour", "day_of_week", "is_weekend", "h_distance"
        )

        val map: Map<String, Any> = HashMap<String, Any>()
            .updated("learning_rate", 0.05)
            .updated("max_depth", 8)
            .updated("subsample", 0.8)
            .updated("gamma", 1)
            .updated("num_round", 500)
            .updated("tree_method", "gpu_hist")
            .updated("num_workers", sparkSettings.executorsNum)

        val regressor = (map)
        regressor.setLabelCol(labelName)
        regressor.setFeaturesCol(featureColumns)

        val model: PredictionModel<Vector, XGBoostRegressionModel> = regressor.fit(tdf)
        val predictions = model.transform(edf)

        val result = predictions.withColumn("error", col("prediction").minus(col(labelName)))
        result.select(labelName, "prediction", "error").show()
        result.describe(labelName, "prediction", "error").show()
    }*/
//}
