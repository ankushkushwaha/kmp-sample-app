import data.Storage

class FakeStorage : Storage {
    private val stringMap = mutableMapOf<String, String>()
    private val intMap = mutableMapOf<String, Int>()
    private val boolMap = mutableMapOf<String, Boolean>()

    override fun save(key: String, value: String) {
        stringMap[key] = value
    }

    override fun get(key: String): String? = stringMap[key]

    override fun save(key: String, value: Int) {
        intMap[key] = value
    }

    override fun getInt(key: String): Int = intMap[key] ?: 0

    override fun save(key: String, value: Boolean) {
        boolMap[key] = value
    }

    override fun getBoolean(key: String): Boolean? = boolMap[key]

    override fun remove(key: String) {
        stringMap.remove(key)
        intMap.remove(key)
        boolMap.remove(key)
    }
}
