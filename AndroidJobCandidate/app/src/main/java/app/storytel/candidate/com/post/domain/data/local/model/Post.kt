package app.storytel.candidate.com.post.domain.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Post(
        @PrimaryKey
        var id: Int,
        val userId: Int = 0,
        var url: String? = null,
        var thumbNail: String? = null,
        var title: String? = null,
        var body: String? = null) : Parcelable