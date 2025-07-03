// shared/src/commonMain/kotlin/Helper/CommonKmpHelper.kt

package data.Database

import Presentation.TodoViewModel
import com.example.kmp_sample_app.database.AppDatabase
import data.Database.DatabaseDriverFactory
import data.Repositories.TodoRepository

object CommonKmpHelper {

    private var _database: AppDatabase? = null
    private var _todoRepository: TodoRepository? = null
    private var _todoViewModel: TodoViewModel? = null

    fun init(driverFactory: DatabaseDriverFactory) {
        if (_database == null) {
            _database = AppDatabase(driverFactory.create())
        }

        if (_todoRepository == null) {
            _todoRepository = TodoRepository(_database!!)
        }

        if (_todoViewModel == null) {
            _todoViewModel = TodoViewModel(_todoRepository!!)
        }
    }

    fun getTodoViewModel(): TodoViewModel {
        return _todoViewModel ?: error("CommonKmpHelper is not initialized. Call init() first.")
    }
}
