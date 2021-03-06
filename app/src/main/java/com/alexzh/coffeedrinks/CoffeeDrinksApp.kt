package com.alexzh.coffeedrinks

import android.app.Application
import com.alexzh.coffeedrinks.di.dataModule
import com.alexzh.coffeedrinks.di.mapperModule
import com.alexzh.coffeedrinks.di.viewModelModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@ExperimentalCoroutinesApi
class CoffeeDrinksApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CoffeeDrinksApp)
            modules(
                listOf(
                    dataModule,
                    mapperModule,
                    viewModelModule
                )
            )
        }
    }
}
