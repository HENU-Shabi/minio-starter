package xyz.luchengeng.minio.starter

import io.minio.ServerSideEncryption

class MinIOItem(
    val obj: Any,
    var headerMap: MutableMap<String, String>? = null,
    var sse: ServerSideEncryption? = null,
    var contentType: String? = null
) {
    operator fun component1() = obj
    operator fun component2() = headerMap
    operator fun component3() = contentType
}

infix fun Any.withHeader(headerMap: MutableMap<String, String>): MinIOItem {
    return if (this is MinIOItem) {
        this.headerMap = headerMap
        this
    } else {
        MinIOItem(this, headerMap)
    }
}

infix fun Any.withContentType(contentType: String): MinIOItem {
    return if (this is MinIOItem) {
        this.contentType = contentType
        this
    } else {
        MinIOItem(this, contentType = contentType)
    }
}

infix fun Any.withEncryption(sse: ServerSideEncryption): MinIOItem {
    return if (this is MinIOItem) {
        this.sse = sse
        this
    } else {
        MinIOItem(this, sse = sse)
    }
}