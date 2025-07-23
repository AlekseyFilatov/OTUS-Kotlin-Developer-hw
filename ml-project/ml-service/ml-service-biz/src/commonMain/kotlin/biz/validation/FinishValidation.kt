package api.kotlinproject.biz.validation

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.finishMlValidation(title: String) = worker {
    this.title = title
    on { state == MdlState.RUNNING }
    handle {
        mlValidated = mlValidating
    }
}

fun ICorChainDsl<MdlContext>.finishMlValidationTransform(title: String) = worker {
    this.title = title
    on { state == MdlState.RUNNING }
    handle {
        mlValidatedTransform = mlTransformValidating
    }
}

fun ICorChainDsl<MdlContext>.finishMlValidationAnalytic(title: String) = worker {
    this.title = title
    on { state == MdlState.RUNNING }
    handle {
        mlResponseValidatedTrainModel = mlTrainResultValidating
    }
}

fun ICorChainDsl<MdlContext>.finishMlFilterValidation(title: String) = worker {
    this.title = title
    on { state == MdlState.RUNNING }
    handle {
        mlFilterValidated = mlFilterValidating
    }
}
