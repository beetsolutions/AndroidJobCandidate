package app.storytel.candidate.com.post.domain.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Photo(
        @PrimaryKey
        var id: Int,
        var albumId: Int = 0,
        var title: String? = null,
        var thumbnailUrl: String? = null,
        var url: String? = null
)