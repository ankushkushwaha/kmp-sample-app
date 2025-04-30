package DI

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

fun initWithSharedModules(iOSModule: String) {
    // Do common stuff here

    //init koin DI
    startKoin {
        modules(getSharedModules(
            module {
                // Add iOS specific DI objects here
                factory { iOSModule } bind String::class
            }
        ))
    }
}
