package com.pos.mahmoud.pos.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.pos.mahmoud.pos.DataBase.TerminalDatabase;
import com.pos.mahmoud.pos.R;
import com.pos.mahmoud.pos.models.Configration;
import com.pos.mahmoud.pos.models.StatmentItem;
import com.pos.mahmoud.pos.models.TransactionItem;

import com.pos.mahmoud.pos.models.payee;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Print {
    UsbThermalPrinter mUsbThermalPrinter ;
    Bitmap sudaPanLog ;
    Context c;
    Map<String,Integer> ids=new HashMap<>();
    String [] course,formkind;
    public Print(Context c){
        sudaPanLog= BitmapFactory.decodeResource(c.getResources(), R.mipmap.ic_logo);
       // mUsbThermalPrinter.printLogo(sudaPanLog,false);
        mUsbThermalPrinter= new UsbThermalPrinter(c);
this.c=c;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mUsbThermalPrinter.start(0);

                    Setupmap();
                    mUsbThermalPrinter.reset();
                } catch (TelpoException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

   public int getPrinterStatus(){
       try {

             mUsbThermalPrinter.walkPaper(1);
       } catch (TelpoException e) {
           e.printStackTrace();
           return 4;
       }
       return 0;
   }
    public void prentSuccessCustomer(TransactionModel model, TransactionItem type, String name, String expDate, Boolean isReprint){

      Configration config= TerminalDatabase.getInstance(c).daoAccess().fetchConfigration("1");

        try {
            printTransaction(model,type,name,expDate,config,R.string.re_clients,isReprint);
            mUsbThermalPrinter.printString();
            mUsbThermalPrinter.walkPaper(10);

        } catch (TelpoException e) {
            e.printStackTrace();
        }

    }

    public void prentSuccessMerchant(TransactionModel model, TransactionItem type, String name, String expDate){

Configration  config=TerminalDatabase.getInstance(c).daoAccess().fetchConfigration("1");
        try {
            printTransaction(model,type,name,expDate,config,R.string.re_merchant,false);
            mUsbThermalPrinter.printString();
            mUsbThermalPrinter.walkPaper(10);

        } catch (TelpoException e) {
            e.printStackTrace();
        }
    }
    public void printTransaction(TransactionModel model, TransactionItem type, String name, String expDate,Configration config,int printType,Boolean isReprint) throws TelpoException {
        model.setPAN(maskPan(model.getPAN()));
        mUsbThermalPrinter.reset();
        mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);
        mUsbThermalPrinter.printLogo(sudaPanLog,false);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mUsbThermalPrinter.setFontSize(1);
        mUsbThermalPrinter.setLineSpace(10);
        mUsbThermalPrinter.setGray(5);
        mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);
        mUsbThermalPrinter.addString(wrap(c.getString(R.string.ebs)));
        mUsbThermalPrinter.addString(wrap(config.getBankName()));
        mUsbThermalPrinter.addString(wrap(config.getMerchantName()));
        mUsbThermalPrinter.addString(wrap(config.getMerchantAddress()));
        if(isReprint){
            mUsbThermalPrinter.addString(wrap(center(c.getString(R.string.reprintcopy))));
        }
        if(type.getType()==TransactionsType.GET_BILL){
            mUsbThermalPrinter.addString(wrap(center(c.getString(type.getPrintName()))));
        }else{
            mUsbThermalPrinter.addString(wrap(center(c.getString(type.getName()))));
        }

        mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);

        String Date="14-05-2018";
        String Time="11:34:34";
        SimpleDateFormat simpledateformat = new SimpleDateFormat("ddMMyyhhmmss");
        Date date = null;
        try {
            date = simpledateformat.parse(model.getTranDateTime());
            SimpleDateFormat formated = new SimpleDateFormat("yyyy-MM-dd");
            Date=formated.format(date);
            SimpleDateFormat formatedTime = new SimpleDateFormat("hh:mm:ss");
            Time=formatedTime.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_date)+Time+"      "+c.getString(R.string.re_time)+Date ));
        mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_merchant_name)+ config.getClientId()));
        mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_termianlId)+ config.getTerminalId()));

        if(type.getType()!=TransactionsType.CASH_OUT_VOUCHER&&type.getType()!=TransactionsType.CASH_OUT_VOUCHER_AMOUNT&&type.getType()!=TransactionsType.PURCHASE_MOBILE) {
            mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_pan) +model.getPAN()));
            mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_card_holderName) + name));
            mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_exp_date) + expDate));
        }
        if(type.getType()==TransactionsType.PURCHASE_MOBILE){

            mUsbThermalPrinter.addString(wrap(c.getString(R.string.phone_number) +model.getMobileNo()));
        }
        if(type.getType()==TransactionsType.electricity) {
            mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_ref_num) + model.getReferenceNumber()));
        }

        if(type.getType()==TransactionsType.Mobile_bill_payment||type.getType()==TransactionsType.electricity||type.getType()==TransactionsType.Mobile_topUp
                ||type.getType()==TransactionsType.mohe||type.getType()==TransactionsType.mohe_arab ||type.getType()==TransactionsType.Customs||type.getType()==TransactionsType.E15){
            mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_auth_code)+ model.getApprovalCode()));
        }

        if(model.getResponseCode()==0){
            mUsbThermalPrinter.addString(wrap(center(c.getString(R.string.re_success))));
        }else
        {
            mUsbThermalPrinter.addString(wrap(center(c.getString(R.string.re_failed))));
        }
        if(type.getType()==TransactionsType.GET_BILL){

            if(model.getResponseCode()==0) {


                if(type.getColor()==TransactionsType.mohe){
                    mUsbThermalPrinter.addString(wrap(c.getString(R.string.seat_number) + model.getPersonalPaymentInfo().substring(0, model.getPersonalPaymentInfo().indexOf("/"))));
                    mUsbThermalPrinter.addString(wrap(c.getString(R.string.course_ID) + getMoheInfo(model.getPersonalPaymentInfo(), 1, "mohe")));
                    mUsbThermalPrinter.addString(wrap(c.getString(R.string.Form_kind) + getMoheInfo(model.getPersonalPaymentInfo(), 2, "mohe")));
                }else if(type.getColor()==TransactionsType.mohe_arab){
                    HashMap<String,String> adp=new HashMap<String,String>();
                    String[] entries = model.getPersonalPaymentInfo().split("/");
                    mUsbThermalPrinter.addString(wrap(c.getString(R.string.phone_number) +entries[1]));
                    mUsbThermalPrinter.addString(wrap(c.getString(R.string.course_ID) + getMoheInfo(model.getPersonalPaymentInfo(), 2, "moheArab")));
                    mUsbThermalPrinter.addString(wrap(c.getString(R.string.Form_kind) + getMoheInfo(model.getPersonalPaymentInfo(), 3, "moheArab")));
                }if(type.getColor()==TransactionsType.Mobile_bill_payment){
                    mUsbThermalPrinter.addString(wrap(c.getString(R.string.operator) +  model.getBillAddtionalData()));
                    mUsbThermalPrinter.addString(wrap(c.getString(R.string.phone_number) + model.getPersonalPaymentInfo()));
                }
                HashMap<String, String> list = getAdditonalData(model.getAdditionalData());

                ArrayList<payee> adp = new ArrayList<payee>();

                String[] entries = model.getAdditionalData().split(";");
                for (String entry : entries) {
                    String[] keyValue = entry.split("\\u003d");
                    if (keyValue.length > 1)
                        adp.add(new payee(keyValue[0], keyValue[1]));
                }

                for (payee p : adp) {
                    if(p.getPayeeName().equals("InvoiceStatus")){
                        String[] entriess = model.getPersonalPaymentInfo().split("/");
                        mUsbThermalPrinter.addString(wrap(c.getString(R.string.INVOICENUMBER) + entriess[1]));
                        String status="";
                        switch(Integer.parseInt(p.getPayeeId())){
                            case 0:
                                status= "CANCELED";
                                break;
                            case 1:
                                status= "PENDING";
                                break;
                            case 2:
                                status = "PAID";
                                break;
                        }

                        mUsbThermalPrinter.addString(wrap(c.getString(ids.get(p.getPayeeName())) + status));
                    }else{

                        mUsbThermalPrinter.addString(wrap(c.getString(ids.get(p.getPayeeName())) + p.getPayeeId()));
                    }



                }
            }
        }
        if(type.getType()==TransactionsType.mohe){
            if(model.getResponseCode()==0) {
                HashMap<String, String> list = getAdditonalData(model.getAdditionalData());
//            HashMap<String,String> listbill=getAdditonalData(model.getBillAddtionalData());
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.seat_number) + model.getPersonalPaymentInfo().substring(0, model.getPersonalPaymentInfo().indexOf("/"))));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("StudentName(English)")) + list.get("StudentName(English)")));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("StudentName(Arabic)")) + list.get("StudentName(Arabic)")));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.course_ID) + getMoheInfo(model.getPersonalPaymentInfo(), 1, "mohe")));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.Form_kind) + getMoheInfo(model.getPersonalPaymentInfo(), 2, "mohe")));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.e15r) + list.get("ReceiptNumber")));
            }else{
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.seat_number) + model.getPersonalPaymentInfo().substring(0, model.getPersonalPaymentInfo().indexOf("/"))));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.course_ID) + getMoheInfo(model.getPersonalPaymentInfo(), 1, "mohe")));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.Form_kind) + getMoheInfo(model.getPersonalPaymentInfo(), 2, "mohe")));
            }
        }
        if(type.getType()==TransactionsType.mohe_arab){
            if(model.getResponseCode()==0) {
                HashMap<String, String> list = getAdditonalData(model.getAdditionalData());
//            HashMap<String,String> listbill=getAdditonalData(model.getBillAddtionalData());


                mUsbThermalPrinter.addString(wrap(c.getString(R.string.Student_number) + list.get("Student No")));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("StudentName(English)")) + list.get("StudentName(English)")));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("StudentName(Arabic)")) + list.get("StudentName(Arabic)")));
                HashMap<String,String> adp=new HashMap<String,String>();
                String[] entries = model.getPersonalPaymentInfo().split("/");
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.phone_number) +entries[1]));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.course_ID) + getMoheInfo(model.getPersonalPaymentInfo(), 2, "moheArab")));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.Form_kind) + getMoheInfo(model.getPersonalPaymentInfo(), 3, "moheArab")));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.e15r) + list.get("ReceiptNumber")));
            }else{
                HashMap<String,String> adp=new HashMap<String,String>();
                String[] entries = model.getPersonalPaymentInfo().split("/");
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.student_name) +entries[0]));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.phone_number) +entries[1]));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.course_ID) + getMoheInfo(model.getPersonalPaymentInfo(), 2, "moheArab")));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.Form_kind) + getMoheInfo(model.getPersonalPaymentInfo(), 3, "moheArab")));

            }
        }
       // mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_tran_id)+ model.getReferenceNumber()));
        mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_re_num)+ model.getSystemTraceAuditNumber()));
        if(type.getType()==TransactionsType.PURCHASE||type.getType()==TransactionsType.DO_CARD_TRANSFER||type.getType()==TransactionsType.CASH_IN
           ||type.getType()==TransactionsType.mohe||type.getType()==TransactionsType.mohe_arab||type.getType()==TransactionsType.REFUND
                ||type.getType()==TransactionsType.GENERATE_VOUCHER||type.getType()==TransactionsType.CASH_OUT_VOUCHER||type.getType()==TransactionsType.CASH_OUT
                ||type.getType()==TransactionsType.CASH_OUT_VOUCHER_AMOUNT||type.getType()==TransactionsType.PURCHASE_WITH_CASH_BACK||type.getType()==TransactionsType.PURCHASE_MOBILE
                ||type.getType()==TransactionsType.VOUCHER_CASH_IN||type.getType()==TransactionsType.DO_ACCOUNT_TRANSFER) {
            mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_tran_amount) + model.getTranAmount()+" "+c.getString(R.string.sdg)));
        }
        if(model.getTranFee()!=null) {


                mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_tran_fee) + model.getTranFee() + " " + c.getString(R.string.sdg)));

        }
        if(type.getType()==TransactionsType.Mobile_topUp){
            mUsbThermalPrinter.addString(wrap(c.getString(R.string.operator) +  model.getBillAddtionalData()));
            if(model.getResponseCode()==0) {
           if(model.getPayeeId().equals("0010010003")) {
               HashMap<String, String> list = getAdditonalData(model.getAdditionalData());
               mUsbThermalPrinter.addString(wrap(c.getString(R.string.phone_number) + model.getPersonalPaymentInfo()));
               mUsbThermalPrinter.addString(wrap(c.getString(ids.get("ContractNumber")) + list.get("ContractNumber")));
               mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_sub_balance) + list.get("SubNewBalance")));
               mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_tran_id) + list.get("TransactionId")));
               mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_tran_amount) + model.getTranAmount() + " " + c.getString(R.string.sdg)));
           }else{
               mUsbThermalPrinter.addString(wrap(c.getString(R.string.phone_number) + model.getPersonalPaymentInfo()));
               mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_tran_amount) + model.getTranAmount()+" "+c.getString(R.string.sdg)));
           }
            }else{
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.phone_number) + model.getPersonalPaymentInfo()));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_tran_amount) + model.getTranAmount()+" "+c.getString(R.string.sdg)));
            }

        }
        if(type.getType()==TransactionsType.PURCHASE_WITH_CASH_BACK){

            mUsbThermalPrinter.addString(wrap(c.getString(R.string.cashBackAmount) +model.getCashBackAmount()+" "+c.getString(R.string.sdg)));
        }
        if(type.getType()==TransactionsType.DO_CARD_TRANSFER){

            mUsbThermalPrinter.addString(wrap(c.getString(R.string.toCard) +" "+ maskPan(model.getToCard())));
        }
        if(type.getType()==TransactionsType.DO_ACCOUNT_TRANSFER){

            mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_account_no) + model.getToAccount()));
        }
        if(type.getType()==TransactionsType.VOUCHER_CASH_IN){
//to do
            mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_despiut) + model.getDisputeRRN()));
        }
        if(type.getType()==TransactionsType.GENERATE_VOUCHER||type.getType()==TransactionsType.CASH_OUT_VOUCHER
                ||type.getType()==TransactionsType.CASH_OUT_VOUCHER_AMOUNT){
            mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_approval_code) + model.getVoucherNumber()));
        }

        if(type.getType()==TransactionsType.Mobile_bill_payment){
            mUsbThermalPrinter.addString(wrap(c.getString(R.string.operator) +  model.getBillAddtionalData()));
            if(model.getResponseCode()==0) {
                HashMap<String, String> list = getAdditonalData(model.getAdditionalData());

                mUsbThermalPrinter.addString(wrap(c.getString(R.string.phone_number) + model.getPersonalPaymentInfo()));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.status) + list.get("Status")));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_tran_id) + list.get("TransactionNumber")));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_tran_amount) + model.getTranAmount()));

            }else{
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.phone_number) + model.getPersonalPaymentInfo()));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_tran_amount) + model.getTranAmount()));
            }
         }
        if(type.getType()==TransactionsType.E15){
            if(model.getResponseCode()==0) {
                HashMap<String, String> list = getAdditonalData(model.getAdditionalData());
                String[] entries = model.getPersonalPaymentInfo().split("/");
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.INVOICENUMBER) + entries[1]));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("PayerName")) + list.get("PayerName")));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("ServiceName")) + list.get("ServiceName")));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("UnitName")) + list.get("UnitName")));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("ReferenceId")) + list.get("ReferenceId")));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_tran_amount) + model.getTranAmount()));
            }else {
                String[] entries = model.getPersonalPaymentInfo().split("/");
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.INVOICENUMBER) + entries[1]));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_tran_amount) + model.getTranAmount()));
            }

        }
        if(type.getType()==TransactionsType.Customs){
            if(model.getResponseCode()==0) {
                HashMap<String, String> list = getAdditonalData(model.getAdditionalData());
                String[] entries = model.getPersonalPaymentInfo().split("/");
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("BankCode")) + entries[0]));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.Declarant) + entries[1]));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("PaidAmount")) + list.get("PaidAmount")));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("ProcStatus")) + list.get("ProcStatus")));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("ProcError")) + list.get("ProcError")));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("ReceiptNumber")) + list.get("ReceiptNumber")));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("ReceiptSerial")) + list.get("ReceiptSerial")));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.ReceiptDate) + list.get("ReceiptDate")));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("Status")) + list.get("Status")));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.E_15ReceiptNumber) + list.get("E-15ReceiptNumber")));
            }else{
                String[] entries = model.getPersonalPaymentInfo().split("/");
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("BankCode")) + entries[0]));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.Declarant) + entries[1]));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_tran_amount) + model.getTranAmount()));
            }

        }
        if(type.getType()==TransactionsType.electricity){
            if(model.getResponseCode()==0) {

                HashMap<String, String> list = getAdditonalData(model.getAdditionalData());
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("ReceiptNumber")) + list.get("ReceiptNumber")));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("Units")) + list.get("Units")));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.paid_amount) + model.getTranAmount() + " " + c.getString(R.string.sdg)));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("SalesAmount")) + list.get("SaleAmount") + " " + c.getString(R.string.sdg)));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("FixedFee")) + list.get("FixedFee") + " " + c.getString(R.string.sdg)));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("CustomerName")) + list.get("CustomerName")));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("AccountNumber")) + list.get("AccountNumber")));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("MeterNumber")) + list.get("MeterNumber")));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("ReferenceNumber")) + list.get("ReferenceNumber")));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("OperatorMessage")) + list.get("OperatorMessage")));
                mUsbThermalPrinter.addString(wrap("======================================"));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("Token")) + list.get("Token")));
                mUsbThermalPrinter.addString(wrap("======================================"));
            }else{
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("MeterNumber")) + model.getPersonalPaymentInfo()));
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.paid_amount) + model.getTranAmount() + " " + c.getString(R.string.sdg)));
            }
        }

        if(model.getResponseCode()==0) {
            if(type.getType()!=TransactionsType.electricity)
            mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_ref_num) + model.getReferenceNumber()));
        }
        if(model.getResponseCode()==0){
            if(type.getType()==TransactionsType.Mobile_bill_payment||type.getType()==TransactionsType.electricity||type.getType()==TransactionsType.Customs
                    ||type.getType()==TransactionsType.mohe||type.getType()==TransactionsType.mohe_arab||type.getType()==TransactionsType.Mobile_topUp
                    ||type.getType()==TransactionsType.E15||type.getType()==TransactionsType.GET_BILL){

            }else {
                mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_auth_code) + model.getApprovalCode()));
            }
        }else
        {
            mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_failed_code)+ model.getResponseCode()));
            mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_failed_message)+ c.getString(ErrorID.getError(model.getResponseCode()))));
        }
        if(type.getType()==TransactionsType.GET_BALANCE){
            mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_balance) + model.getAdditionalAmount()));
        }
        if(type.getType()==TransactionsType.GET_MINI_STATEMENT){
           // mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);
            mUsbThermalPrinter.addString((centerNine(c.getString(R.string.re_date_min))+ centerNine(c.getString(R.string.re_operationCode))+centerNine(c.getString(R.string.re_tran_amount_min))));
            for(StatmentItem item:model.getMiniStatementRecords()){
                mUsbThermalPrinter.addString((centerNine(StDate(item.getOperationDate()))+ centerNine(item.getOperationCode())+centerNine(item.getOperationAmount()+"("+item.getOperationSign()+") "+c.getString(R.string.sdg))));
            }

        }
        if(type.getType()==TransactionsType.mohe_arab||type.getType()==TransactionsType.mohe){
            if(model.getResponseCode()==0) {
                HashMap<String, String> list = getAdditonalData(model.getAdditionalData());

                mUsbThermalPrinter.addString(wrap("======================================"));
                mUsbThermalPrinter.addString(wrap(c.getString(ids.get("ApplicationID")) + list.get("ApplicationID")));
                mUsbThermalPrinter.addString(wrap("======================================"));
            }
        }
        if(printType!=R.string.re_clients){
            mUsbThermalPrinter.addString(wrap(c.getString(R.string.re_sign)));
        }
        mUsbThermalPrinter.addString(wrap(center(c.getString(printType))));

    }
    public static String center(String string) {
        if(string.length()>=38)
            return string;
        int length=38;
        char pad='*';
        StringBuilder sb = new StringBuilder(length);
        sb.setLength((length - string.length()) / 2);
        sb.append(string);
        sb.setLength(length);
        return sb.toString().replace('\0', pad);
    }
    public static String centerNine(String string) {

        int length=23;
        char pad=' ';
        StringBuilder sb = new StringBuilder(length);
        sb.setLength((length - string.length()) / 2);
        sb.append(string);
        sb.setLength(length);
        return sb.toString().replace('\0', pad);
    }
    public String StDate(String date){
        String day=date.substring(2,4);
        String mon=date.substring(0,2);
        return day+"/"+mon ;
    }
    public String getMoheInfo(String info,int num,String type)
    {
        course=c.getResources().getStringArray(R.array.course_array);
        formkind=c.getResources().getStringArray(R.array.acadmin_array);
        HashMap<String,String> adp=new HashMap<String,String>();
        String[] entries = info.split("/");
        int n=Integer.parseInt(entries[num]);
        if(type=="mohe") {
            if (num == 1)
                return course[n];
            else
                return formkind[n];
        }else {
            if (num == 2)
                return course[n];
            else
                return formkind[n];

        }
    }
    public  String wrap(String s) {
        int max=38;
        Log.d("TextLength",s+"  "+s.length());
        if(s.length()>max) {
          return s.substring(0,37);
        }else{
            return s;
        }
    }
    private  String maskPan(String ccnum)
            {
                if(ccnum==null)
                    return "";
                int total = ccnum.length();
                int startlen=6,endlen = 4;
                int masklen = total-(startlen + endlen) ;
                StringBuffer maskedbuf = new StringBuffer();
                for(int i=0;i<masklen;i++) {
                    maskedbuf.append('*');
                }
                String masked = maskedbuf.toString();
               SharedPreferences pref= c.getSharedPreferences("MyPref", 0); // 0 - for private mode
                String lang=pref.getString("lang", "ar");
               if(lang.equals("ar")) {
                   return ccnum.substring(startlen + masklen, total) + masked + ccnum.substring(0, startlen);
               }else {
                   return ccnum.substring(0, startlen)+ masked + ccnum.substring(startlen + masklen, total) ;

               }
            }
public void StopPrinter(){
    mUsbThermalPrinter.stop();
}
    public void Setupmap(){
        ids.put("BillAmount",R.string.BilledAmount);
        ids.put("UnbillAmount",R.string.UnbilledAmount);
        ids.put("ContractNumber",R.string.ContractNumber);
        ids.put("TotalBill",R.string.TotalBill);
        ids.put("InvoiceDate",R.string.LastInvoiceDate);
        ids.put("LastInvoiceDate",R.string.LastInvoiceDate);
        ids.put("ReferenceId",R.string.ReferenceId);
        ids.put("UnitName",R.string.UnitName);
        ids.put("ServiceName",R.string.ServiceName);
        ids.put("PayerName",R.string.PayerName);
        ids.put("TotalAmount",R.string.TotalAmount);
        ids.put("DueAmount",R.string.DueAmount);
        ids.put("InvoiceExpiry",R.string.InvoiceExpiry);
        ids.put("InvoiceStatus",R.string.InvoiceStatus);
        ids.put("BankCode",R.string.BankCode);
        ids.put("Amount",R.string.Amount);
        ids.put("DeclarantNAME",R.string.DeclarantNAME);
        ids.put("InstanceID",R.string.InstanceID);
        ids.put("ProcStatus",R.string.ProcStatus);
        ids.put("ProcError",R.string.ProcError);
        ids.put("ProcErroe",R.string.ProcError);
        ids.put("Office",R.string.Office);
        ids.put("Declarant",R.string.Declarant);
        ids.put("DeclarantCode",R.string.Declarant);
        ids.put("DECNBER",R.string.DECNBER);
        ids.put("Transaction",R.string.Transaction);
        ids.put("Year",R.string.Year);
        ids.put("DECSER",R.string.DECSER);
        ids.put("SalesAmount",R.string.SalesAmount);
        ids.put("FixedFee",R.string.FixedFee);
        ids.put("Token",R.string.Token);
        ids.put("MeterNumber",R.string.MeterNumber);
        ids.put("Units",R.string.Units);
        ids.put("AccountNumber",R.string.AccountNumber);
        ids.put("ReceiptNumber",R.string.ReceiptNumber);
        ids.put("CustomerName",R.string.CustomerName);
        ids.put("ReferenceNumber",R.string.ReferenceNumber);
        ids.put("OperatorMessage",R.string.OperatorMessage);
        ids.put("StudentName(English)",R.string.studentnameenglish);
        ids.put("StudentName(Arabic)",R.string.studentnamearabic);
        ids.put("ApplicationID",R.string.application_id);
        ids.put("dueAmount",R.string.DueAmount);
        ids.put("Status",R.string.status);
        ids.put("PaidAmount",R.string.paid_amount);
        ids.put("RegistrationNumber",R.string.RegistrationNumber);
        ids.put("RegistrationSerial",R.string.RegistrationSerial);
        ids.put("ReceiptSerial",R.string.ReceiptSerial);
        ids.put("ReceiptDate",R.string.ReceiptDate);
        ids.put("Student No",R.string.Student_number);
    }

    public HashMap<String,String> getAdditonalData(String Data){
        HashMap<String,String> adp=new HashMap<String,String>();
        String[] entries = Data.split(";");
        for (String entry : entries) {
            String[] keyValue = entry.split("=");
            if(keyValue.length>1)
            adp.put(keyValue[0],keyValue[1]);
            else
                adp.put(keyValue[0],"");
        }
        return adp;
    }

    public void printSummery(){

        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("ddMMyy");
        String Date = simpledateformat.format(calander.getTime());
        List<TransactionModel> models=TerminalDatabase.getInstance(c).daoAccess().fetchAllTransactionModel(Date+"%");
        Configration  config=TerminalDatabase.getInstance(c).daoAccess().fetchConfigration("1");

        try {
            mUsbThermalPrinter.reset();
            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);
            mUsbThermalPrinter.printLogo(sudaPanLog,false);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mUsbThermalPrinter.setFontSize(1);
            mUsbThermalPrinter.setLineSpace(10);
            mUsbThermalPrinter.setGray(5);
            mUsbThermalPrinter.addString(wrap(c.getString(R.string.ebs)));
            mUsbThermalPrinter.addString(wrap(config.getBankName()));
            mUsbThermalPrinter.addString(wrap(config.getMerchantName()));
            mUsbThermalPrinter.addString(wrap(config.getMerchantAddress()));
            mUsbThermalPrinter.addString(wrap(center("Details  Report")));
            mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);
            mUsbThermalPrinter.addString((("Transaction Name")+"   "+ (c.getString(R.string.re_pan))+"   "+(c.getString(R.string.re_auth_code))));
            mUsbThermalPrinter.addString(((c.getString(R.string.re_tran_amount_min))+"   "+ (c.getString(R.string.re_date_min))+"   "+(c.getString(R.string.receipt_number))));
            for(TransactionModel model:models){
                mUsbThermalPrinter.addString(((c.getString(Transactions.getInstance().getItemByType(model.getItem().getType()).getName()))+"   "+
                        (maskPan(model.getPAN()))+"   "+(model.getApprovalCode())));
                mUsbThermalPrinter.addString(((model.getTranAmount()+"")+"   "+ (model.getTranDateTime())+"   "+(model.getSystemTraceAuditNumber()+"")));
                mUsbThermalPrinter.addString("--------------------------------------");

            }

            mUsbThermalPrinter.printString();
            mUsbThermalPrinter.walkPaper(10);

        } catch (TelpoException e) {
            e.printStackTrace();
        }
    }

}
