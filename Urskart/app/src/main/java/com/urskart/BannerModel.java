package com.urskart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.urskart.modal.Category;
import com.urskart.modal.ResponseModal;

import java.util.List;

public class BannerModel extends ResponseModal{
    @SerializedName("Details")
    @Expose
    private List<Category> bannerList;

    public List<Category> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Category> bannerList) {
        this.bannerList = bannerList;
    }
}
