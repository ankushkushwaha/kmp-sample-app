package data
class UserSettingsManager(
    private val storage: Storage = getPlatformSystemPreferences()
) {
    private val countKey = "count"

    fun saveCount(count: Int) {
        storage.save(countKey, count)
    }

    fun getCount(): Int {
        return storage.getInt(countKey)
    }

    fun clearUser() {
        storage.remove(countKey)
    }
}
