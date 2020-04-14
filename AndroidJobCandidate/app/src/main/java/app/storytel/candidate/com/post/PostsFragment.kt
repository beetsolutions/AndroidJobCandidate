package app.storytel.candidate.com.post

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import app.storytel.candidate.com.R
import app.storytel.candidate.com.common.RetryCallback
import app.storytel.candidate.com.common.base.BaseFragment
import app.storytel.candidate.com.databinding.FragmentPostsBinding
import app.storytel.candidate.com.post.domain.data.remote.repository.resource.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class PostsFragment : BaseFragment<FragmentPostsBinding, PostsViewModel>() {

    private lateinit var postsAdapter : PostsAdapter

    override val viewModel: PostsViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_posts

    override fun onReady() {
        super.onReady()

        postsAdapter = PostsAdapter(viewModel)

        // Interface Segregation Principle from S.O.L.I.D
        binding.retryCallback = object : RetryCallback {
            override fun retry() {
                viewModel.getData()
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postsAdapter
            setHasFixedSize(true)
        }

        viewModel.postsData.observe(this, Observer {
            binding.resource = it
            if (it.status == Resource.Status.SUCCESS) {
                it.data?.let { list ->
                    postsAdapter.setItems(list)
                }
            }
        })
    }
}