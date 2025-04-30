package di

import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

fun initWithSharedModules(iOSModule: String) {
    // Do common stuff here

    //init koin DI
    startKoin {
        modules(appendWithSharedModule(
            module {
                // Add iOS specific DI objects here
                factory { iOSModule } bind String::class
            }
        ))
    }
}
