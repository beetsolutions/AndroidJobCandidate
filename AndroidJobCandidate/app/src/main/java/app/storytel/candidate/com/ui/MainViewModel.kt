package app.storytel.candidate.com.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import app.storytel.candidate.com.dataModel.CommentModel
import app.storytel.candidate.com.dataModel.PhotoModel
import app.storytel.candidate.com.dataModel.PostModel
import app.storytel.candidate.com.dataModel.PostsAndPhotos
import app.storytel.candidate.com.extensions.setOrPost
import app.storytel.candidate.com.repository.MainRepository
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class MainViewModel @Inject constructor() :
        ViewModel(), CoroutineScope {

    @Inject
    lateinit var mainRepository: MainRepository

    private val job = SupervisorJob()

    private val TIMEOUT_LIMIT = 5000L

    @Inject
    lateinit var context: Context

    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    private val _currentScrollingPositionLiveData = MutableLiveData<Int>()
    val currentScrollingPositionLiveData = _currentScrollingPositionLiveData
    fun getScrollingPosition (pos:Int){
        _currentScrollingPositionLiveData.setOrPost(pos)
    }

    private val postListLiveData = MutableLiveData<MutableList<PostModel>>()
    private val photoListLiveData = MutableLiveData<MutableList<PhotoModel>>()

    val postAndPhotoListLiveData = MutableLiveData<PostsAndPhotos>()

    val isLoadingPostsAndPhotos = MutableLiveData<Boolean>().apply { value = true }
    val isTimeoutPostsAndPhotos = MutableLiveData<Boolean>().apply { value = false }

    fun getPostsAndPhotos() {
        launch {
            withContext(Dispatchers.IO) {
                val latestPosts = mainRepository.getPostList()
                val latestPhotos = mainRepository.getPhotoList()
                if (!latestPosts.isNullOrEmpty() && !latestPhotos.isNullOrEmpty()) {
                    isLoadingPostsAndPhotos.setOrPost(false)

                    postListLiveData.setOrPost(latestPosts)
                    photoListLiveData.setOrPost(latestPhotos)

                    postAndPhotoListLiveData.setOrPost(PostsAndPhotos(latestPosts, latestPhotos))
                } else {
                    isLoadingPostsAndPhotos.setOrPost(true)
                    Log.e("Empty post or Photo", "No contents about this post or photo!")

                    delay(TIMEOUT_LIMIT)
                    isLoadingPostsAndPhotos.setOrPost(false)
                    isTimeoutPostsAndPhotos.setOrPost(true)
                }
            }
        }
    }

    var selectedPostIdLiveData = MutableLiveData<Int?>()

    private val _commentsLiveData = MutableLiveData<MutableList<CommentModel>>()
    val commentsLiveData = _commentsLiveData

    val isLoadingComments = MutableLiveData<Boolean>().apply { value = true }
    val isTimeoutComments = MutableLiveData<Boolean>().apply { value = false }

    fun getComments(postId: Int) {
        launch {
            withContext(Dispatchers.IO) {
                val latestComments = mainRepository.getCommentList(postId)

                if (!latestComments.isNullOrEmpty()) {
                    isLoadingComments.setOrPost(false)
                    _commentsLiveData.setOrPost(latestComments)
                } else {
                    isLoadingComments.setOrPost(true)
                    Log.i("Empty COMMENTS", "No comments about this post!")

                    delay(TIMEOUT_LIMIT)
                    isLoadingComments.setOrPost(false)
                    isTimeoutComments.setOrPost(true)
                }
            }
        }
    }
}
