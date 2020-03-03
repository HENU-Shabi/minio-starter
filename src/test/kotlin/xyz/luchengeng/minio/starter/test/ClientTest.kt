package xyz.luchengeng.minio.starter.test

import io.minio.MinioClient
import org.junit.Test
import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit4.SpringRunner
import xyz.luchengeng.minio.starter.bean.MinIOClient
import xyz.luchengeng.minio.starter.bean.withContentType
import xyz.luchengeng.minio.starter.bean.withHeader
import xyz.luchengeng.minio.starter.config.MinIOProperties

@SpringBootTest
@RunWith(
    SpringRunner::class
)
class ClientTest {
    @Autowired
    lateinit var client: MinIOClient
    val logger: Logger = LoggerFactory.getLogger(ClientTest::class.java)

    @TestConfiguration
    class MinIOClientConfig @Autowired constructor(val props: MinIOProperties) {
        @Bean
        fun client(): MinIOClient {
            return MinIOClient(
                MinioClient(
                    props.host,
                    props.accessKey,
                    props.secretKey
                ), props
            )
        }
    }

    @Test
    fun test() {
        client["test"] = Unit
        client["pure"] = Unit
        client["test"] = "test str" withHeader mutableMapOf("a" to "b") withContentType "application/json"
        client["pure"] = "test str"
        val (str, map, _) = client["test"]
        for ((k, v) in map) {
            logger.info { "$k to $v" }
        }
        val (str1, map1, _) = client["pure"]
        for ((k, v) in map1) {
            logger.info { "$k to $v" }
        }
        assert(str == "test str")
        assert(str1 == "test str")
        client["non-existent"].component1() is Unit
        client["test"] = Unit
        client["pure"] = Unit
    }

    @Test
    fun testLargeObj() {
        client["large"] = ByteArray(64 * 1024 * 1024) {
            127.toByte()
        }
        val (bytes, _, _) = client["large"]
        (bytes as ByteArray).forEach {
            assert(it == 127.toByte())
        }
        client["large"] = Unit
    }
}