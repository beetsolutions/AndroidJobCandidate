package app.storytel.candidate.com.post.domain.interactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import app.storytel.candidate.com.post.domain.data.local.model.Comment
import app.storytel.candidate.com.post.domain.data.remote.repository.PostRepository
import app.storytel.candidate.com.post.domain.data.remote.repository.resource.Resource

/**
 * Use case to get comments by post Id
 */
class GetCommentsByPostId(private val postRepository: PostRepository) {
    suspend operator fun invoke(id: Int): LiveData<Resource<List<Comment>>> {
        return Transformations.map(postRepository.getComments(id)) {
            it
        }
    }
}