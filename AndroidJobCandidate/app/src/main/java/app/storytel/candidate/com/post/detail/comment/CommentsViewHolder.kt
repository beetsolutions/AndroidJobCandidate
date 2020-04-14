package app.storytel.candidate.com.post.detail.comment

import androidx.recyclerview.widget.RecyclerView
import app.storytel.candidate.com.databinding.RowItemCommentBinding
import app.storytel.candidate.com.post.domain.data.local.model.Comment

/**
 * Binds comment to the UI (Single Responsibility Principle) from S.O.L.I.D
 */
class CommentsViewHolder(private val binding: RowItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(comment: Comment?) {
        comment?.let {
            binding.comment = it
        }
    }
}