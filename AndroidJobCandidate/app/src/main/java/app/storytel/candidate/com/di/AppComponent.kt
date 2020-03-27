package app.storytel.candidate.com.di

import android.app.Application
import app.storytel.candidate.com.PostApplication
import app.storytel.candidate.com.di.activity.ActivityBuilder
import app.storytel.candidate.com.di.fragment.FragmentBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.AndroidInjector.Builder
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [NetworkModule::class,
            AppModule::class,
            ViewModelModule::class,
            AndroidSupportInjectionModule::class,
            ActivityBuilder::class,
            FragmentBuilder::class]
)
interface AppComponent : AndroidInjector<PostApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<PostApplication>()
}