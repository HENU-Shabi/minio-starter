package xyz.luchengeng.minio.starter
import io.minio.ErrorCode
import io.minio.MinioClient
import io.minio.errors.ErrorResponseException
import io.minio.messages.Item
import java.io.*

class MinIOClient(private val client : MinioClient, private val props : MinIOProperties) {
    private val raw: MinioClient
        get() = client
    private val bucket: String
        get() = props.bucket
    val objectList: List<Item>
        get() = raw.listObjects(bucket).map { it.get() }

    operator fun set(id: String, obj: Any) {
        if (obj is Unit) {
            if (this.objectExists(id)) client.removeObject(props.bucket, id)
            return
        }
        if (this.objectExists(id)) {
            client.removeObject(props.bucket, id)
        }
        if (obj is MinIOItem) {

            val stream = serialize(obj.obj)
            client.putObject(
                props.bucket,
                id,
                stream,
                stream.available().toLong(),
                obj.headerMap,
                null,
                obj.contentType
                )
        } else {
            val stream = serialize(obj)
            client.putObject(
                props.bucket,
                id,
                stream,
                stream.available().toLong(),
                "application/octet-stream"
            )
        }
    }

    private fun objectExists(id: String): Boolean {
        return try {
            client.statObject(props.bucket, id)
            true
        } catch (e: ErrorResponseException) {
            //potential bug
            if (e.errorResponse().errorCode().code() != ErrorCode.NO_SUCH_OBJECT.code()) {
                throw e
            }
            false
        }
    }

    operator fun get(id: String): Triple<Any, Map<String, List<String>>, String> {
        return try {
            val stat = client.statObject(props.bucket, id)
            Triple(
                ObjectInputStream(client.getObject(props.bucket, id)).readObject(),
                stat.httpHeaders(),
                stat.contentType()
            )
        } catch (e: ErrorResponseException) {
            if (e.errorResponse().errorCode().code() != ErrorCode.NO_SUCH_OBJECT.code()) {
                throw e
            }
            Triple(
                Unit,
                mapOf(),
                "application/octet-stream"
            )
        }
    }
}

fun serialize(obj: Any): InputStream {
    val baos = ByteArrayOutputStream()
    val oos = ObjectOutputStream(baos)
    oos.writeObject(obj)
    oos.flush()
    oos.close()
    return ByteArrayInputStream(baos.toByteArray())
}