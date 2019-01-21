package heartbeatofindia.heartbeatofindia.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserModal extends ResponseModal {
    @SerializedName("result")
    @Expose
    private List<User> userList = null;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
