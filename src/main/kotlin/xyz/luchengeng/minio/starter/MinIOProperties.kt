package xyz.luchengeng.minio.starter

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "min.io")
class MinIOProperties {
    var host : String = "http://127.0.0.1:9000"
    var accessKey : String = "minioadmin"
    var secretKey : String = "minioadmin"
    var bucket : String = "default"
}