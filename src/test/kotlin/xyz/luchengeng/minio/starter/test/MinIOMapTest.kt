package xyz.luchengeng.minio.starter.test

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import xyz.luchengeng.minio.starter.MinIOMap

@SpringBootTest
@RunWith(
    SpringRunner::class
)
class MinIOMapTest {
    @Autowired
    private lateinit var map: MinIOMap

    @Test
    fun test() {
        map.clear()
        map["1"] = "1"
        map.putAll(mapOf("2" to "2", "3" to "3"))
        assert(map.put("1", "2") == "1")
        assert(map["1"] == "2")
        assert(map["non-exist"] == null)
        assert(!map.isEmpty())
        assert(map.containsValue("2"))
        assert(map.containsKey("1"))
        assert(map.replace("1", "3") == "2")
        assert(map.isNotEmpty())
        map.clear()
        assert(map.isEmpty())
    }
}