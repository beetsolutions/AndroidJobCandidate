package app.storytel.candidate.com.post.domain.data.remote.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.rules.CoroutinesDispatcherRule
import app.storytel.candidate.com.post.domain.data.local.dao.CommentsDao
import app.storytel.candidate.com.post.domain.data.local.dao.PhotosDao
import app.storytel.candidate.com.post.domain.data.local.dao.PostsDao
import app.storytel.candidate.com.post.domain.data.local.model.Photo
import app.storytel.candidate.com.post.domain.data.local.model.Post
import app.storytel.candidate.com.post.domain.data.remote.api.PostService
import app.storytel.candidate.com.post.domain.data.remote.model.ApiResponse
import app.storytel.candidate.com.post.domain.data.remote.model.PhotoResponse
import app.storytel.candidate.com.post.domain.data.remote.model.PostResponse
import app.storytel.candidate.com.post.domain.data.remote.repository.ApiTestUtil.successCall
import app.storytel.candidate.com.post.domain.data.remote.repository.resource.Resource
import io.mockk.*
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class PostRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesDispatcherRule = CoroutinesDispatcherRule()

    private lateinit var postRepository: PostRepository

    private lateinit var postsObserver: Observer<Resource<List<Post>>>
    private lateinit var photosObserver: Observer<Resource<List<Photo>>>

    private val postService = mockk<PostService>()
    private val postsDao = mockk<PostsDao>(relaxed = true)
    private val commentsDao = mockk<CommentsDao>(relaxed = true)
    private val photosDao = mockk<PhotosDao>(relaxed = true)

    @Before
    fun setUp() {
        postsObserver = mockk(relaxed = true)
        photosObserver = mockk(relaxed = true)

        postRepository = PostRepositoryImpl(postService, postsDao, photosDao, commentsDao)
    }

    @Test
    fun `Get posts from API`() {

        val fakeResponse = FakeData.createFakePostResponses(3)
        val data = fakeResponse.map { Post(id = it.id, userId = it.userId, title = it.title, body = it.body) }

        every { postService.getAllPostsAsync() } returns GlobalScope.async { successCall(fakeResponse) }


        coEvery { postsDao.getPosts() } returns listOf() andThen { data }

        runBlocking {
            postRepository.getPosts().observeForever(postsObserver)
        }

        verifyOrder {
            postsObserver.onChanged(Resource.loading(null))
            postsObserver.onChanged(Resource.loading(listOf()))
            postsObserver.onChanged(Resource.success(data))
        }

        coVerify(exactly = 1) {
            postsDao.save(data)
        }

        confirmVerified(postsObserver)
    }

    @Test
    fun `Get photos from API`() {

        val fakeResponse = FakeData.createFakePhotoResponses(3)
        val data = fakeResponse.map { Photo(id = it.id, albumId = it.albumId, title = it.title, url = it.url, thumbnailUrl = it.thumbnailUrl) }

        every { postService.getAllPhotosAsync() } returns GlobalScope.async { successCall(fakeResponse) }


        coEvery { photosDao.getPhotos() } returns listOf() andThen { data }

        runBlocking {
            postRepository.getPhotos().observeForever(photosObserver)
        }

        verifyOrder {
            photosObserver.onChanged(Resource.loading(null))
            photosObserver.onChanged(Resource.loading(listOf()))
            photosObserver.onChanged(Resource.success(data))
        }

        coVerify(exactly = 1) {
            photosDao.save(data)
        }

        confirmVerified(photosObserver)
    }
}

object ApiTestUtil {
    suspend fun <T : Any> successCall(data: T) = createCall(Response.success(data))

    private suspend fun <T : Any> createCall(response: Response<T>) = (CompletableDeferred<ApiResponse<T>>().apply {
        this.complete(ApiResponse.create(response))
    } as Deferred<ApiResponse<T>>).await()
}

object FakeData {
    fun createFakePostResponses(count: Int): List<PostResponse> {
        return (0 until count).map {
            createFakePostResponse(it)
        }
    }

    fun createFakePhotoResponses(count: Int): List<PhotoResponse> {
        return (0 until count).map {
            createFakePhotosResponse(it)
        }
    }

    private fun createFakePostResponse(id: Int): PostResponse {
        return PostResponse(
                id = id,
                userId = 1,
                title = "Title$id",
                body = "Body$id"
        )
    }

    private fun createFakePhotosResponse(id: Int): PhotoResponse {
        return PhotoResponse(
                id = id, albumId = 1,
                title = "Title$id",
                url = "Url$id",
                thumbnailUrl = "ThumbnailUrl$id"
        )
    }
}