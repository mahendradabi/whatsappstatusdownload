package heartbeatofindia.heartbeatofindia.dbroom;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey
    public int cid;

    @ColumnInfo(name = "parent_id")
    public int parentId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "cat_img")
    public String catImg;

    @ColumnInfo(name = "status")
    public int status;

    @ColumnInfo(name = "sub_cat_status")
    public int subCatStatus;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
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

    public String getCatImg() {
        return catImg;
    }

    public void setCatImg(String catImg) {
        this.catImg = catImg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSubCatStatus() {
        return subCatStatus;
    }

    public void setSubCatStatus(int subCatStatus) {
        this.subCatStatus = subCatStatus;
    }
}
