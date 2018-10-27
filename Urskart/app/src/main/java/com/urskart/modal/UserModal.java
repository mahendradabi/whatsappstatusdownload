package com.urskart.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModal extends ResponseModal {
    @SerializedName("Userdetail")
    @Expose
    private UserDetailsModel userdetail;

    public UserDetailsModel getUserdetail() {
        return userdetail;
    }

    public void setUserdetail(UserDetailsModel userdetail) {
        this.userdetail = userdetail;
    }
}
