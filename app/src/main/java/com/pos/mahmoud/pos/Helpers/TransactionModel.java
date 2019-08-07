package com.pos.mahmoud.pos.Helpers;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.pos.mahmoud.pos.DataBase.DataConverter;
import com.pos.mahmoud.pos.models.StatmentItem;
import com.pos.mahmoud.pos.models.TransactionItem;
import com.pos.mahmoud.pos.models.payee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TransactionModel implements Serializable {


    private String clientId;
    private String terminalId;
    private String tranDateTime;

    @NonNull
    @PrimaryKey
    private Integer   systemTraceAuditNumber;

    private String PAN;
    private String mobileNo;
    private String PIN;
    private String expDate;
    private String tranCurrencyCode;
    private Double    tranAmount;
    private Double  tranFee;
    private Double   additionalAmount;
    private String   payeeId;
    private String personalPaymentInfo;
    private String    toCard;
    private String toAccount;
   private String   voucherNumber;
    private String newPIN;
    private Double   cashBackAmount;
    private String referenceNumber;
    private Integer  responseCode;
    private String responseMessage;
    private String   responseStatus;
    private Double  aditionalAmount;
    private String  additionalData;
    private Integer  payeesCount;
    private String  workingKey;
    private String originalSystemTraceAuditNumber;
    @Ignore
    private ArrayList<StatmentItem> miniStatementRecords;
    private String serviceId;
    private String phoneNumber;
    private Integer requestedServicesId;
    private String phone;
    private String   referenceId;
    private String  identityType;
    private String Identity;
    private String  description;
    private String paymentDetails;
    private Integer  paymentMethodId;
    private Double totalAmount;
    private Integer discount;
    private Boolean hasVat;
    private String  invoiceNo;
    private String invoiceExpiryDate;
    private Integer invoiceStatus;
    private String unitName;
    private String serviceName;
    private Integer totalAmountInt;
    private String totalInWord;
    private Double dueAmount;
    private String  receiptNo;
    private String e15Status;
    private String availableBalance;
    private Double legerBalance;
    private Integer   disputeRRN;
    private String accountType;
    private String ServiceReference;
    private Double ServiceDueDate;
    private String    ServiceDueAmount;
    private String ServiceDetails;
    private Double  ServiceTotalAmount;
    private String approvalCode;
    @Ignore
    private List<payee> payeesList;
    private String billAddtionalData;
    private   String holderName;
    @TypeConverters(DataConverter.class)
    private TransactionItem item;


    public Integer getTotalAmountInt() {
        return totalAmountInt;
    }

    public Integer getDisputeRRN() {
        return disputeRRN;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public TransactionItem getItem() {
        return item;
    }

    public void setItem(TransactionItem item) {
        this.item = item;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public Double getAdditionalAmount() {
        return additionalAmount;
    }

    public void setAdditionalAmount(Double additionalAmount) {
        this.additionalAmount = additionalAmount;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Integer getPayeesCount() {
        return payeesCount;
    }

    public void setPayeesCount(Integer payeesCount) {
        this.payeesCount = payeesCount;
    }

    public Integer getRequestedServicesId() {
        return requestedServicesId;
    }

    public void setRequestedServicesId(Integer requestedServicesId) {
        this.requestedServicesId = requestedServicesId;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getBillAddtionalData() {
        return billAddtionalData;
    }

    public void setBillAddtionalData(String billAddtionalData) {
        this.billAddtionalData = billAddtionalData;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Boolean getHasVat() {
        return hasVat;
    }

    public void setHasVat(Boolean hasVat) {
        this.hasVat = hasVat;
    }

    public Integer getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Integer invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public void setTotalAmountInt(Integer totalAmountInt) {
        this.totalAmountInt = totalAmountInt;
    }

    public void setDisputeRRN(Integer disputeRRN) {
        this.disputeRRN = disputeRRN;
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

    public String getTranDateTime() {
        return tranDateTime;
    }

    public void setTranDateTime(String tranDateTime) {
        this.tranDateTime = tranDateTime;
    }

    public Integer getSystemTraceAuditNumber() {
        return systemTraceAuditNumber;
    }

    public void setSystemTraceAuditNumber(Integer systemTraceAuditNumber) {
        this.systemTraceAuditNumber = systemTraceAuditNumber;
    }

    public String getPAN() {
        return PAN;
    }

    public void setPAN(String PAN) {
        this.PAN = PAN;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }


    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getTranCurrencyCode() {
        return tranCurrencyCode;
    }

    public void setTranCurrencyCode(String tranCurrencyCode) {
        this.tranCurrencyCode = tranCurrencyCode;
    }

    public Double getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(Double tranAmount) {
        this.tranAmount = tranAmount;
    }

    public Double getTranFee() {
        return tranFee;
    }

    public void setTranFee(Double tranFee) {
        this.tranFee = tranFee;
    }

    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    public String getPersonalPaymentInfo() {
        return personalPaymentInfo;
    }

    public void setPersonalPaymentInfo(String personalPaymentInfo) {
        this.personalPaymentInfo = personalPaymentInfo;
    }

    public String getToCard() {
        return toCard;
    }

    public void setToCard(String toCard) {
        this.toCard = toCard;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }



    public String getNewPIN() {
        return newPIN;
    }

    public void setNewPIN(String newPIN) {
        this.newPIN = newPIN;
    }

    public Double getCashBackAmount() {
        return cashBackAmount;
    }

    public void setCashBackAmount(Double cashBackAmount) {
        this.cashBackAmount = cashBackAmount;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }


    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Double getAditionalAmount() {
        return aditionalAmount;
    }

    public void setAditionalAmount(Double aditionalAmount) {
        this.aditionalAmount = aditionalAmount;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }

    public List<payee> getPayeesList() {
        return payeesList;
    }

    public void setPayeesList(List<payee> payeesList) {
        this.payeesList = payeesList;
    }

    public String getWorkingKey() {
        return workingKey;
    }

    public void setWorkingKey(String workingKey) {
        this.workingKey = workingKey;
    }

    public String getOriginalSystemTraceAuditNumber() {
        return originalSystemTraceAuditNumber;
    }

    public void setOriginalSystemTraceAuditNumber(String originalSystemTraceAuditNumber) {
        this.originalSystemTraceAuditNumber = originalSystemTraceAuditNumber;
    }

    public ArrayList<StatmentItem> getMiniStatementRecords() {
        return miniStatementRecords;
    }

    public void setMiniStatementRecords(ArrayList<StatmentItem> miniStatementRecords) {
        this.miniStatementRecords = miniStatementRecords;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentity() {
        return Identity;
    }

    public void setIdentity(String identity) {
        Identity = identity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }


    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }


    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceExpiryDate() {
        return invoiceExpiryDate;
    }

    public void setInvoiceExpiryDate(String invoiceExpiryDate) {
        this.invoiceExpiryDate = invoiceExpiryDate;
    }


    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Double getServiceTotalAmount() {
        return ServiceTotalAmount;
    }

    public void setServiceTotalAmount(Double serviceTotalAmount) {
        ServiceTotalAmount = serviceTotalAmount;
    }



    public String getTotalInWord() {
        return totalInWord;
    }

    public void setTotalInWord(String totalInWord) {
        this.totalInWord = totalInWord;
    }

    public Double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(Double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public String getE15Status() {
        return e15Status;
    }

    public void setE15Status(String e15Status) {
        this.e15Status = e15Status;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Double getLegerBalance() {
        return legerBalance;
    }

    public void setLegerBalance(Double legerBalance) {
        this.legerBalance = legerBalance;
    }


    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getServiceReference() {
        return ServiceReference;
    }

    public void setServiceReference(String serviceReference) {
        ServiceReference = serviceReference;
    }

    public Double getServiceDueDate() {
        return ServiceDueDate;
    }

    public void setServiceDueDate(Double serviceDueDate) {
        ServiceDueDate = serviceDueDate;
    }

    public String getServiceDueAmount() {
        return ServiceDueAmount;
    }

    public void setServiceDueAmount(String serviceDueAmount) {
        ServiceDueAmount = serviceDueAmount;
    }

    public String getServiceDetails() {
        return ServiceDetails;
    }

    public void setServiceDetails(String serviceDetails) {
        ServiceDetails = serviceDetails;
    }
}
