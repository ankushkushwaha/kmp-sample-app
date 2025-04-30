package di

import Network.UserRepository
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

//fun initKoinFromShared(androidModules: Module = module {}) {
//    startKoin {
//        modules(
//            appendWithSharedModule(
//                androidModules // Android modules
//            )
//        )
//    }
//}
