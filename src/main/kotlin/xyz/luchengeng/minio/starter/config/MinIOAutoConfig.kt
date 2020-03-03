package xyz.luchengeng.minio.starter.config

import io.minio.MinioClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import xyz.luchengeng.minio.starter.bean.MinIOClient
import xyz.luchengeng.minio.starter.bean.ObjectMap

@Configuration
@ConditionalOnProperty(prefix = "min.io",name = ["host"])
@EnableConfigurationProperties(MinIOProperties::class)
class MinIOAutoConfig @Autowired constructor(val props : MinIOProperties){

    @Autowired
    private lateinit var rawClient: MinioClient


    @Bean
    fun rawClient(): MinioClient {
        return MinioClient(props.host, props.accessKey, props.secretKey)
    }

    @Bean
    fun map(): ObjectMap {
        return ObjectMap(
            MinIOClient(
                rawClient,
                props
            )
        )
    }
}