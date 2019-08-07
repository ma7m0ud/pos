package com.pos.mahmoud.pos.Helpers;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.view.ContextThemeWrapper;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pos.mahmoud.pos.DataBase.TerminalDatabase;
import com.pos.mahmoud.pos.R;
import com.pos.mahmoud.pos.models.Configration;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class ServicesActivityHelper {

    private LinearLayout layout;
    private int transactionType;
    private Context context;

    public ServicesActivityHelper(LinearLayout layout, int transactionType,Context context) {
        this.layout = layout;
        this.transactionType = transactionType;
        this.context=context;
    }

    public void setupTransaction( View.OnClickListener listener){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 30, 0, 0);
        //setting up the layout according to the type that was passed to the activity

        if(transactionType==TransactionsType.PURCHASE_MOBILE
                || transactionType==TransactionsType.Mobile_topUp
                || transactionType==TransactionsType.Mobile_bill_payment
                || transactionType==TransactionsType.mohe_arab
                || transactionType==TransactionsType.E15
                || transactionType==TransactionsType.GENERATE_VOUCHER
                || transactionType==TransactionsType.CASH_OUT_VOUCHER
                || transactionType==TransactionsType.CASH_OUT_VOUCHER_AMOUNT
                || transactionType==TransactionsType.VOUCHER_CASH_IN
                )
        {

            layout.addView(getInput(InputViews.getInstance().findViewById(ViewId.phone_number_View)));
        }
        if(       transactionType==TransactionsType.CASH_OUT_VOUCHER
                || transactionType==TransactionsType.CASH_OUT_VOUCHER_AMOUNT
                || transactionType==TransactionsType.VOUCHER_CASH_IN
                )
        {
            layout.addView(getInput(InputViews.getInstance().findViewById(ViewId.voucherNumber_View)));
        }
        if(transactionType==TransactionsType.REVERSE){
            layout.addView(getInput(InputViews.getInstance().findViewById(ViewId.originalTranSystemTraceAuditNumber_View)));
            layout.addView(getInputSpinner(R.array.services_array,R.string.serviceId,ViewId.serviceId_View));

        }
        if(transactionType==TransactionsType.PURCHASE_WITH_CASH_BACK){
            layout.addView(getInput(InputViews.getInstance().findViewById(ViewId.CASH_BACK_AMOUNT_VIEW)));
        }
        if(transactionType==TransactionsType.E15){
            layout.addView(getInput(InputViews.getInstance().findViewById(ViewId.INVOICENUMBER_View)));
        }
        if(transactionType==TransactionsType.Customs){
            layout.addView(getInput(InputViews.getInstance().findViewById(ViewId.bank_code_view)));
            layout.addView(getInput(InputViews.getInstance().findViewById(ViewId.dec_num_View)));
        }
        if(transactionType==TransactionsType.electricity){
            layout.addView(getInput(InputViews.getInstance().findViewById(ViewId.meter_number_View)));
        }
        if(transactionType==TransactionsType.Mobile_bill_payment){
           // layout.addView(getInput(InputViews.getInstance().findViewById(ViewId.meter_number_View)));
            layout.addView(getInputSpinner(R.array.oprators_billpayemnt_array,R.string.operator,ViewId.operator_view));
        }
        if(transactionType==TransactionsType.Mobile_topUp){
            layout.addView(getInputSpinner(R.array.oprators_topup_array,R.string.operator,ViewId.operator_view));
        }
        if(transactionType==TransactionsType.CHANGE_PIN){
            layout.addView(getInput(InputViews.getInstance().findViewById(ViewId.newPIN_View)));
            layout.addView(getInput(InputViews.getInstance().findViewById(ViewId.con_newPIN_View)));
        }
        if(transactionType==TransactionsType.DO_CARD_TRANSFER){
            layout.addView(getInput(InputViews.getInstance().findViewById(ViewId.toCard_View)));
        }
        if(transactionType==TransactionsType.mohe){
            layout.addView(getInput(InputViews.getInstance().findViewById(ViewId.seat_number_View)));
            layout.addView(getInputSpinner(R.array.course_array,R.string.course_ID,ViewId.course_ID_View));
            layout.addView(getInputSpinner(R.array.acadmin_array,R.string.Form_kind,ViewId.Form_kind_View));
        }
        if(transactionType==TransactionsType.DO_ACCOUNT_TRANSFER){
            layout.addView(getInput(InputViews.getInstance().findViewById(ViewId.to_account_view)));
        }
        if(transactionType==TransactionsType.mohe_arab){
            layout.addView(getInput(InputViews.getInstance().findViewById(ViewId.student_name_View)));
            layout.addView(getInputSpinner(R.array.course_array,R.string.course_ID,ViewId.course_ID_View));
            layout.addView(getInputSpinner(R.array.acadmin_array,R.string.Form_kind,ViewId.Form_kind_View));
        }
        if(transactionType== TransactionsType.PURCHASE
                || transactionType==TransactionsType.PURCHASE_WITH_CASH_BACK
                || transactionType==TransactionsType.PURCHASE_MOBILE
                || transactionType==TransactionsType.Mobile_topUp
                || transactionType==TransactionsType.Mobile_bill_payment
                || transactionType==TransactionsType.mohe
                || transactionType==TransactionsType.mohe_arab
                || transactionType==TransactionsType.Customs
                || transactionType==TransactionsType.E15
                || transactionType==TransactionsType.electricity
                || transactionType==TransactionsType.CASH_IN
                || transactionType==TransactionsType.VOUCHER_CASH_IN
                || transactionType==TransactionsType.CASH_OUT
                || transactionType==TransactionsType.REFUND
                || transactionType==TransactionsType.GENERATE_VOUCHER
                || transactionType==TransactionsType.CASH_OUT_VOUCHER_AMOUNT
                || transactionType==TransactionsType.DO_CARD_TRANSFER
                || transactionType==TransactionsType.DO_ACCOUNT_TRANSFER
                )
        {
            layout.addView(getInput(InputViews.getInstance().findViewById(ViewId.TRANSACTION_AMOUNT_VIEW)));
        }
       // layout.addView(getInputSpinner(R.array.planets_array,R.string.Transaction_CurrencyCode));



        //adding the submit button to the end of each view
        Button btn=new Button(context);
        btn.setText(context.getString(R.string.submit));
        btn.setLayoutParams(lp);
        btn.setOnClickListener(listener);
        layout.addView(btn);
    }
    public void submitTransaction(){
        if(transactionType== TransactionsType.PURCHASE){
            String amount=((EditText)layout.findViewById(ViewId.TRANSACTION_AMOUNT_VIEW)).getText().toString();

        }

    }
    public View getInputSpinner(int id,int titleId,int viewId){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 30, 0, 0);
        TextInputLayout amountInputLayout=new TextInputLayout(context);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                id, android.R.layout.simple_spinner_item);
        ContextThemeWrapper wrappedContext = new ContextThemeWrapper(context,R.style.Widget_AppCompat_Spinner_Underlined);
                Spinner spinner = new Spinner(wrappedContext,null,0);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

     spinner.setLayoutParams(lp);
     spinner.setId(viewId);
        amountInputLayout.addView(spinner);
        return amountInputLayout;
    }
    public View getInput(InputViews.InputView view){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 30, 0, 0);

        TextInputLayout amountInputLayout=new TextInputLayout(context);
        EditText text1=new EditText(context);
        if(view.getInputType()== InputType.TYPE_CLASS_TEXT){
            text1.setInputType(InputType.TYPE_CLASS_TEXT);
        }else if(view.getInputType()== InputType.TYPE_NUMBER_VARIATION_PASSWORD){
            text1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        }else{
            text1.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        }
        text1.setHint(view.getTitle());
        text1.setId(view.getId());
        amountInputLayout.addView(text1);
        amountInputLayout.setLayoutParams(lp);
        return amountInputLayout;
    }

    public String pinCalc(String pin,String pan,String TWK){
        String PINPAN=new PINCALC().process(pin,pan);
        byte [] encodedKey=hexStrToByteArray(TerminalDatabase.getInstance(context).daoAccess().fetchConfigration("1").getTMK());
        new PINCALC().getHexString(encodedKey);
        SecretKey key=new SecretKeySpec(encodedKey,0,encodedKey.length, "DES/ECB/NoPadding");
        try {
            Cipher  ecipher = Cipher.getInstance("DES/ECB/NoPadding");
            Cipher  dcipher = Cipher.getInstance("DES/ECB/NoPadding");
            dcipher.init(Cipher.DECRYPT_MODE, key);
            byte [] TPK= decrypt(TWK,dcipher);
            Log.d("ENC",byteArrayToHexStr(TPK));
            byte [] encodedKeydec=TPK;
            ecipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encodedKeydec,0,encodedKeydec.length, "DES/ECB/NoPadding"));
            return encrypt(PINPAN,ecipher);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public String byteArrayToHexStr(byte[] encrypted) {
        StringBuilder hex = new StringBuilder();
        for (byte b : encrypted) {
            hex.append(String.format("%02X", b));
        }
        return new String(hex.toString());
    }
    public byte[] hexStrToByteArray(String hex) {
        hex=hex.replace(" ","");
        ByteArrayOutputStream baos = new ByteArrayOutputStream(hex.length() / 2);

        for (int i = 0; i < hex.length(); i += 2) {
            String output = hex.substring(i, i + 2);
            int decimal = Integer.parseInt(output, 16);
            baos.write(decimal);
        }
        return baos.toByteArray();
    }
    public byte[] decrypt(String str, Cipher  dcipher) throws Exception {
        return dcipher.doFinal( hexStrToByteArray(str));
    }
    public String encrypt(String str,Cipher  ecipher) throws Exception {
        return byteArrayToHexStr(ecipher.doFinal(hexStrToByteArray(str)));
    }

    public TransactionModel getRequestModel(String pin, String pan, Configration configration){
        TransactionModel model=new TransactionModel();
        model.setTerminalId(configration.getTerminalId());
        model.setClientId(configration.getClientId());
        Calendar calander = Calendar.getInstance();
       SimpleDateFormat simpledateformat = new SimpleDateFormat("ddMMyyhhmmss");
       String Date = simpledateformat.format(calander.getTime());

        model.setTranDateTime(Date);
        model.setSystemTraceAuditNumber(configration.getSystemTraceAuditNumber());
        model.setPAN(pan);
        model.setPIN(pin);
        model=getDynamicFileds(model);
        return model;
    }

    public TransactionModel getDynamicFileds(TransactionModel model){


        if(     transactionType==TransactionsType.GENERATE_VOUCHER
                || transactionType==TransactionsType.CASH_OUT_VOUCHER
                || transactionType==TransactionsType.CASH_OUT_VOUCHER_AMOUNT
                )
        {

            model.setPhoneNumber(((EditText)layout.findViewById(ViewId.phone_number_View)).getText().toString());
        }
        if(transactionType==TransactionsType.PURCHASE_MOBILE){
            model.setMobileNo(((EditText)layout.findViewById(ViewId.phone_number_View)).getText().toString());
        }
        if(       transactionType==TransactionsType.CASH_OUT_VOUCHER
                || transactionType==TransactionsType.CASH_OUT_VOUCHER_AMOUNT

                )
        {

            model.setVoucherNumber(((EditText)layout.findViewById(ViewId.voucherNumber_View)).getText().toString());

        }
        if(transactionType==TransactionsType.VOUCHER_CASH_IN){
            model.setVoucherNumber(((EditText)layout.findViewById(ViewId.phone_number_View)).getText().toString());
            model.setApprovalCode(((EditText)layout.findViewById(ViewId.voucherNumber_View)).getText().toString());
        }
        if(transactionType==TransactionsType.REVERSE){
            model.setOriginalSystemTraceAuditNumber(((EditText)layout.findViewById(ViewId.originalTranSystemTraceAuditNumber_View)).getText().toString());
            model.setServiceId(((Spinner)layout.findViewById(ViewId.serviceId_View)).getSelectedItem().toString());

        }
        if(transactionType==TransactionsType.PURCHASE_WITH_CASH_BACK){
            model.setCashBackAmount(Double.parseDouble(((EditText)layout.findViewById(ViewId.CASH_BACK_AMOUNT_VIEW)).getText().toString()));

        }
        if(transactionType==TransactionsType.E15){
            model.setPersonalPaymentInfo("2/"+((EditText)layout.findViewById(ViewId.INVOICENUMBER_View)).getText().toString()+"/"+((EditText)layout.findViewById(ViewId.phone_number_View)).getText().toString());
            model.setPayeeId(TerminalDatabase.getInstance(context).daoAccess().fetchPayee("E15").getPayeeId());

        }
        if(transactionType==TransactionsType.Customs){
            model.setPayeeId(TerminalDatabase.getInstance(context).daoAccess().fetchPayee("CUSTOM Payment").getPayeeId());
            model.setPersonalPaymentInfo(((EditText)layout.findViewById(ViewId.bank_code_view)).getText().toString()+"/"+((EditText)layout.findViewById(ViewId.dec_num_View)).getText().toString());

        }
        if(transactionType==TransactionsType.electricity){
            model.setPayeeId(TerminalDatabase.getInstance(context).daoAccess().fetchPayee("National Electricity Corp.").getPayeeId());
            model.setPersonalPaymentInfo(((EditText)layout.findViewById(ViewId.meter_number_View)).getText().toString());
        }
        if(transactionType==TransactionsType.Mobile_topUp){
            model.setPayeeId(getPayeeIdTopUp(((Spinner)layout.findViewById(ViewId.operator_view)).getSelectedItemPosition()));
            model.setPersonalPaymentInfo(((EditText)layout.findViewById(ViewId.phone_number_View)).getText().toString());

        }
        if(transactionType==TransactionsType.Mobile_bill_payment){
            model.setPayeeId(getPayeeIdBill(((Spinner)layout.findViewById(ViewId.operator_view)).getSelectedItemPosition()));
            model.setPersonalPaymentInfo(((EditText)layout.findViewById(ViewId.phone_number_View)).getText().toString());

        }

        if(transactionType==TransactionsType.CHANGE_PIN){
            model.setNewPIN(((EditText)layout.findViewById(ViewId.newPIN_View)).getText().toString());
        }
        if(transactionType==TransactionsType.DO_CARD_TRANSFER){
            model.setToCard(((EditText)layout.findViewById(ViewId.toCard_View)).getText().toString());

        }
        if(transactionType==TransactionsType.DO_ACCOUNT_TRANSFER){
            model.setToAccount(((EditText)layout.findViewById(ViewId.to_account_view)).getText().toString());

        }
        if(transactionType==TransactionsType.mohe){
            model.setPayeeId(TerminalDatabase.getInstance(context).daoAccess().fetchPayee("Higher Education Admission").getPayeeId());

             String set=((EditText)layout.findViewById(ViewId.seat_number_View)).getText().toString();
            String cours=((Spinner)layout.findViewById(ViewId.course_ID_View)).getSelectedItemPosition()+"";
            String form=((Spinner)layout.findViewById(ViewId.Form_kind_View)).getSelectedItemPosition()+"";
            model.setPersonalPaymentInfo(set+"/"+cours+"/"+formkind(form));
        }
        if(transactionType==TransactionsType.mohe_arab){
            model.setPayeeId(TerminalDatabase.getInstance(context).daoAccess().fetchPayee("Higher Education Admission Arab").getPayeeId());

            String set=((EditText)layout.findViewById(ViewId.student_name_View)).getText().toString();
            String phone=((EditText)layout.findViewById(ViewId.phone_number_View)).getText().toString();
            String cours=((Spinner)layout.findViewById(ViewId.course_ID_View)).getSelectedItemPosition()+"";
            String form=((Spinner)layout.findViewById(ViewId.Form_kind_View)).getSelectedItemPosition()+"";
            model.setPersonalPaymentInfo(set+"/"+phone+"/"+cours+"/"+formkind(form));
        }
        if(transactionType== TransactionsType.PURCHASE
                || transactionType==TransactionsType.PURCHASE_WITH_CASH_BACK
                || transactionType==TransactionsType.PURCHASE_MOBILE
                || transactionType==TransactionsType.Mobile_topUp
                || transactionType==TransactionsType.Mobile_bill_payment
                || transactionType==TransactionsType.mohe
                || transactionType==TransactionsType.mohe_arab
                || transactionType==TransactionsType.Customs
                || transactionType==TransactionsType.E15
                || transactionType==TransactionsType.electricity
                || transactionType==TransactionsType.CASH_IN
                || transactionType==TransactionsType.VOUCHER_CASH_IN
                || transactionType==TransactionsType.CASH_OUT
                || transactionType==TransactionsType.REFUND
                || transactionType==TransactionsType.GENERATE_VOUCHER
                || transactionType==TransactionsType.CASH_OUT_VOUCHER_AMOUNT
                || transactionType==TransactionsType.DO_CARD_TRANSFER
                || transactionType==TransactionsType.DO_ACCOUNT_TRANSFER
                )
        {
            model.setTranAmount(Double.parseDouble(((EditText)layout.findViewById(ViewId.TRANSACTION_AMOUNT_VIEW)).getText().toString()));
            model.setTranCurrencyCode("SDG");
        }



        return model;
    }

    public boolean isValidEditText(EditText text,int type){
      //

        if(text.getId()==ViewId.phone_number_View){
                if(transactionType==TransactionsType.PURCHASE_MOBILE){
                    if(text.getText().toString().length()<=11||!text.getText().toString().startsWith("249")){

                            text.setError(context.getString(R.string.mob_number_error));
                            return false;

                    }
                }else{
            if(text.getText().toString().length()<=9||!text.getText().toString().startsWith("0")){
                text.setError(context.getString(InputViews.getInstance().findViewById(text.getId()).getErrorMessage()));
                return false;
            }}
          }

          if(text.getId()==ViewId.meter_number_View){
             if(text.getText().toString().length()<=10||text.getText().toString().length()>13){
                 text.setError(context.getString(InputViews.getInstance().findViewById(text.getId()).getErrorMessage()));
                 return false;
             }
          }
        if(text.getId()==ViewId.toCard_View){
            if(text.getText().toString().length()<=15||text.getText().toString().length()>19){
                text.setError(context.getString(InputViews.getInstance().findViewById(text.getId()).getErrorMessage()));
                return false;
            }
        }
        if(text.getText().toString().length()<=0){
            text.setError(context.getString(InputViews.getInstance().findViewById(text.getId()).getErrorMessage()));
            return false;
        }
        return true;
    }

    public String formkind(String kindS){
        int kind=Integer.parseInt(kindS);
        if(kind>3){
            kind+=2;
        }
        return kind+"";
    }

    public Boolean isValiedSpinner(Spinner spinner){
        if(spinner.getSelectedItemPosition()<1){
            TextView error=(TextView)spinner.getSelectedView();
            error.setError("");
            error.setTextColor(Color.RED);
            error.setText(R.string.Please_Select_Value);
            return false;
        }else {
            return true;
        }
    }

    public Boolean isValied(){

boolean isValied=true;



        if(transactionType==TransactionsType.PURCHASE_MOBILE
                || transactionType==TransactionsType.mohe_arab
                || transactionType==TransactionsType.E15
                || transactionType==TransactionsType.GENERATE_VOUCHER
                || transactionType==TransactionsType.CASH_OUT_VOUCHER
                || transactionType==TransactionsType.CASH_OUT_VOUCHER_AMOUNT
                )
        {

            if(!isValidEditText(((EditText)layout.findViewById(ViewId.phone_number_View)),((EditText)layout.findViewById(ViewId.phone_number_View)).getInputType())){
                isValied= false;
            }

        }
        if(       transactionType==TransactionsType.CASH_OUT_VOUCHER
                || transactionType==TransactionsType.CASH_OUT_VOUCHER_AMOUNT

                )
        {
            if(!isValidEditText(((EditText)layout.findViewById(ViewId.phone_number_View)),((EditText)layout.findViewById(ViewId.phone_number_View)).getInputType())){
                isValied= false;
            }
            if(!isValidEditText(((EditText)layout.findViewById(ViewId.voucherNumber_View)),((EditText)layout.findViewById(ViewId.voucherNumber_View)).getInputType())){
                isValied= false;
            }
        }
        if(transactionType==TransactionsType.VOUCHER_CASH_IN){
            if(!isValidEditText(((EditText)layout.findViewById(ViewId.phone_number_View)),((EditText)layout.findViewById(ViewId.phone_number_View)).getInputType())){
                isValied= false;
            }if(!isValidEditText(((EditText)layout.findViewById(ViewId.voucherNumber_View)),((EditText)layout.findViewById(ViewId.voucherNumber_View)).getInputType())){
                isValied= false;
        }
        }
        if(transactionType==TransactionsType.REVERSE){
             if(!isValidEditText(((EditText)layout.findViewById(ViewId.originalTranSystemTraceAuditNumber_View)),((EditText)layout.findViewById(ViewId.originalTranSystemTraceAuditNumber_View)).getInputType())){
                 isValied= false;
            }if(!isValidEditText(((EditText)layout.findViewById(ViewId.serviceId_View)),((EditText)layout.findViewById(ViewId.serviceId_View)).getInputType())){
                isValied= false;
            }
        }
        if(transactionType==TransactionsType.PURCHASE_WITH_CASH_BACK){
            if(!isValidEditText(((EditText)layout.findViewById(ViewId.CASH_BACK_AMOUNT_VIEW)),((EditText)layout.findViewById(ViewId.CASH_BACK_AMOUNT_VIEW)).getInputType())){
                isValied= false;
            }
        }
        if(transactionType==TransactionsType.E15){
            if(!isValidEditText(((EditText)layout.findViewById(ViewId.INVOICENUMBER_View)),((EditText)layout.findViewById(ViewId.INVOICENUMBER_View)).getInputType())){
                isValied= false;
            }
        }
        if(transactionType==TransactionsType.Customs){
            if(!isValidEditText(((EditText)layout.findViewById(ViewId.bank_code_view)),((EditText)layout.findViewById(ViewId.bank_code_view)).getInputType())){
                isValied= false;
            }
            if(!isValidEditText(((EditText)layout.findViewById(ViewId.dec_num_View)),((EditText)layout.findViewById(ViewId.dec_num_View)).getInputType())){
                isValied= false;
            }
        }
        if(transactionType==TransactionsType.electricity){
            if(!isValidEditText(((EditText)layout.findViewById(ViewId.meter_number_View)),((EditText)layout.findViewById(ViewId.meter_number_View)).getInputType())){
                isValied= false;
            }
        }
        if(transactionType==TransactionsType.Mobile_topUp||transactionType==TransactionsType.Mobile_bill_payment){
            if(!isValidEditText(((EditText)layout.findViewById(ViewId.phone_number_View)),((EditText)layout.findViewById(ViewId.phone_number_View)).getInputType())){
                isValied= false;
            }
        }
        if(transactionType==TransactionsType.Mobile_topUp||transactionType==TransactionsType.Mobile_bill_payment){
            if(!isValiedSpinner(((Spinner)layout.findViewById(ViewId.operator_view)))){
                isValied= false;
            }
        }


        if(transactionType==TransactionsType.DO_ACCOUNT_TRANSFER){
            if(!isValidEditText(((EditText)layout.findViewById(ViewId.to_account_view)),((EditText)layout.findViewById(ViewId.to_account_view)).getInputType())){
                isValied= false;
            }

        }

        if(transactionType==TransactionsType.CHANGE_PIN){

            if(!isValidEditText(((EditText)layout.findViewById(ViewId.newPIN_View)),((EditText)layout.findViewById(ViewId.newPIN_View)).getInputType())){
                isValied= false;
            }
            if(!isValidEditText(((EditText)layout.findViewById(ViewId.con_newPIN_View)),((EditText)layout.findViewById(ViewId.con_newPIN_View)).getInputType())){
                isValied= false;
            }

            if(!((EditText)layout.findViewById(ViewId.newPIN_View)).getText().toString().equals(((EditText)layout.findViewById(ViewId.con_newPIN_View)).getText().toString())){
                ((EditText)layout.findViewById(ViewId.con_newPIN_View)).setError(context.getString(InputViews.getInstance().findViewById(ViewId.con_newPIN_View).getErrorMessage()));
                isValied=false;
            }


        }
        if(transactionType==TransactionsType.DO_CARD_TRANSFER){

            if(!isValidEditText(((EditText)layout.findViewById(ViewId.toCard_View)),((EditText)layout.findViewById(ViewId.toCard_View)).getInputType())){
                isValied= false;
            }
        }
        if(transactionType==TransactionsType.mohe){
            if(!isValidEditText(((EditText)layout.findViewById(ViewId.seat_number_View)),((EditText)layout.findViewById(ViewId.seat_number_View)).getInputType())){
                isValied= false;
            }
            if(!isValiedSpinner(((Spinner)layout.findViewById(ViewId.course_ID_View)))){
                isValied= false;
            }
            if(!isValiedSpinner(((Spinner)layout.findViewById(ViewId.Form_kind_View)))){
                isValied= false;
            }
         //   model.setPayeeId(TerminalDatabase.getInstance(context).daoAccess().fetchPayee("Higher Education").getPayeeId());

//            layout.addView(getInput(InputViews.getInstance().findViewById(ViewId.seat_number_View)));
//            model.setServiceId(((Spinner)layout.findViewById(ViewId.course_ID_View)).getSelectedItem().toString());
//            model.setServiceId(((Spinner)layout.findViewById(ViewId.Form_kind_View)).getSelectedItem().toString());

        }
        if(transactionType==TransactionsType.mohe_arab){
            if(!isValidEditText(((EditText)layout.findViewById(ViewId.student_name_View)),((EditText)layout.findViewById(ViewId.student_name_View)).getInputType())){
                isValied= false;
            }
            if(!isValiedSpinner(((Spinner)layout.findViewById(ViewId.course_ID_View)))){
                isValied= false;
            }
            if(!isValiedSpinner(((Spinner)layout.findViewById(ViewId.Form_kind_View)))){
                isValied= false;
            }
        }
        if(transactionType== TransactionsType.PURCHASE
                || transactionType==TransactionsType.PURCHASE_WITH_CASH_BACK
                || transactionType==TransactionsType.PURCHASE_MOBILE
                || transactionType==TransactionsType.Mobile_topUp
                || transactionType==TransactionsType.Mobile_bill_payment
                || transactionType==TransactionsType.mohe
                || transactionType==TransactionsType.mohe_arab
                || transactionType==TransactionsType.Customs
                || transactionType==TransactionsType.E15
                || transactionType==TransactionsType.electricity
                || transactionType==TransactionsType.CASH_IN
                || transactionType==TransactionsType.CASH_OUT
                || transactionType==TransactionsType.REFUND
                || transactionType==TransactionsType.GENERATE_VOUCHER
                || transactionType==TransactionsType.CASH_OUT_VOUCHER_AMOUNT
                || transactionType==TransactionsType.DO_CARD_TRANSFER
                || transactionType==TransactionsType.DO_ACCOUNT_TRANSFER
                || transactionType==TransactionsType.VOUCHER_CASH_IN
                )
        {
            if(!isValidEditText(((EditText)layout.findViewById(ViewId.TRANSACTION_AMOUNT_VIEW)),((EditText)layout.findViewById(ViewId.TRANSACTION_AMOUNT_VIEW)).getInputType())){
                isValied= false;
            }
        }



        return isValied;
    }
    public String getPayeeIdTopUp(int pos){
        Log.d("index",pos+"");
       String name="";
        switch (pos){
           case 1:
               name="Zain Top Up";
               break;
            case 2:
                name="MTN Top Up";
                break;
            case 3:
                name="Sudani Top Up";
                break;
       }
       return TerminalDatabase.getInstance(context).daoAccess().fetchPayee(name).getPayeeId();
    }

    public String getPayeeIdBill(int pos){
        String name="";
        switch (pos){
            case 1:
                name="Zain Bill Payment";
                break;
            case 2:
                name="MTN Bill Payment";
                break;
            case 3:
                name="SUDANI Bill Payment";
                break;
        }
        return TerminalDatabase.getInstance(context).daoAccess().fetchPayee(name).getPayeeId();
    }




}
