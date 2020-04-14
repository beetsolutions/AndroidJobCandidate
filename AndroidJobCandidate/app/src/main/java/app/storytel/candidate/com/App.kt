package app.storytel.candidate.com

import android.app.Application
import app.storytel.candidate.com.di.appModule
import app.storytel.candidate.com.post.di.postsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

open class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // Connect Fabrics or Firebase here to handle
        }
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(appModule, postsModule))
        }
    }
}