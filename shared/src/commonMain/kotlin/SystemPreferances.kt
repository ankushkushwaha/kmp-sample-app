
interface Storage {
    fun save(key: String, value: String)
    fun get(key: String): String?

    fun save(key: String, value: Int)
    fun getInt(key: String): Int

    fun save(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean?

    fun remove(key: String)
}

expect fun getPlatformSystemPreferences(): Storage
