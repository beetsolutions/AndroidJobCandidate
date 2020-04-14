package app.storytel.candidate.com.post.domain.data.remote.di

import android.app.Application
import app.storytel.candidate.com.AppDispatchers
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

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

val remoteModule = module {
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { providePostRepository(get(), get(), get(), get(), get()) }
    single { providePostService(get()) }
}

fun providePostRepository(postService: PostService,
                          appDispatchers: AppDispatchers,
                          postsDao: PostsDao,
                          photosDao: PhotosDao,
                          commentsDao: CommentsDao): PostRepository {
    return PostRepositoryImpl(postService, appDispatchers, postsDao, photosDao, commentsDao)
}

fun providePostService(retrofit: Retrofit): PostService = retrofit.create(PostService::class.java)

fun provideOkHttpClient(application: Application): OkHttpClient {
    return OkHttpClient()
            .newBuilder()
            .addInterceptor(NoConnectionInterceptor(application))
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

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
}