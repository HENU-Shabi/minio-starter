package xyz.luchengeng.minio.starter

class MinIOObject(
    val obj: Any,
    var headerMap: MutableMap<String, String>? = null,
    var contentType: String? = null
) {
    operator fun component1() = obj
    operator fun component2() = headerMap
    operator fun component3() = contentType
}

infix fun Any.withHeader(headerMap: MutableMap<String, String>): MinIOObject {
    return if (this is MinIOObject) {
        this.headerMap = headerMap
        this
    } else {
        MinIOObject(this, headerMap)
    }
}

infix fun Any.withContentType(contentType: String): MinIOObject {
    return if (this is MinIOObject) {
        this.contentType = contentType
        this
    } else {
        MinIOObject(this, contentType = contentType)
    }
}