package heartbeatofindia.heartbeatofindia.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostAdModel {

    @SerializedName("select_option")
    @Expose
    private String select_option;

    @SerializedName("select_id")
    @Expose
    private String select_id;

    public String getSelect_id() {
        return select_id;
    }

    public void setSelect_id(String select_id) {
        this.select_id = select_id;
    }

    public String getSelect_option() {
        return select_option;
    }

    public void setSelect_option(String select_option) {
        this.select_option = select_option;
    }
}
