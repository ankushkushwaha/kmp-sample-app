package Presentation

import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import kotlinx.coroutines.flow.asStateFlow

class ContentViewModel : ViewModel() {
    private val _viewState = MutableStateFlow(
        viewModelScope,
        ViewState()
    )
    val viewState = _viewState.asStateFlow()
    val viewStateValue: ViewState get() = viewState.value

    fun selectOption(option: ContentOptions) {
        _viewState.value = _viewState.value.copy(selectedOption = option)
    }

    data class ViewState(
        val options: List<ContentOptions> = ContentOptions.entries,
        val selectedOption: ContentOptions = ContentOptions.USER_LIST
    )

    enum class ContentOptions {
        USER_LIST,
        TODOS
    }
}
