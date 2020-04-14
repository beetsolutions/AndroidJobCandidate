package app.storytel.candidate.com.post.domain.data.remote.model

data class PostResponse(
        val userId: Int,
        val id: Int,
        val title: String,
        val body: String
)