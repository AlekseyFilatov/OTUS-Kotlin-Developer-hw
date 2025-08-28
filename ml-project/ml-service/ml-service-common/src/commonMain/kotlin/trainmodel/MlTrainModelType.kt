package api.kotlinproject.common.trainmodel

enum class MlTrainModelType(val modelName: String) {
    XGBoost("xgboost"), FOREST("forrest"), RAPIDS("rapids"), STUB("stub")
}