package di

import Presentation.ContentViewModel
import Presentation.TodoViewModel
import Presentation.UserViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CommonKoinHelper : KoinComponent {
    val contentViewModel: ContentViewModel by inject()
    val userViewModel: UserViewModel by inject()
    val todoViewModel: TodoViewModel by inject()
 }