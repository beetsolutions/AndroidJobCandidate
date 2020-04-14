package app.storytel.candidate.com.post.domain.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comment(
        @PrimaryKey
        var id: Int,
        val postId: Int = 0,
        var name: String? = null,
        var email: String? = null,
        var body: String? = null
)