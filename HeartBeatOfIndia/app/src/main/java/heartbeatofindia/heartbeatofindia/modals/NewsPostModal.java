package heartbeatofindia.heartbeatofindia.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsPostModal extends ResponseModal {
    @SerializedName("result")
    @Expose
    private List<NewsPost> posts = null;


    public List<NewsPost> getPosts() {
        return posts;
    }

    public void setPosts(List<NewsPost> posts) {
        this.posts = posts;
    }
}
