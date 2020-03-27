package app.storytel.candidate.com.di

import android.content.Context
import android.util.Log
import app.storytel.candidate.com.BuildConfig
import app.storytel.candidate.com.api.AppApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Log.d("OkHttp", message)
        }).apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun provideHttpCache(context: Context): Cache {
        return Cache(File(context.cacheDir, "appApiCache"), 1024 * 1024 * 4L)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
            cache: Cache,
            loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
            client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .callFactory(client)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideAppApi(retrofit: Retrofit): AppApi {
        return retrofit.create(AppApi::class.java)
    }

    companion object {
        private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}