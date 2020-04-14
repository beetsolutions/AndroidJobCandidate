package app.storytel.candidate.com.post.domain.data.remote.repository.resource

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.storytel.candidate.com.AppDispatchers
import app.storytel.candidate.com.post.domain.data.remote.model.ApiResponse
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.coroutineContext

abstract class NetworkBoundResource<ResultType, RequestType>(private val appDispatchers: AppDispatchers) {

    private val result = MutableLiveData<Resource<ResultType>>()
    private val supervisorJob = SupervisorJob()

    suspend fun build(): NetworkBoundResource<ResultType, RequestType> {
        withContext(appDispatchers.main) { result.value = Resource.loading(null) }
        CoroutineScope(coroutineContext).launch(supervisorJob) {
            val dbResult = loadFromDb()
            if (shouldFetch(dbResult)) {
                fetchFromNetwork(dbResult)
            } else {
                setValue(Resource.success(dbResult))
            }
        }
        return this
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    private suspend fun fetchFromNetwork(dbResult: ResultType) {
        Timber.d("Fetch data from network")
        setValue(Resource.loading(dbResult))

        when (val apiResponse = createCallAsync().await()) {
            is ApiResponse.ApiSuccessResponse -> {
                saveCallResults(processResponse(apiResponse))
                setValue(Resource.success(loadFromDb()))
            }
            is ApiResponse.ApiEmptyResponse -> {
                setValue(Resource.success(loadFromDb()))
            }
            is ApiResponse.ApiErrorResponse -> {
                onFetchFailed()
                setValue(Resource.error(Throwable(apiResponse.errorMessage), loadFromDb()))
            }
        }
        Timber.i("Data fetched from network")
    }

    protected open fun onFetchFailed() {}

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        Timber.d("Resource: $newValue")
        if (result.value != newValue) result.postValue(newValue)
    }

    @WorkerThread
    protected open fun processResponse(response: ApiResponse.ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract suspend fun saveCallResults(items: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): ResultType

    @MainThread
    protected abstract fun createCallAsync(): Deferred<ApiResponse<RequestType>>
}