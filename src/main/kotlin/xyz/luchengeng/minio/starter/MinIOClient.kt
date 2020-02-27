package xyz.luchengeng.minio.starter
import io.minio.ErrorCode
import io.minio.MinioClient
import io.minio.ServerSideEncryption
import io.minio.errors.ErrorResponseException
import org.apache.http.entity.ContentType
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

class MinIOClient(private val client : MinioClient, private val props : MinIOProperties) {
    val raw : MinioClient
        get() = client
    val bucket : String
        get() = props.bucket
    operator fun set(id : String,obj : Any){
        if(obj is Unit){
            client.removeObject(props.bucket,id)
            return
        }
        if(this.objectExists(id)){
            client.removeObject(props.bucket,id)
        }
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(obj)
        oos.flush()
        oos.close()
        val inputStream = ByteArrayInputStream(baos.toByteArray())
        client.putObject(props.bucket,id,inputStream,inputStream.available().toLong(),"application/octet-stream")
    }

    private fun objectExists(id : String) : Boolean{
        return try{
            client.statObject(props.bucket,id)
            true
        }catch (e : ErrorResponseException){
            //potential bug
            if (e.errorResponse().errorCode().code() != ErrorCode.NO_SUCH_OBJECT.code()) {
                throw e
            }
            false
        }
    }

    operator fun get(id : String) : Any{
        if(!this.objectExists(id)) return Unit
        return ObjectInputStream(client.getObject(props.bucket,id)).readObject()
    }
}