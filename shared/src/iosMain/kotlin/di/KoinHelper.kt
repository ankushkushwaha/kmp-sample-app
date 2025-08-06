package di

import com.example.kmp_sample_app.database.AppDatabase
import data.Database.DatabaseDriverFactory
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

fun initWithSharedModules(iOSModule: String) {
    // Do common stuff here

    //init koin DI

    val driverFactory = DatabaseDriverFactory()

    startKoin {
        modules(appendWithSharedModule(
            module {
                // Add iOS specific DI objects here
                factory { iOSModule } bind String::class
                single { AppDatabase(driverFactory.create()) } bind AppDatabase::class
            }
        ))
    }
}
