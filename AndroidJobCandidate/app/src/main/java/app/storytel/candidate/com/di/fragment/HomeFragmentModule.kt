package app.storytel.candidate.com.di.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import app.storytel.candidate.com.api.AppApi
import app.storytel.candidate.com.ui.ScrollingPostFragment
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class HomeFragmentModule {

    @Provides
    fun provideHomeFragment(homeFragment: ScrollingPostFragment): ScrollingPostFragment {
        return homeFragment
    }
}