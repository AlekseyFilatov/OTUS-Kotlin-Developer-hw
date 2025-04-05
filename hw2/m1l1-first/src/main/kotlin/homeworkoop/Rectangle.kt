package homeworkoop

class Rectangle(val width: Int, val height: Int): Figure {
    override fun area(): Int = width*height

    override fun toString(): String {
        return "Rectangle(${width}x${height})"
    }

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is Rectangle -> false
        other::class.java == Rectangle::class.java -> width == other.width && height == other.height
        else -> false
    }

    override fun hashCode(): Int {
        val prime = 41
        return prime * (prime + width.hashCode()) + height.hashCode()
    }


}