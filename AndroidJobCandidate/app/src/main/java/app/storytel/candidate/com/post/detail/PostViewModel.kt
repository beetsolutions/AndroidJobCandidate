package app.storytel.candidate.com.post.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.storytel.candidate.com.AppDispatchers
import app.storytel.candidate.com.common.base.BaseViewModel
import app.storytel.candidate.com.post.domain.data.local.model.Comment
import app.storytel.candidate.com.post.domain.data.local.model.Post
import app.storytel.candidate.com.post.domain.data.remote.repository.resource.Resource
import app.storytel.candidate.com.post.domain.interactor.GetCommentsByPostId
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostViewModel(private val getCommentsByPostId: GetCommentsByPostId,
                    private val appDispatchers: AppDispatchers) : BaseViewModel() {

    val title = MutableLiveData<String>()
    val body = MutableLiveData<String>()

    private var commentsSource: LiveData<Resource<List<Comment>>> = MutableLiveData()

    private val _comments = MediatorLiveData<Resource<List<Comment>>>()
    val comments: LiveData<Resource<List<Comment>>> get() = _comments

    private fun loadPostDetail(id: Int) = viewModelScope.launch(appDispatchers.main) {
        _comments.removeSource(commentsSource)
        withContext(appDispatchers.io) { commentsSource = getCommentsByPostId(id) }
        _comments.addSource(commentsSource) {
            _comments.value = it
        }
    }

    fun setupPost(post: Post) {
        title.value = post.title
        body.value = post.body
        loadPostDetail(post.id)
    }
}