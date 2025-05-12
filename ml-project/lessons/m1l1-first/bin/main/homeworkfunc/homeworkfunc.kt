package homeworkfunc

/*val input = listOf(
    mapOf(
        "first" to "Иван",
        "middle" to "Васильевич",
        "last" to "Рюрикович",
    ),
    mapOf(
        "first" to "Петька",
    ),
    mapOf(
        "first" to "Сергей",
        "last" to "Королев",
    ),
)*/

fun mapListToNames(input: List<Map<String, String>>): List<String> {
    return input.map { rec -> rec.let {
        mutableListOf<String>().apply {
            rec["last"]?.let { add(it) }
            rec["first"]?.let { add(it) }
            rec["middle"]?.let { add(it) }
        }.joinToString ( separator = " " )
    }
    }.toList()
}

/*fun main() {
   println( input.map { rec -> rec.let {
           mutableListOf<String>().apply {
               rec["last"]?.let { add(it) }
               rec["first"]?.let { add(it) }
               rec["middle"]?.let { add(it) }
           }.joinToString ( separator = " " )
       }
   }.toList())

}*/