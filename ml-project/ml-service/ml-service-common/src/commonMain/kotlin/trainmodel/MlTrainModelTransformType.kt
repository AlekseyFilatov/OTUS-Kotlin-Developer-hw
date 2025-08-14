package api.kotlinproject.common.trainmodel

enum class MlTrainModelTransformType(val modelName: String) {
    XGBoost("xgboost"), FOREST("forest"), RAPIDS("rapids")
}