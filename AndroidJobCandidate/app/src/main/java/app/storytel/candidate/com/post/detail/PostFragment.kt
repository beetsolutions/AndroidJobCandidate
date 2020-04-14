package app.storytel.candidate.com.post.detail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import app.storytel.candidate.com.R
import app.storytel.candidate.com.common.base.BaseFragment
import app.storytel.candidate.com.databinding.FragmentPostBinding
import app.storytel.candidate.com.post.PostsFragmentArgs
import app.storytel.candidate.com.post.detail.comment.CommentsAdapter
import app.storytel.candidate.com.post.domain.data.remote.repository.resource.Resource
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel

class PostFragment : BaseFragment<FragmentPostBinding, PostViewModel>() {

    private val args: PostsFragmentArgs by navArgs()
    private val commentsAdapter = CommentsAdapter()

    override val viewModel: PostViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_post

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.setupPost(args.post)
    }

    override fun onReady() {
        super.onReady()

        Picasso.get()
                .load(args.post.url)
                .fit()
                .noPlaceholder()
                .into(binding.image)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = commentsAdapter
            setHasFixedSize(true)
        }

        viewModel.comments.observe(this, Observer {
            binding.resource = it
            if (it.status == Resource.Status.SUCCESS) {
                it.data?.let { list ->
                    commentsAdapter.setItems(list)
                }
            }
        })
    }
}