package app.storytel.candidate.com.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.storytel.candidate.com.di.viewModel.ViewModelKey
import app.storytel.candidate.com.ui.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}