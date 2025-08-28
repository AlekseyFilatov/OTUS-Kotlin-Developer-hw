package api.kotlinproject.mlmodel.mlxgboost

import api.kotlinproject.common.models.MdlMlTrainResult
import api.kotlinproject.common.trainmodel.*
import ml.dmlc.xgboost4j.java.DMatrix
import ml.dmlc.xgboost4j.java.XGBoost
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.rows
import org.jetbrains.kotlinx.dataframe.api.select
import org.jetbrains.kotlinx.dataframe.io.readJson
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.charset.Charset

class XGBoostDataFrameTrainResult (): MlTrainModelTrainResultBase(), ITrainModelMlTrainResult {

    override suspend fun usingmodelMl(rq: TrainModelMlTrainResultRequest): ITrainModelMlTrainResultResponse = tryTrainModelMlTrainResultMethod {

            val df = DataFrame.readJson(
                File(
                    this::class.java.classLoader.getResource("dataForXGBoost.json")?.toURI()
                        ?: throw RuntimeException("Can't read file")
                ).absolutePath
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

        return@tryTrainModelMlTrainResultMethod TrainModelMlTrainResultResponseOk(MdlMlTrainResult(id = rq.ml.id ))
        }

    override fun close() {

    }

    inline fun ByteArrayOutputStream.readText(charset: Charset = Charsets.UTF_8): String {
        return String(this.toByteArray(), charset)
    }
}