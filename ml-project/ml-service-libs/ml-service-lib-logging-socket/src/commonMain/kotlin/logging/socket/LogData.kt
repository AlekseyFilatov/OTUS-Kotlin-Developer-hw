package api.kotlinproject.logging.socket

import kotlinx.serialization.Serializable
import api.kotlinproject.logging.common.LogLevel

@Serializable
data class LogData(
    val level: LogLevel,
    val message: String,
//    val data: T
)
