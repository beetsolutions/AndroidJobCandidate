package app.storytel.candidate.com.di.fragment

import app.storytel.candidate.com.ui.DetailsFragment
import app.storytel.candidate.com.ui.ScrollingPostFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun contributeScrollingPostFragment(): ScrollingPostFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailsFragment(): DetailsFragment
}