package app.storytel.candidate.com.repository

import app.storytel.candidate.com.api.AppApi
import app.storytel.candidate.com.dataModel.CommentModel
import app.storytel.candidate.com.dataModel.PhotoModel
import app.storytel.candidate.com.dataModel.PostModel
import javax.inject.Inject

class MainRepository @Inject constructor(
        private val appApi: AppApi
) : BaseRepository() {

    suspend fun getPostList(): MutableList<PostModel>? {
        return safeApiResult(
                call = {
                    appApi.getAllPosts().await()
                },
                error = "Error fetching posts"
        )
    }

    suspend fun getPhotoList(): MutableList<PhotoModel>? {
        return safeApiResult(
                call = {
                    appApi.getAllPhotos().await()
                },
                error = "Error fetching photos"
        )
    }

    suspend fun getCommentList(postId: Int): MutableList<CommentModel>? {
        return safeApiResult(
                call = {
                    appApi.getThreeComments(id = postId).await()
                },
                error = "Error fetching comments"
        )
    }
}