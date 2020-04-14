package app.storytel.candidate.com.post

import androidx.recyclerview.widget.DiffUtil
import app.storytel.candidate.com.post.domain.data.local.model.Post

class PostsDiffCallback(private val oldList: List<Post>,
                        private val newList: List<Post>): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].id == newList[newItemPosition].id
}