package api.kotlinproject.common.stubs

import api.kotlinproject.common.models.MdlMlTrainResult

object MdlMlTrainResultStubs {
  val ML_TrainResult: MdlMlTrainResult
    get() = MdlMlTrainResult(
      dateTime = "1900-01-01",
      close = 0.1,
      labelDatetime = "2025-01-01",
      realResult = 0.1,
      prediction = 0.1,
      error = 0.1
    )
}

