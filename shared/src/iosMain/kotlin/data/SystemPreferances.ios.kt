package data

import platform.Foundation.NSUserDefaults

actual fun getPlatformSystemPreferences(): Storage {
    return iOSUserDefaults()
}

class iOSUserDefaults : Storage {

    private val userDefaults = NSUserDefaults.standardUserDefaults()

    // Save and retrieve String values
    override fun save(key: String, value: String) {
        userDefaults.setObject(value, forKey = key)
        userDefaults.synchronize()
    }

    override fun get(key: String): String? {
        return userDefaults.stringForKey(key)
    }

    // Save and retrieve Int values
    override fun save(key: String, value: Int) {
        userDefaults.setInteger(value.toLong(), forKey = key)
        userDefaults.synchronize()
    }

    override fun getInt(key: String): Int {
        return userDefaults.integerForKey(key).toInt()
    }

    // Save and retrieve Boolean values
    override fun save(key: String, value: Boolean) {
        userDefaults.setBool(value, forKey = key)
        userDefaults.synchronize()
    }

    override fun getBoolean(key: String): Boolean? {
        return userDefaults.boolForKey(key)
    }

    // Remove value for the key
    override fun remove(key: String) {
        userDefaults.removeObjectForKey(key)
        userDefaults.synchronize()
    }
}
