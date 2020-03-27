package app.storytel.candidate.com.di.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import app.storytel.candidate.com.api.AppApi
import app.storytel.candidate.com.di.viewModel.ViewModelKey
import app.storytel.candidate.com.repository.MainRepository
import app.storytel.candidate.com.ui.MainViewModel
import app.storytel.candidate.com.ui.ScrollingActivityNew
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class MainActivityModule {

    @Provides
    fun provideMainRepository(
            appApi: AppApi
    ): MainRepository {
        return MainRepository(appApi)
    }

    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun provideMainViewModel(
            mainActivity: ScrollingActivityNew,
            viewModelProvider: ViewModelProvider.Factory
    ): ViewModel {
        return ViewModelProviders.of(mainActivity, viewModelProvider).get(MainViewModel::class.java)
    }
}