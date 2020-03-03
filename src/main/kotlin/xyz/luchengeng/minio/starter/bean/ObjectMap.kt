package xyz.luchengeng.minio.starter.bean

class ObjectMap constructor(private val client: MinIOClient) : MutableMap<String, Any> {
    override val size: Int
        get() = client.objectList.size
    override val entries: MutableSet<MutableMap.MutableEntry<String, Any>>
        get() = client.objectList.map {
            object : MutableMap.MutableEntry<String, Any> {
                override val value: Any
                    get() = client[it.objectName()].component1()
                override val key: String
                    get() = it.objectName()

                /**
                 * Changes the value associated with the key of this entry.
                 *
                 * @return the previous value corresponding to the key.
                 */
                override fun setValue(newValue: Any): Any {
                    val prev = client[it.objectName()].component1()
                    client[it.objectName()] = newValue
                    return prev
                }
            }
        }.toMutableSet()
    override val keys: MutableSet<String>
        get() = client.objectList.map { it.objectName() }.toMutableSet()
    override val values: MutableCollection<Any>
        get() = client.objectList.map { client[it.objectName()].component1() }.toMutableList()

    /**
     * Returns `true` if the map contains the specified [key].
     */
    override fun containsKey(key: String): Boolean {
        keys.forEach {
            if (it == key) return@containsKey true
        }
        return false
    }

    /**
     * Returns `true` if the map maps one or more keys to the specified [value].
     */
    override fun containsValue(value: Any): Boolean {
        values.forEach {
            if (it == value) return@containsValue true
        }
        return false
    }

    /**
     * Returns the value corresponding to the given [key], or `null` if such a key is not present in the map.
     */
    override fun get(key: String): Any? {
        val (obj, _, _) = client[key]
        if (obj is Unit) return null
        return obj
    }

    /**
     * Returns `true` if the map is empty (contains no elements), `false` otherwise.
     */
    override fun isEmpty(): Boolean {
        return entries.count() == 0
    }

    /**
     * Removes all elements from this map.
     */
    override fun clear() {
        client.objectList.forEach {
            client[it.objectName()] = Unit
        }
    }

    /**
     * Associates the specified [value] with the specified [key] in the map.
     *
     * @return the previous value associated with the key, or `null` if the key was not present in the map.
     */
    override fun put(key: String, value: Any): Any? {
        val prev = client[key].component1()
        client[key] = value
        return if (prev is Unit) null else prev
    }

    /**
     * Updates this map with key/value pairs from the specified map [from].
     */
    override fun putAll(from: Map<out String, Any>) {
        for ((k, v) in from.entries) {
            client[k] = v
        }
    }

    /**
     * Removes the specified key and its corresponding value from this map.
     *
     * @return the previous value associated with the key, or `null` if the key was not present in the map.
     */
    override fun remove(key: String): Any? {
        val prev = client[key].component1()
        client[key] = Unit
        return if (prev is Unit) null else prev
    }
}