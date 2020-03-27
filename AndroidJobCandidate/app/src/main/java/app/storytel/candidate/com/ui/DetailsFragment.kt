package app.storytel.candidate.com.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import app.storytel.candidate.com.R
import app.storytel.candidate.com.extensions.setVisibleOrGone
import app.storytel.candidate.com.utils.ShowAlertDialog
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.comment_view.view.*
import kotlinx.android.synthetic.main.fragment_details.*
import javax.inject.Inject


class DetailsFragment : DaggerFragment() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    private var position: Int = -1
    private var title: String? = null
    private var imageUrl: String? = null
    private var body: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments?.getInt(ARGS_POSITION, -1) ?: return
        if (position < 0) return

        imageUrl = arguments?.getString(ARGS_IMAGE_URL, "") ?: return
        Picasso.with(context).load(imageUrl).into(postImage)

        title = arguments?.getString(ARGS_TITLE_TEXT, "") ?: return
        titleText.text = title ?: return

        body = arguments?.getString(ARGS_BODY_TEXT, "") ?: return
        bodyText.text = body ?: return

        showComments()
        showCommentLoading()

        val commentsAlert = ShowAlertDialog(requireContext(),showComments())
        showCommentTimeout(commentsAlert)
    }

    private fun showComments() {
        mainViewModel.selectedPostIdLiveData.observe(
                viewLifecycleOwner,
                Observer { selectedPostPosition ->
                    selectedPostPosition?.let {
                        mainViewModel.getComments(it)

                        mainViewModel.commentsLiveData.observe(
                                viewLifecycleOwner,
                                Observer { comments ->
                                    comments?.let {
                                        comments.sortByDescending { it.id }
                                        commentOne.commentTitleText.text = comments[0].name
                                        commentOne.commentBodyText.text = comments[0].body
                                        commentOne.commentIdText.text = getString(R.string.commentId, comments[0].id.toString())

                                        commentTwo.commentTitleText.text = comments[1].name
                                        commentTwo.commentBodyText.text = comments[1].body
                                        commentTwo.commentIdText.text = getString(R.string.commentId, comments[1].id.toString())

                                        commentThree.commentTitleText.text = comments[2].name
                                        commentThree.commentBodyText.text = comments[2].body
                                        commentThree.commentIdText.text = getString(R.string.commentId, comments[2].id.toString())
                                    }
                                })
                    }
                })
    }

    private fun showCommentLoading() {
        mainViewModel.isLoadingComments.observe(viewLifecycleOwner, Observer { isLoading ->
            commentsProgressbar.setVisibleOrGone(isLoading)
            commentOne.setVisibleOrGone(!isLoading)
            commentTwo.setVisibleOrGone(!isLoading)
            commentThree.setVisibleOrGone(!isLoading)
        })
    }

    private fun showCommentTimeout(alert: AlertDialog) {
        mainViewModel.isTimeoutComments.observe(viewLifecycleOwner, Observer { isTimeout ->
            if (isTimeout==true) {
                alert.show()
            } else {
                alert.hide()
            }
        })
    }

    companion object {
        private const val ARGS_POSITION = "args_position"
        private const val ARGS_TITLE_TEXT = "args_title_text"
        private const val ARGS_IMAGE_URL = "args_image_url"
        private const val ARGS_BODY_TEXT = "args_body_text"

        fun newInstance(
                position: Int,
                titleText: String?,
                imageUrl: String?,
                bodyText: String?
        ) =
                DetailsFragment().apply {
                    arguments = Bundle().apply {
                        putInt(
                                ARGS_POSITION, position
                        )
                        putString(
                                ARGS_TITLE_TEXT, titleText
                        )
                        putString(
                                ARGS_IMAGE_URL, imageUrl
                        )
                        putString(
                                ARGS_BODY_TEXT, bodyText
                        )
                    }
                }
    }

}
