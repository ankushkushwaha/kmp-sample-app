package di

import Presentation.ContentViewModel
import Presentation.UserViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CommonKoinHelper : KoinComponent {
    val userViewModel: UserViewModel by inject()
    val contentViewModel: ContentViewModel by inject()
 }