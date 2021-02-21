package id.anis.bajpsub1

import android.app.Application
import id.anis.bajpsub1.core.di.*
import id.anis.bajpsub1.di.useCaseModule
import id.anis.bajpsub1.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyMovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyMovieApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )

            )
        }
    }
}