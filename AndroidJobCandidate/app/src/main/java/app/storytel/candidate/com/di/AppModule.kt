package app.storytel.candidate.com.di

import app.storytel.candidate.com.AppDispatchers
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val appModule = module {
    factory { AppDispatchers(Dispatchers.Main, Dispatchers.IO) }
}