package xyz.luchengeng.minio.starter.test

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.test.context.junit4.SpringRunner
import xyz.luchengeng.minio.starter.MinIOClient

@SpringBootTest
@RunWith(
    SpringRunner::class)
class MinIOStarterTest constructor() {
    @Autowired
    lateinit var client: MinIOClient
    @Test
    fun test(){
        client["str"] = "str"
        assert(client["str"] == "str")
        client["str"] = "str1"
        assert(client["str"] == "str1")
        client["str"] = Unit
        assert(client["str"] is Unit)
        assert(client["should return Unit"] is Unit)
    }
}