package app.storytel.candidate.com.post.domain.data.remote.repository.resource

data class Resource<out T>(val status: Status, val data: T?, val error: String?) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data,null)
        }

        fun <T> error(error: Throwable, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, error.message)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}