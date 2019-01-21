package heartbeatofindia.heartbeatofindia.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("parent_id")
    @Expose
    private int parentId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("sub_cat_status")
    @Expose
    private int sub_cat_status;
    @SerializedName("cat_img")
    @Expose
    private String catImag;

    public void setCatImag(String catImag) {
        this.catImag = catImag;
    }

    public String getCatImag() {
        return catImag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSub_cat_status() {
        return sub_cat_status;
    }

    public void setSub_cat_status(int sub_cat_status) {
        this.sub_cat_status = sub_cat_status;
    }
}
