package app.storytel.candidate.com.di

import android.content.Context
import app.storytel.candidate.com.PostApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideContext(application: PostApplication): Context {
        return application.applicationContext
    }
}