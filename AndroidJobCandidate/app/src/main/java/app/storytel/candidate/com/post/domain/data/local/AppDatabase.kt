package app.storytel.candidate.com.post.domain.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.storytel.candidate.com.post.domain.data.local.dao.CommentsDao
import app.storytel.candidate.com.post.domain.data.local.dao.PhotosDao
import app.storytel.candidate.com.post.domain.data.local.dao.PostsDao
import app.storytel.candidate.com.post.domain.data.local.model.Comment
import app.storytel.candidate.com.post.domain.data.local.model.Photo
import app.storytel.candidate.com.post.domain.data.local.model.Post

@Database(
        entities = [
            Post::class,
            Photo::class,
            Comment::class
        ],
        version = 1,
        exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postsDao(): PostsDao
    abstract fun photosDao(): PhotosDao
    abstract fun commentsDao(): CommentsDao

    companion object {
        fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "Posts.db")
                        .build()
    }
}