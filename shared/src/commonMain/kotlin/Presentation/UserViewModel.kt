package Presentation

import data.UserSettingsManager
import data.Model.User
import data.Network.UserRepository
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel(
    private val userRepository: UserRepository = UserRepository(),
    private val manager: UserSettingsManager = UserSettingsManager()
) : ViewModel() {

    private val _viewState = MutableStateFlow(
        viewModelScope,
        ViewState()
    )
    val viewState = _viewState.asStateFlow()
    val viewStateValue: ViewState
        get() = viewState.value

    init {
        viewModelScope.launch {
            combine(
                _viewState.map { it.users },
                _viewState.map { it.searchQuery }
            ) { users, query ->
                if (query.isBlank()) users
                else users.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            it.email.contains(query, ignoreCase = true)
                }
            }.collect { filtered ->
                _viewState.value = _viewState.value.copy(filteredUsers = filtered)
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _viewState.value = _viewState.value.copy(searchQuery = query)
    }

    fun fetchUsers() {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(isLoading = true)

            try {
                val users = userRepository.fetchUsers()
                _viewState.value = _viewState.value.copy(
                    isLoading = false,
                    errorMessage = null,
                    users = users
                )
            } catch (e: Exception) {
                _viewState.value = _viewState.value.copy(
                    isLoading = false,
                    errorMessage = "Unknown error"
                )
            }

            val count = manager.getCount()
            manager.saveCount(count + 1)
            println("App launched: -- ${manager.getCount()} -- times")
        }
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val users: List<User> = emptyList(),
        val filteredUsers: List<User> = emptyList(),
        val searchQuery: String = ""
    )
}

