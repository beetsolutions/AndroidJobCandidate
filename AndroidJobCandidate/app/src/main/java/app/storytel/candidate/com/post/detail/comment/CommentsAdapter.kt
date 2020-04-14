package app.storytel.candidate.com.post.detail.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.storytel.candidate.com.databinding.RowItemCommentBinding
import app.storytel.candidate.com.post.domain.data.local.model.Comment

class CommentsAdapter : RecyclerView.Adapter<CommentsViewHolder>() {
    private val comments: MutableList<Comment> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder = CommentsViewHolder(RowItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun getItemCount(): Int = comments.size
    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) = holder.bind(comments[position])

    fun setItems(items: List<Comment>) {
        val diffCallback = CommentsDiffCallback(this.comments, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.comments.clear()
        this.comments.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }
}