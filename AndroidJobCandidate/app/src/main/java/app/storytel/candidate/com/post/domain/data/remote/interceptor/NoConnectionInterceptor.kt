package app.storytel.candidate.com.post.domain.data.remote.interceptor

import android.content.Context
import app.storytel.candidate.com.R
import app.storytel.candidate.com.common.util.isConnectionOn
import app.storytel.candidate.com.post.domain.data.remote.interceptor.exception.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Catches all internet connection issues and propagates the errors to the UI
 */
class NoConnectionInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!context.isConnectionOn()) {
            throw NoConnectivityException(context.getString(R.string.error_no_connection))
        } else {
            chain.proceed(chain.request())
        }
    }
}