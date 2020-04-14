package app.storytel.candidate.com.post

import app.storytel.candidate.com.R
import app.storytel.candidate.com.common.base.BaseFragment
import app.storytel.candidate.com.databinding.FragmentPostsBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PostsFragment : BaseFragment<FragmentPostsBinding, PostsViewModel>() {
    override val viewModel: PostsViewModel by  viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_posts
}