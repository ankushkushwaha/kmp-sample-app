import android.content.Context
import android.content.SharedPreferences

lateinit var appContext: Context

fun initKmpStorage(context: Context) {
    appContext = context.applicationContext
}

actual fun getPlatformSystemPreferences(): Storage {
    return SimpleDataStorageImpl(appContext)
}

class SimpleDataStorageImpl(context: Context) : Storage {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("simple_data_storage", Context.MODE_PRIVATE)

    // Save and retrieve String values
    override fun save(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun get(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    // Save and retrieve Int values
    override fun save(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    override fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    // Save and retrieve Boolean values
    override fun save(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String): Boolean? {
        return sharedPreferences.getBoolean(key, false) // Default value is false if not found
    }

    // Remove value for the key
    override fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}
