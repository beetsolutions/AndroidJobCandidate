package app.storytel.candidate.com.post.domain.data.remote.api

import android.content.Context
import android.net.ConnectivityManager
import app.storytel.candidate.com.common.util.isConnectionOn
import app.storytel.candidate.com.post.domain.data.remote.di.remoteModule
import app.storytel.candidate.com.post.domain.data.remote.model.ApiResponse
import app.storytel.candidate.com.post.domain.data.remote.model.PhotoResponse
import app.storytel.candidate.com.post.domain.data.remote.model.PostResponse
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import java.io.File
import java.net.HttpURLConnection

class PostServiceTest : KoinTest {

    private val postService: PostService by inject()

    private lateinit var mockServer: MockWebServer

    private val context = mockk<Context>(relaxed = true)
    private val connectivityManager =  mockk<ConnectivityManager>(relaxed = true)

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()

        with(context) {
            every {
                isConnectionOn()
            } returns true
        }

        every {
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
        } returns connectivityManager

        startKoin {
            modules(listOf(remoteModule(mockServer.url("/").toString(), context)))
        }
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
        stopKoin()
    }

    private fun mockHttpResponse(mockServer: MockWebServer, fileName: String, code: Int) = mockServer.enqueue(
            MockResponse()
                    .setResponseCode(code)
                    .setBody(json(fileName)))

    private fun json(path: String): String {
        val uri = this.javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    @Test
    fun `Get list of photos`() {
        mockHttpResponse(mockServer, "photos.json", HttpURLConnection.HTTP_OK)

        runBlocking {
            val response = postService.getAllPhotosAsync().await() as ApiResponse.ApiSuccessResponse<List<PhotoResponse>>

            val photos = response.body
            Assert.assertEquals(3, photos.size)
            Assert.assertEquals(1, photos.first().id)
            Assert.assertEquals(1, photos.first().albumId)
            Assert.assertEquals("accusamus beatae ad facilis cum similique qui sunt", photos.first().title)
            Assert.assertEquals("https://via.placeholder.com/600/92c952", photos.first().url)
            Assert.assertEquals("https://via.placeholder.com/150/92c952", photos.first().thumbnailUrl)
        }
    }

    @Test
    fun `Get list of posts`() {
        mockHttpResponse(mockServer, "posts.json", HttpURLConnection.HTTP_OK)

        runBlocking {
            val response = postService.getAllPostsAsync().await() as ApiResponse.ApiSuccessResponse<List<PostResponse>>

            val photos = response.body
            Assert.assertEquals(3, photos.size)
            Assert.assertEquals(1, photos.first().id)
            Assert.assertEquals(1, photos.first().userId)
            Assert.assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", photos.first().title)
            Assert.assertEquals("quia et suscipit\n" +
                    "suscipit recusandae consequuntur expedita et cum\n" +
                    "reprehenderit molestiae ut ut quas totam\n" +
                    "nostrum rerum est autem sunt rem eveniet architecto", photos.first().body)
        }
    }
}