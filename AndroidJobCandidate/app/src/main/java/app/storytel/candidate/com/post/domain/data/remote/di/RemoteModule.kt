package app.storytel.candidate.com.post.domain.data.remote.di

import android.app.Application
import app.storytel.candidate.com.BuildConfig
import app.storytel.candidate.com.post.domain.data.remote.adapter.CoroutineCallAdapterFactory
import app.storytel.candidate.com.post.domain.data.remote.api.PostService
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
    single { providePostRepository() }
    single { providePostService(get()) }
}

fun providePostRepository(): PostRepository {
    return PostRepositoryImpl()
}

fun providePostService(retrofit: Retrofit): PostService = retrofit.create(PostService::class.java)

fun provideOkHttpClient(application: Application): OkHttpClient {
    return OkHttpClient()
            .newBuilder()
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