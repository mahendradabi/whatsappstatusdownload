package heartbeatofindia.heartbeatofindia.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdListModel extends ResponseModal {
    @SerializedName("result")
    @Expose
    private List<Ad> adList;

    public List<Ad> getAdList() {
        return adList;
    }

    public void setAdList(List<Ad> adList) {
        this.adList = adList;
    }
}
