package app.storytel.candidate.com.post.domain.data.remote.model

data class CommentResponse(
        val postId: Int,
        val id: Int,
        val name: String,
        val email: String,
        val body: String
)