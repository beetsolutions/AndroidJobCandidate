package app.storytel.candidate.com.post.domain.data.remote.di

import android.content.Context
import app.storytel.candidate.com.BuildConfig
import app.storytel.candidate.com.post.domain.data.local.dao.CommentsDao
import app.storytel.candidate.com.post.domain.data.local.dao.PhotosDao
import app.storytel.candidate.com.post.domain.data.local.dao.PostsDao
import app.storytel.candidate.com.post.domain.data.remote.adapter.CoroutineCallAdapterFactory
import app.storytel.candidate.com.post.domain.data.remote.api.PostService
import app.storytel.candidate.com.post.domain.data.remote.interceptor.ErrorInterceptor
import app.storytel.candidate.com.post.domain.data.remote.interceptor.NoConnectionInterceptor
import app.storytel.candidate.com.post.domain.data.remote.repository.PostRepository
import app.storytel.candidate.com.post.domain.data.remote.repository.PostRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

fun remoteModule(baseUrl: String, context: Context) = module {
    single { providePostRepository(get(), get(), get(), get()) }

    factory {
        OkHttpClient.Builder()
                .addInterceptor(NoConnectionInterceptor(context))
                .addInterceptor(ErrorInterceptor())
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
                })
                .connectTimeout(30000, TimeUnit.MILLISECONDS)
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .writeTimeout(30000, TimeUnit.MILLISECONDS)
                .build()
    }

    single {
        Retrofit.Builder()
                .client(get())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }

    factory{ get<Retrofit>().create(PostService::class.java) }
}

fun providePostRepository(postService: PostService,
                          postsDao: PostsDao,
                          photosDao: PhotosDao,
                          commentsDao: CommentsDao): PostRepository {
    return PostRepositoryImpl(postService, postsDao, photosDao, commentsDao)
}
