package homeworkoop

class Square(val width: Int): Figure {

    override fun area(): Int = width*width
    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is Square -> false
        other::class.java == Square::class.java -> width == other.width
        else -> false
    }

    override fun hashCode(): Int {
        return width
    }


}