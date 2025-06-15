package api.kotlinproject.mappers.v1.jacksonmappers.exceptions

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Class $clazz cannot be mapped to MdlContext")
