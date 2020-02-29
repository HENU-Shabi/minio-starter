package xyz.luchengeng.minio.starter.test

import io.minio.errors.ErrorResponseException
import org.junit.Test
import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import xyz.luchengeng.minio.starter.MinIOClient
import xyz.luchengeng.minio.starter.withContentType
import xyz.luchengeng.minio.starter.withHeader

@SpringBootTest
@RunWith(
    SpringRunner::class
)
class MinIOClientTest {
    @Autowired
    lateinit var client: MinIOClient
    val logger: Logger = LoggerFactory.getLogger(MinIOClientTest::class.java)
    @Test
    fun test() {
        client["test"] = Unit
        client["pure"] = Unit
        client["test"] = "test str" withHeader mutableMapOf("a" to "b") withContentType "application/json"
        client["pure"] = "test str"
        val (str, map, contentType) = client["test"]
        for ((k, v) in map) {
            logger.info { "$k to $v" }
        }
        val (str1, map1, contentType1) = client["pure"]
        for ((k, v) in map1) {
            logger.info { "$k to $v" }
        }
        assert(str == "test str")
        assert(str1 == "test str")
        try {
            client["should throw exception"]
            assert(false)
        } catch (ignored: ErrorResponseException) {

        }
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