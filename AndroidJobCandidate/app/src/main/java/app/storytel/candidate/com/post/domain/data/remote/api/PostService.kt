package app.storytel.candidate.com.post.domain.data.remote.api

import app.storytel.candidate.com.post.domain.data.remote.model.ApiResponse
import app.storytel.candidate.com.post.domain.data.remote.model.CommentResponse
import app.storytel.candidate.com.post.domain.data.remote.model.PhotoResponse
import app.storytel.candidate.com.post.domain.data.remote.model.PostResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Endpoints describing the Posts, Comments and Photos API
 */
interface PostService {

    @GET("posts")
    fun getAllPostsAsync(): Deferred<ApiResponse<List<PostResponse>>>

    @GET("photos")
    fun getAllPhotosAsync(): Deferred<ApiResponse<List<PhotoResponse>>>

    @GET("posts/{id}/comments")
    fun getCommentsAsync(@Path("id") id: Int): Deferred<ApiResponse<List<CommentResponse>>>
}