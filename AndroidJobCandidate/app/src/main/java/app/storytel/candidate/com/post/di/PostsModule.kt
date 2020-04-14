package app.storytel.candidate.com.post.di

import app.storytel.candidate.com.post.PostsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postsModule = module {
    viewModel { PostsViewModel() }
}