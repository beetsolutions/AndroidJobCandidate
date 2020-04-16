package app.storytel.candidate.com.post.domain.data.remote.repository

import androidx.lifecycle.LiveData
import app.storytel.candidate.com.post.domain.data.local.dao.CommentsDao
import app.storytel.candidate.com.post.domain.data.local.dao.PhotosDao
import app.storytel.candidate.com.post.domain.data.local.dao.PostsDao
import app.storytel.candidate.com.post.domain.data.local.model.Comment
import app.storytel.candidate.com.post.domain.data.local.model.Photo
import app.storytel.candidate.com.post.domain.data.local.model.Post
import app.storytel.candidate.com.post.domain.data.remote.api.PostService
import app.storytel.candidate.com.post.domain.data.remote.model.ApiResponse
import app.storytel.candidate.com.post.domain.data.remote.model.CommentResponse
import app.storytel.candidate.com.post.domain.data.remote.model.PhotoResponse
import app.storytel.candidate.com.post.domain.data.remote.model.PostResponse
import app.storytel.candidate.com.post.domain.data.remote.repository.resource.NetworkBoundResource
import app.storytel.candidate.com.post.domain.data.remote.repository.resource.Resource
import kotlinx.coroutines.Deferred

class PostRepositoryImpl(private val postService: PostService,
                         private val postsDao: PostsDao,
                         private val photosDao: PhotosDao,
                         private val commentsDao: CommentsDao) : PostRepository {

    override suspend fun getPosts(): LiveData<Resource<List<Post>>> {
        return object : NetworkBoundResource<List<Post>, List<PostResponse>>() {

            override suspend fun saveCallResults(items: List<PostResponse>) {
                if (items.isNotEmpty()) {
                    val posts = items.map {
                        Post(
                                id = it.id,
                                userId = it.userId,
                                title = it.title,
                                body = it.body
                        )
                    }

                    postsDao.save(posts)
                }
            }

            override fun shouldFetch(data: List<Post>?): Boolean = true
            override suspend fun loadFromDb(): List<Post> = postsDao.getPosts()
            override fun createCallAsync(): Deferred<ApiResponse<List<PostResponse>>> {
                return postService.getAllPostsAsync()
            }
        }.build().asLiveData()
    }

    override suspend fun getPhotos(): LiveData<Resource<List<Photo>>> {
        return object : NetworkBoundResource<List<Photo>, List<PhotoResponse>>() {

            override suspend fun saveCallResults(items: List<PhotoResponse>) {
                if (items.isNotEmpty()) {
                    val photos = items.map {
                        Photo(
                                id = it.id,
                                albumId = it.albumId,
                                title = it.title,
                                url = it.url,
                                thumbnailUrl = it.thumbnailUrl
                        )
                    }
                    photosDao.save(photos)
                }
            }

            override fun shouldFetch(data: List<Photo>?): Boolean = true
            override suspend fun loadFromDb(): List<Photo> = photosDao.getPhotos()
            override fun createCallAsync(): Deferred<ApiResponse<List<PhotoResponse>>> = postService.getAllPhotosAsync()
        }.build().asLiveData()
    }

    override suspend fun getComments(id: Int): LiveData<Resource<List<Comment>>> {
        return object : NetworkBoundResource<List<Comment>, List<CommentResponse>>() {
            override suspend fun saveCallResults(items: List<CommentResponse>) {
                if (items.isNotEmpty()) {
                    val comments = items.map {
                        Comment(
                                id = it.id,
                                postId = it.postId,
                                name = it.name,
                                email = it.email,
                                body = it.body
                        )
                    }
                    commentsDao.save(comments)
                }
            }

            override fun shouldFetch(data: List<Comment>?): Boolean = true
            override suspend fun loadFromDb(): List<Comment> = commentsDao.getComments(id)
            override fun createCallAsync(): Deferred<ApiResponse<List<CommentResponse>>> = postService.getCommentsAsync(id)
        }.build().asLiveData()
    }
}