package app.storytel.candidate.com.post.domain.data.remote.adapter

import app.storytel.candidate.com.post.domain.data.remote.model.ApiResponse
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * [ApiResponse] adapter adaptation
 */
class CoroutineCallAdapterFactory private constructor() : CallAdapter.Factory() {

    companion object {
        @JvmStatic
        @JvmName("create")
        operator fun invoke() = CoroutineCallAdapterFactory()
    }

    override fun get(
            returnType: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
    ): CallAdapter<*, *>? {

        if (getRawType(returnType) != Deferred::class.java) {
            return null
        }
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        if (rawObservableType != ApiResponse::class.java) {
            throw IllegalArgumentException("type must be a resource")
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        val bodyType = getParameterUpperBound(0, observableType)
        return BodyCallAdapter<Any>(bodyType)
    }
}

private class BodyCallAdapter<T>(
        private val responseType: Type
) : CallAdapter<T, Deferred<ApiResponse<T>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<T>): Deferred<ApiResponse<T>> {
        val deferred = CompletableDeferred<ApiResponse<T>>()

        deferred.invokeOnCompletion {
            if (deferred.isCancelled) {
                call.cancel()
            }
        }

        call.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                deferred.complete(ApiResponse.create(t))
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    deferred.complete(ApiResponse.create(response))
                } else {
                    deferred.complete(
                            ApiResponse.create(
                                    Throwable(

                                    )
                            )
                    )
                }
            }
        })

        return deferred
    }
}
