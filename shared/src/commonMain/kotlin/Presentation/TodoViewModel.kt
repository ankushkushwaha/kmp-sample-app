package Presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmpsampleapp.database.Todo
import data.Repositories.TodoRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos.asStateFlow()

    init {
        loadTodos()
    }

    fun loadTodos() {
        viewModelScope.launch {
            _todos.value = repository.getAllTodos()
        }
    }

    fun addTodo(title: String) {
        viewModelScope.launch {
            if (title.isNotBlank()) {
                repository.addTodo(title)
                loadTodos()
            }
        }
    }

    fun toggleTodo(todo: Todo) {
        viewModelScope.launch {
            repository.toggleTodo(todo.id, todo.is_done == 0L)
            loadTodos()
        }
    }
}
