package DI

import UserViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CommonKoinHelper : KoinComponent {
    val userViewModel: UserViewModel by inject()
 }