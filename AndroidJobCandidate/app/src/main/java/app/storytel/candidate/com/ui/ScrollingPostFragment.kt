package app.storytel.candidate.com.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.storytel.candidate.com.R
import app.storytel.candidate.com.extensions.setOrPost
import app.storytel.candidate.com.extensions.setVisibleOrGone
import app.storytel.candidate.com.utils.ShowAlertDialog
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_scrolling_post.*
import javax.inject.Inject

class ScrollingPostFragment : DaggerFragment() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scrolling_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showPostLoading()
        loadPostAndPhotoList()

        val postAlert = ShowAlertDialog(requireContext(), loadPostAndPhotoList())
        showPostTimeout(postAlert)
    }

    private fun showPostLoading() {
        mainViewModel.isLoadingPostsAndPhotos.observe(viewLifecycleOwner, Observer { isLoading ->
            progressbar.setVisibleOrGone(isLoading)
        })
    }

    private fun showPostTimeout(alert: AlertDialog) {
        mainViewModel.isTimeoutPostsAndPhotos.observe(viewLifecycleOwner, Observer { isTimeout ->
            if (isTimeout == true) {
                alert.show()
            } else {
                alert.hide()
            }
        })
    }

    private fun loadPostAndPhotoList() {
        mainViewModel.getPostsAndPhotos()

        mainViewModel.postAndPhotoListLiveData.observe(viewLifecycleOwner, Observer { combinedList ->
            if (combinedList.posts.isNotEmpty() && combinedList.photos.isNotEmpty()) {
                postRecyclerList.layoutManager =
                        LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

                mainViewModel.currentScrollingPositionLiveData.observe(viewLifecycleOwner, Observer {LastShownPosition ->
                    (postRecyclerList.layoutManager as LinearLayoutManager).scrollToPosition(LastShownPosition)
                })

                postRecyclerList.adapter = PostAdapterNew(requireContext(), combinedList) { posts, photos, position ->
                    mainViewModel.selectedPostIdLiveData.setOrPost(posts.id)
                    mainViewModel.getScrollingPosition(position)
                    requireActivity().supportFragmentManager.beginTransaction()
                            .replace(
                                    R.id.fragmentContainer, DetailsFragment.newInstance(
                                    position, posts.title, photos.url, posts.body
                            )
                            )
                            .addToBackStack("first frag")
                            .commit()
                }
                postRecyclerList.adapter?.notifyDataSetChanged()
            }
        })
    }
}
