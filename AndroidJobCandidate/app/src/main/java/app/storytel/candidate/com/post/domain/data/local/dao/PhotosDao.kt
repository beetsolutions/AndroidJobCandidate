package app.storytel.candidate.com.post.domain.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import app.storytel.candidate.com.common.base.BaseDao
import app.storytel.candidate.com.post.domain.data.local.model.Photo

@Dao
abstract class PhotosDao  : BaseDao<Photo>() {

    @Query("SELECT * FROM Photo")
    abstract suspend fun getPhotos(): List<Photo>

    suspend fun save(photos: List<Photo>) {
        insert(photos)
    }
}