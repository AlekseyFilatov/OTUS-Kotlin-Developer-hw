package api.kotlinproject.mlmodel.mlxgboost

import api.kotlinproject.common.trainmodel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import ml.dmlc.xgboost4j.java.DMatrix
import ml.dmlc.xgboost4j.java.XGBoost
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.api.rows
import org.jetbrains.kotlinx.dataframe.api.select
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

class XGBoostDataFrame (): MlTrainModelBase(), ITrainModelMl {

    override suspend fun usingmodelMl(rq: TrainModelMlRequest): ITrainModelMlResponse = tryTrainModelMlMethod {
        runBlocking(Dispatchers.IO) {

            /*val df = DataFrame.readJson(
                File(
                    this::class.java.classLoader.getResource("dataForXGBoost.json")?.toURI()
                        ?: throw RuntimeException("Can't read file")
                ).absolutePath
            )*/

            val df = dataFrameOf(
                "feature1" to listOf(1.0, 2.0, 3.0, 4.0, 5.0),
                "feature2" to listOf(0.5, 1.2, 2.1, 3.0, 4.8),
                "target" to listOf(10.0, 15.0, 22.0, 28.0, 35.0)
            )

            val featuresDf = df.select { cols("feature1", "feature2") }
            val targetDf = df.select { cols("target") }
            val fdata =
                featuresDf.rows().flatMap { it.values().map { v -> (v as Number).toFloat() } }.toFloatArray()
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

            val watches = hashMapOf(
                "train" to trainDmatrix,
                "test" to trainDmatrix
            )
            val booster = XGBoost.train(trainDmatrix, params, 100, watches, null, null)
            val predictions = booster.predict(trainDmatrix)
            val outputStream = ByteArrayOutputStream()
            booster.saveModel(outputStream)

            return@runBlocking TrainModelMlResponseOk(rq.ml.copy(description = outputStream.readText()))
        }
    }

    override fun close() {

    }

    inline fun ByteArrayOutputStream.readText(charset: Charset = Charsets.UTF_8): String {
        return String(this.toByteArray(), charset)
    }
}