package app.storytel.candidate.com.post.di

import app.storytel.candidate.com.post.PostsViewModel
import app.storytel.candidate.com.post.detail.PostViewModel
import app.storytel.candidate.com.post.domain.interactor.GetCommentsByPostId
import app.storytel.candidate.com.post.domain.interactor.GetPhotosUseCase
import app.storytel.candidate.com.post.domain.interactor.GetPostsUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postsModule = module {

    single { GetPostsUseCase(get()) }
    single { GetPhotosUseCase(get()) }
    single { GetCommentsByPostId(get()) }

    viewModel { PostsViewModel(get(), get(), get()) }
    viewModel { PostViewModel(get(), get()) }
}