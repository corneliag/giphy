package com.cjuca.giphy

import android.app.Application
import com.cjuca.giphy.service.module.GiphyModule
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class GiphyApplication : Application() {
    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidContext(this@GiphyApplication)
            modules(GiphyModule.giphyserviceModule)
        }
        super.onCreate()
        registerRxUncaughtExceptionHandler()
    }


    private fun registerRxUncaughtExceptionHandler() {
        RxJavaPlugins.setErrorHandler { throwable ->
            Timber.e(throwable, "RxUncaughtException")
        }
    }
}