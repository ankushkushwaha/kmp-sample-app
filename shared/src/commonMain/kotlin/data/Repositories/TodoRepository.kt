package data.Repositories

import com.example.kmp_sample_app.database.AppDatabase
import com.example.kmpsampleapp.database.Todo  // ✅ Make sure to import this


class TodoRepository(private val db: AppDatabase) {
    private val queries = db.appDatabaseQueries

    fun getAllTodos(): List<Todo> = queries.selectAllTodos().executeAsList()

    fun addTodo(title: String) = queries.insertTodo(title = title, is_done = 0)

    fun toggleTodo(id: Long, isDone: Boolean) =
        queries.toggleTodoStatus(is_done = if (isDone) 1 else 0, id = id)

    fun deleteTodo(id: Long) = queries.deleteTodo(id)
}
