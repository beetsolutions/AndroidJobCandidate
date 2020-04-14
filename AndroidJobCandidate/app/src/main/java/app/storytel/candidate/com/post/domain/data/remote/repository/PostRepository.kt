package app.storytel.candidate.com.post.domain.data.remote.repository

import androidx.lifecycle.LiveData
import app.storytel.candidate.com.post.domain.data.local.model.Comment
import app.storytel.candidate.com.post.domain.data.local.model.Photo
import app.storytel.candidate.com.post.domain.data.local.model.Post
import app.storytel.candidate.com.post.domain.data.remote.repository.resource.Resource

interface PostRepository {
    suspend fun getPosts() : LiveData<Resource<List<Post>>>
    suspend fun getPhotos() : LiveData<Resource<List<Photo>>>
    suspend fun getComments(id: Int) : LiveData<Resource<List<Comment>>>
}