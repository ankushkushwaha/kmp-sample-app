package di

import Presentation.ContentViewModel
import Presentation.TodoViewModel
import Presentation.UserViewModel
import com.example.kmp_sample_app.database.AppDatabase
import data.Database.DatabaseDriverFactory
import data.Repositories.TodoRepository
import org.koin.core.module.Module
import org.koin.dsl.module


private val viewModelModules = module {
    factory { ContentViewModel() }
    factory { UserViewModel() }
    factory { TodoViewModel(get()) }
}

private val repositoryModules = module {
    factory { TodoRepository(get()) }
}

private val sharedModules = arrayListOf(
    viewModelModules,
    repositoryModules,
)

/*
* This function combines the appModule dependencies to the sharedModules list and returns the list
* @param appModule - the module that contains all the dependencies of shared module.
 */
fun appendWithSharedModule(platformSpecificModules: Module = module {}): List<Module> {
    sharedModules.add(index = 0, element = platformSpecificModules)
    return sharedModules
}


