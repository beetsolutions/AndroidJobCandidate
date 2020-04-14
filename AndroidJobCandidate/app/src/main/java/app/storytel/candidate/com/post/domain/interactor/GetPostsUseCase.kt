package app.storytel.candidate.com.post.domain.interactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import app.storytel.candidate.com.post.domain.data.local.model.Post
import app.storytel.candidate.com.post.domain.data.remote.repository.PostRepository
import app.storytel.candidate.com.post.domain.data.remote.repository.resource.Resource

/**
 * Use case for getting posts
 */
class GetPostsUseCase(private val postRepository: PostRepository) {

    suspend operator fun invoke(): LiveData<Resource<List<Post>>> {
        return Transformations.map(postRepository.getPosts()) {
            it
        }
    }
}