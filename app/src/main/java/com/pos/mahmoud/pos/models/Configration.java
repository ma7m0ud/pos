package com.pos.mahmoud.pos.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Configration {
    @NonNull
    @PrimaryKey
    private String id;
    private String ip;
    private String port;
    private String path;
    private String timeout;
    private String TMK;
    private String TWK;
    private String clientId;
    private String terminalId;
    private int  systemTraceAuditNumber;
    private String bankName;
    private String merchantName;
    private String merchantAddress;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getTMK() {
        return TMK;
    }

    public void setTMK(String TMK) {
        this.TMK = TMK;
    }

    public String getTWK() {
        return TWK;
    }

    public void setTWK(String TWK) {
        this.TWK = TWK;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public int getSystemTraceAuditNumber() {
        return systemTraceAuditNumber;
    }

    public void setSystemTraceAuditNumber(int systemTraceAuditNumber) {
        this.systemTraceAuditNumber = systemTraceAuditNumber;
    }
}
