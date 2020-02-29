package xyz.luchengeng.minio.starter.test

import org.junit.Test
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
    SpringRunner::class)
class MinIOStarterTest constructor() {
    @Autowired
    lateinit var client: MinIOClient
    val logger = LoggerFactory.getLogger(MinIOStarterTest::class.java)
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
    }
}