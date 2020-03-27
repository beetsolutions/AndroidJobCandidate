package app.storytel.candidate.com.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.storytel.candidate.com.R
import app.storytel.candidate.com.dataModel.PhotoModel
import app.storytel.candidate.com.dataModel.PostModel
import app.storytel.candidate.com.dataModel.PostsAndPhotos
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.post_item.view.*
import kotlin.random.Random

class PostAdapterNew(
        private val context: Context,
        private var postAndPhotoList: PostsAndPhotos,
        private val clickListener: (PostModel,PhotoModel,Int) -> Unit
) : RecyclerView.Adapter<PostAdapterNew.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.post_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = postAndPhotoList.posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postItem = postAndPhotoList.posts[position]
        val photoItem = postAndPhotoList.photos[position]
        holder.itemView.apply {
            holder.itemView.title.text = postItem.title
            holder.itemView.body.text = postItem.body
            setOnClickListener { clickListener(postItem,photoItem, position) }
        }

        val photoIndex = Random.nextInt(postAndPhotoList.photos.size - 1)
        val photoUrl = postAndPhotoList.photos[photoIndex].thumbnailUrl

        Glide.with(context)
                .load(photoUrl)    // photo can't be displayed although photoUrl matches with source data
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter()
                .centerCrop()
                .into(holder.itemView.image)
    }
}