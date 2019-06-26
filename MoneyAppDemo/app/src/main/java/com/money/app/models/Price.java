package com.money.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Price {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("bids")
    @Expose
    private List<Bid> bids = null;
    @SerializedName("asks")
    @Expose
    private List<Ask> asks = null;
    @SerializedName("closeoutBid")
    @Expose
    private String closeoutBid;
    @SerializedName("closeoutAsk")
    @Expose
    private String closeoutAsk;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tradeable")
    @Expose
    private boolean tradeable;
    @SerializedName("instrument")
    @Expose
    private String instrument;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public List<Ask> getAsks() {
        return asks;
    }

    public void setAsks(List<Ask> asks) {
        this.asks = asks;
    }

    public String getCloseoutBid() {
        return closeoutBid;
    }

    public void setCloseoutBid(String closeoutBid) {
        this.closeoutBid = closeoutBid;
    }

    public String getCloseoutAsk() {
        return closeoutAsk;
    }

    public void setCloseoutAsk(String closeoutAsk) {
        this.closeoutAsk = closeoutAsk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isTradeable() {
        return tradeable;
    }

    public void setTradeable(boolean tradeable) {
        this.tradeable = tradeable;
    }


    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }
}
