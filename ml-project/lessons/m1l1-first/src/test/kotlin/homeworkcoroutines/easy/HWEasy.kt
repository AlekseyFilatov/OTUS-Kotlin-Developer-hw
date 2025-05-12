package homeworkcoroutines.easy

import api.kotlinproject.homeworkcoroutines.hard.parMap
import kotlinx.coroutines.*
import kotlin.test.Test
import kotlin.time.measureTime

class HWEasy {

    @Test
    fun easyHw() :Unit = runBlocking(Dispatchers.IO) {
        launch {
            val numbers = generateNumbers()
            val toFind = 10
            val toFindOther = 1000

            val foundNumbers = listOf(
                findNumberInList(toFind, numbers),
                findNumberInList(toFindOther, numbers)
            )


            coroutineScope {
                val elapsed1 = measureTime {
                    foundNumbers.forEach {
                        if (it != -1) {
                            //delay(10)
                            println("Your number $it found!")
                        } else {
                            //delay(10)
                            println("Not found number $toFind || $toFindOther")
                        }
                    }
                }

                val elapsed2 = measureTime {
                    foundNumbers.parMap {
                        if (it != -1) {
                            //delay(10)
                            println("Your number $it found!")
                        } else {
                            //delay(10)
                            println("Not found number $toFind || $toFindOther")
                        }
                    }
                }


                println(
                    "First test: Time elapsed: ${elapsed1.inWholeMilliseconds} milliseconds ($elapsed1); " +
                            "Second test: Time elapsed: ${elapsed2.inWholeMilliseconds} milliseconds ($elapsed2)"
                )
            }
        }
    }
}