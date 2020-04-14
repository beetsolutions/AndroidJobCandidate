package app.storytel.candidate.com.post.domain.data.local.di

import app.storytel.candidate.com.post.domain.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

const val DATABASE = "DATABASE"

val localModule = module {

    single(qualifier = StringQualifier(DATABASE)) {
        AppDatabase.buildDatabase(androidContext())
    }

    factory {
        (get(qualifier = StringQualifier(DATABASE)) as AppDatabase).postsDao()
    }

    factory {
        (get(qualifier = StringQualifier(DATABASE)) as AppDatabase).photosDao()
    }

    factory {
        (get(qualifier = StringQualifier(DATABASE)) as AppDatabase).commentsDao()
    }
}