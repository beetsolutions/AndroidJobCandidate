package app.storytel.candidate.com.oldFilesForComparing;

import java.util.List;

import app.storytel.candidate.com.oldFilesForComparing.Photo;
import app.storytel.candidate.com.oldFilesForComparing.Post;

public class PostAndImages {
    public List<Post> mPosts;
    public List<Photo> mPhotos;

    public PostAndImages(List<Post> post, List<Photo> photos) {
        mPosts = post;
        mPhotos = photos;
    }
}
