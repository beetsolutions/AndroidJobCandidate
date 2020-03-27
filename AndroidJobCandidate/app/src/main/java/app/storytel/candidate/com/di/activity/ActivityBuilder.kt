package app.storytel.candidate.com.di.activity

import app.storytel.candidate.com.ui.ScrollingActivityNew
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivityNew(): ScrollingActivityNew
}
