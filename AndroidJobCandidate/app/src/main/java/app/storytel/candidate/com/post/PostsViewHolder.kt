package app.storytel.candidate.com.post

import androidx.recyclerview.widget.RecyclerView
import app.storytel.candidate.com.databinding.RowItemPostBinding
import app.storytel.candidate.com.post.domain.data.local.model.Post
import com.squareup.picasso.Picasso

/**
 * Binds post to the UI (Single Responsibility Principle) from S.O.L.I.D
 */
class PostsViewHolder(private val binding: RowItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post?, viewModel: PostsViewModel) {
        binding.viewModel = viewModel
        post?.let {
            binding.post = it
            Picasso.get()
                    .load(it.thumbNail)
                    .noPlaceholder()
                    .fit()
                    .into(binding.photo)
        }
    }
}