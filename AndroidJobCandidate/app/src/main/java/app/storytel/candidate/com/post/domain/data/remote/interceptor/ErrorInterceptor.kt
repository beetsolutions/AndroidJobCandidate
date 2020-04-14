package app.storytel.candidate.com.post.domain.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Responsible for managing errors from the api.
 * Should map every error we expect from the api here.
 */
class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        val response = chain.proceed(request)
        if (!response.isSuccessful) {
            throw Throwable("Something went wrong!")
        }
        return response
    }
}