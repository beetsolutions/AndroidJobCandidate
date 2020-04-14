package app.storytel.candidate.com.post.domain.interactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import app.storytel.candidate.com.post.domain.data.local.model.Photo
import app.storytel.candidate.com.post.domain.data.remote.repository.PostRepository
import app.storytel.candidate.com.post.domain.data.remote.repository.resource.Resource

/**
 * Use case for getting photos
 */
class GetPhotosUseCase(private val postRepository: PostRepository) {
    suspend operator fun invoke(): LiveData<Resource<List<Photo>>> {
        return Transformations.map(postRepository.getPhotos()) {
            it
        }
    }
}