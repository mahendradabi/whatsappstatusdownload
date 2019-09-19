package heartbeatofindia.heartbeatofindia.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ad {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("categoryid")
    @Expose
    private String categoryid;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
