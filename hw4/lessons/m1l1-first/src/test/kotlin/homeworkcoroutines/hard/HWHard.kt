package homeworkcoroutines.hard

import api.kotlinproject.homeworkcoroutines.hard.parMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.test.Test

class HWHard {
    @Test
    fun hardHw()  :Unit = runBlocking(Dispatchers.IO) {
        launch {
            val dictionaryApi = DictionaryApi()
            val words = FileReader.readFile().split(" ", "\n").toSet()


            coroutineScope {
                words.toList().forEach{ word ->

                    val setDictionary :Set<String> = setOf(word)
                    val dictionaries = findWords(dictionaryApi, setDictionary, Locale.EN)

                    dictionaries.filterNotNull().map { dictionary ->
                        print("For word ${dictionary.word} i found examples: ")
                        println(
                            dictionary.meanings
                                .mapNotNull { definition ->
                                    val r = definition.definitions
                                        .mapNotNull { it.example.takeIf { it?.isNotBlank() == true } }
                                        .takeIf { it.isNotEmpty() }
                                    r
                                }
                                .takeIf { it.isNotEmpty() }
                            )
                        }
                }
            }
        }
    }

    private suspend fun findWords(
        dictionaryApi: DictionaryApi,
        words: Set<String>,
        @Suppress("SameParameterValue") locale: Locale
    ) =
        // make some suspensions and async
        words.parMap {
            dictionaryApi.findWord(locale, it)
        }

    object FileReader {
        fun readFile(): String =
            File(
                this::class.java.classLoader.getResource("words.txt")?.toURI()
                    ?: throw RuntimeException("Can't read file")
            ).readText()
    }
}