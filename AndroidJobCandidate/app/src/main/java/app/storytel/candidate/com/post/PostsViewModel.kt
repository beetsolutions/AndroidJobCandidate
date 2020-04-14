package app.storytel.candidate.com.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.storytel.candidate.com.AppDispatchers
import app.storytel.candidate.com.common.base.BaseViewModel
import app.storytel.candidate.com.common.util.combineWith
import app.storytel.candidate.com.post.domain.data.local.model.Photo
import app.storytel.candidate.com.post.domain.data.local.model.Post
import app.storytel.candidate.com.post.domain.data.remote.repository.resource.Resource
import app.storytel.candidate.com.post.domain.interactor.GetPhotosUseCase
import app.storytel.candidate.com.post.domain.interactor.GetPostsUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostsViewModel(private val getPostsUseCase: GetPostsUseCase,
                     private val getPhotosUseCase: GetPhotosUseCase,
                     private val appDispatchers: AppDispatchers) : BaseViewModel() {

    private var postsSource: LiveData<Resource<List<Post>>> = MutableLiveData()
    private val _posts = MediatorLiveData<Resource<List<Post>>>()
    private val posts: LiveData<Resource<List<Post>>> get() = _posts

    private var photosSource: LiveData<Resource<List<Photo>>> = MutableLiveData()
    private val _photos = MediatorLiveData<Resource<List<Photo>>>()
    private val photos: LiveData<Resource<List<Photo>>> get() = _photos

    init {
        getData()
    }

    fun getData() {
        getPosts()
        getPhotos()
    }

    private fun getPosts() = viewModelScope.launch(appDispatchers.main) {
        _posts.removeSource(postsSource)
        withContext(appDispatchers.io) { postsSource = getPostsUseCase() }
        _posts.addSource(postsSource) {
            _posts.value = it
        }
    }

    private fun getPhotos() = viewModelScope.launch(appDispatchers.main) {
        _photos.removeSource(photosSource)
        withContext(appDispatchers.io) { photosSource = getPhotosUseCase() }
        _photos.addSource(photosSource) {
            _photos.value = it
        }
    }

    // FIXME Remove this when a proper API is provided with proper data structure
    // API to fetch photos does not have any proper correlation with the post (Really bad API representation)
    val postsData: LiveData<Resource<List<Post>>> = posts.combineWith(photos) { postResource, photoResource ->
        return@combineWith computePosts(postResource, photoResource)
    }

    fun onPostItemClicked(post: Post) = navigate(PostsFragmentDirections.actionNavigationPostsToNavigationPost(post, post.title!!))

    private fun computePosts(postResource: Resource<List<Post>>?, photoResource: Resource<List<Photo>>?): Resource<List<Post>> {
        when {

            postResource?.status == Resource.Status.LOADING && photoResource?.status == Resource.Status.LOADING || postResource?.status == Resource.Status.SUCCESS && photoResource?.status == Resource.Status.LOADING || postResource?.status == Resource.Status.LOADING && photoResource?.status == Resource.Status.SUCCESS -> {
                return Resource.loading(null)
            }

            // We can still show local saved posts if we have an error
            postResource?.status == Resource.Status.ERROR -> {
                return Resource.error(Throwable(postResource.error), postResource.data)
            }

            photoResource?.status == Resource.Status.ERROR -> {
                return Resource.error(Throwable(photoResource.error), null)
            }

            // We can still show local saved posts if we have an error
            postResource?.status == Resource.Status.ERROR && photoResource?.status == Resource.Status.ERROR -> {
                return Resource.error(Throwable(photoResource.error), postResource.data)
            }

            postResource?.status == Resource.Status.SUCCESS && photoResource?.status == Resource.Status.SUCCESS -> {

                val resPhotos = photoResource.data?.subList(0, postResource.data!!.size)
                val postsById: Map<Int, Post> = postResource.data!!.associateBy { it.id }

                val resPosts = resPhotos!!.filter { postsById[it.id] != null }.map { photo ->
                    postsById[photo.id].let { post ->
                        post?.url = photo.url
                        post?.thumbNail = photo.thumbnailUrl
                        return@let post!!
                    }
                }
                return Resource.success(resPosts)
            }

            else -> {
                return Resource.loading(null)
            }
        }
    }
}