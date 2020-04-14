package app.storytel.candidate.com.post.domain.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import app.storytel.candidate.com.common.base.BaseDao
import app.storytel.candidate.com.post.domain.data.local.model.Post

@Dao
abstract class PostsDao : BaseDao<Post>() {

    @Query("SELECT * FROM Post")
    abstract suspend fun getPosts(): List<Post>

    suspend fun save(posts: List<Post>) {
        insert(posts)
    }
}