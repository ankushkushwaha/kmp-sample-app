package di

import UserViewModel
import org.koin.core.module.Module
import org.koin.dsl.module


private val viewModelModules = module {
    factory { UserViewModel() }
}

private val repositoryModules = module {
//    factory { UserRepository(get()) }
}

private val sharedModules = arrayListOf(
    viewModelModules,
    repositoryModules
)

/*
* This function combines the appModule dependencies to the sharedModules list and returns the list
* @param appModule - the module that contains all the dependencies of shared module.
 */
fun appendWithSharedModule(platformSpecificModules: Module = module {}): List<Module> {
    sharedModules.add(index = 0, element = platformSpecificModules)
    return sharedModules
}


