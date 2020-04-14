package app.storytel.candidate.com.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.storytel.candidate.com.databinding.RowItemPostBinding
import app.storytel.candidate.com.post.domain.data.local.model.Post

class PostsAdapter(private val viewModel: PostsViewModel) : RecyclerView.Adapter<PostsViewHolder>() {
    private val posts: MutableList<Post> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder = PostsViewHolder(RowItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun getItemCount(): Int = posts.size
    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) = holder.bind(posts[position], viewModel)

    fun setItems(items: List<Post>) {
        val diffCallback = PostsDiffCallback(this.posts, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.posts.clear()
        this.posts.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }
}