package Presentation

import Presentation.UserViewModel.ViewState
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import com.example.kmpsampleapp.database.Todo
import data.Model.User
import data.Repositories.TodoRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    private val _viewState = MutableStateFlow(
        viewModelScope,
        ViewState()
    )
    val viewState = _viewState.asStateFlow()
    val viewStateValue: TodoViewModel.ViewState
        get() = viewState.value

    init {
        loadTodos()
    }

    fun loadTodos() {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(todos = repository.getAllTodos())
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

    data class ViewState(
        val todos: List<Todo> = emptyList()
    )
}
