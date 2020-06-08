package com.nexoslav.boundservicemvvm

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.compat.ViewModelCompat.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // declare used Android context
            androidContext(this@MyApplication)
            // declare modules
            modules(myModule)
        }
    }
}

// declared ViewModel using the viewModel keyword
val myModule: Module = module {
    single { BleManager() }
    viewModel { MainActivityViewModel(get()) }
}
