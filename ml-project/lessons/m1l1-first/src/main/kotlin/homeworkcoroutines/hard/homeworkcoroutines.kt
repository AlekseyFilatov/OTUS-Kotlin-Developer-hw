package api.kotlinproject.homeworkcoroutines.hard


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.awaitAll

suspend fun <T, V> Iterable<T>.parMap(func: suspend (T) -> V): Iterable<V> =
    coroutineScope {
        map { element ->
            async(Dispatchers.IO) { func(element) }
        }.awaitAll()
    }
