package api.kotlinproject.common.trainmodel

enum class MlTrainModelTrainResultType(val modelName: String) {
    XGBoost("xgboost"), FOREST("forest"), RAPIDS("rapids")
}