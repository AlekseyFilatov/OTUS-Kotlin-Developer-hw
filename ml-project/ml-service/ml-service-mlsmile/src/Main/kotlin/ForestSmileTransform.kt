package api.kotlinproject.mlmodel.mlxgboost


import api.kotlinproject.common.models.MdlMlTransform
import api.kotlinproject.common.trainmodel.*
import smile.data.formula.Formula
import smile.regression.RandomForest
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.ObjectOutputStream
import java.nio.charset.Charset

class ForestSmileTransform (): MlTrainModelTransformBase(), ITrainModelMlTransform {

    override suspend fun usingmodelMl(rq: TrainModelMlTransformRequest): ITrainModelMlTransformResponse = tryTrainModelMlTransformMethod {

        val df = smile.io.Read.arff(File(
            this::class.java.classLoader.getResource("iris.arff")?.toURI()
                ?: throw RuntimeException("Can't read file")
        ).absolutePath)

        val formula = Formula.lhs("class")
        val model = RandomForest.fit(formula, df)
        val outputStream = ByteArrayOutputStream()
        ObjectOutputStream(outputStream).use { oos ->
            oos.writeObject(model)
        }

            TrainModelMlTransformResponseOk(MdlMlTransform(id = rq.ml.id))
        }

    override fun close() {

    }

    inline fun ByteArrayOutputStream.readText(charset: Charset = Charsets.UTF_8): String {
        return String(this.toByteArray(), charset)
    }
}