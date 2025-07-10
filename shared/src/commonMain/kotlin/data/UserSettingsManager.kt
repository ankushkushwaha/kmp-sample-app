package data
open class UserSettingsManager(
    private val storage: Storage = getPlatformSystemPreferences()
) {
    private val countKey = "count"

    open fun saveCount(count: Int) {
        storage.save(countKey, count)
    }

    open fun getCount(): Int {
        return storage.getInt(countKey)
    }

    fun clearUser() {
        storage.remove(countKey)
    }
}
