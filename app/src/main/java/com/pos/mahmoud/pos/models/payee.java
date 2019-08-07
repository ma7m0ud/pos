package com.pos.mahmoud.pos.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity
public class payee implements Serializable {

    @NonNull
    @PrimaryKey
    private String payeeName;

    private String payeeId;

    public payee() {
    }

    public payee(String payeeName, String payeeId) {
        this.payeeName = payeeName;
        this.payeeId = payeeId;
    }

    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }
}
