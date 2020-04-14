package app.storytel.candidate.com.post.detail.comment

import androidx.recyclerview.widget.DiffUtil
import app.storytel.candidate.com.post.domain.data.local.model.Comment

class CommentsDiffCallback(private val oldList: List<Comment>,
                           private val newList: List<Comment>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].id == newList[newItemPosition].id
}