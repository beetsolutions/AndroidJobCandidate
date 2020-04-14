package app.storytel.candidate.com.post.domain.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import app.storytel.candidate.com.common.base.BaseDao
import app.storytel.candidate.com.post.domain.data.local.model.Comment

@Dao
abstract class CommentsDao : BaseDao<Comment>() {

    @Query("SELECT * FROM Comment WHERE postId = :postId LIMIT 3")
    abstract suspend fun getComments(postId: Int): List<Comment>

    suspend fun save(comments: List<Comment>) {
        insert(comments)
    }
}