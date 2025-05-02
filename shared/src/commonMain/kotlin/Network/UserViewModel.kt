import Model.User
import Network.UserRepository
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel(
    private val userRepository: UserRepository = UserRepository(),
    private val manager: UserSettingsManager = UserSettingsManager()
) : ViewModel() {

    private val _viewState = com.rickclephas.kmp.observableviewmodel.MutableStateFlow(
        viewModelScope,
        ViewState()
    )

    val viewState = _viewState.asStateFlow()
    val viewStateValue: ViewState
        get() = viewState.value

    fun fetchUsers() {
        viewModelScope.launch {

            _viewState.value = _viewState.value.copy(isLoading = true)
//            println("State" + _viewState.value)

            val users = userRepository.fetchUsers()

            _viewState.value = _viewState.value.copy(
                isLoading = false,
                users = users
            )

            println("State" + _viewState.value)

            val count = manager.getCount()
            manager.saveCount(count+1)
            println("App launched: -- ${manager.getCount()} -- times")
        }
    }

    data class ViewState(
        var isLoading: Boolean = false,
        var errorMessage: String? = null,
        var users: List<User> = emptyList()
    )
}


