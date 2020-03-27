package app.storytel.candidate.com.api

//sealed class ApiResult<out T : Any> {
//    data class Success<out T : Any>(val output: T?) : ApiResult<T>()
//    data class Error(val exception: Exception) : ApiResult<Nothing>()
//    object Loading : ApiResult<Nothing>()
//}

sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val output: T) : ApiResult<T>()
    data class Error(val exception: Exception) : ApiResult<Nothing>()
}

