package app.storytel.candidate.com.api

import app.storytel.candidate.com.dataModel.CommentModel
import app.storytel.candidate.com.dataModel.PhotoModel
import app.storytel.candidate.com.dataModel.PostModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

// POSTS_URL = "https://jsonplaceholder.typicode.com/posts"
// PHOTOS_URL = "https://jsonplaceholder.typicode.com/photos"
// COMMENTS_URL = "https://jsonplaceholder.typicode.com/posts/{id}/comments"

interface AppApi{
    @GET("posts")
    fun getAllPosts() : Deferred<Response<MutableList<PostModel>>>

    @GET("photos")
    fun getAllPhotos() : Deferred<Response<MutableList<PhotoModel>>>

    @GET("posts/{id}/comments")
    fun getThreeComments(
            @Path("id") id: Int
    ) : Deferred<Response<MutableList<CommentModel>>>
}