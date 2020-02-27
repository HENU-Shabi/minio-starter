package xyz.luchengeng.minio.starter

import io.minio.MinioClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(prefix = "min.io",name = ["host"])
@EnableConfigurationProperties(MinIOProperties::class)
class MinIOAutoConfig @Autowired constructor(val props : MinIOProperties){
    @Autowired
    private lateinit var rawClient: MinioClient
    @Bean
    fun rawClient() : MinioClient {
        return MinioClient(props.host, props.accessKey, props.secretKey)
    }
    @Bean
    fun client() : MinIOClient{
        return MinIOClient(rawClient,props)
    }
}