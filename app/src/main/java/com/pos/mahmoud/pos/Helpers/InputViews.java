package com.pos.mahmoud.pos.Helpers;

import android.text.InputType;

import com.pos.mahmoud.pos.R;

import java.util.ArrayList;

public class InputViews {
    private ArrayList<InputView> views;
    private static final InputViews ourInstance = new InputViews();
    public static InputViews getInstance() {
        return ourInstance;
    }
    public InputViews() {
      views=  new ArrayList<InputView>();
        views.add(new InputView(ViewId.TRANSACTION_AMOUNT_VIEW, R.string.Transaction_amount,R.string.Transaction_amount_error, InputType.TYPE_CLASS_NUMBER));
        views.add(new InputView(ViewId.CASH_BACK_AMOUNT_VIEW, R.string.cashBackAmount,R.string.cashBackAmount_error, InputType.TYPE_CLASS_NUMBER));
        views.add(new InputView(ViewId.INVOICENUMBER_View, R.string.INVOICENUMBER,R.string.INVOICENUMBER_error, InputType.TYPE_CLASS_NUMBER));
        views.add(new InputView(ViewId.meter_number_View, R.string.meter_number,R.string.meter_number_error, InputType.TYPE_CLASS_NUMBER));
        views.add(new InputView(ViewId.newPIN_View, R.string.newPIN,R.string.newPIN_error, InputType.TYPE_NUMBER_VARIATION_PASSWORD));
        views.add(new InputView(ViewId.con_newPIN_View, R.string.con_newPIN,R.string.con_newPIN_error, InputType.TYPE_NUMBER_VARIATION_PASSWORD));
        views.add(new InputView(ViewId.originalTranSystemTraceAuditNumber_View, R.string.originalTranSystemTraceAuditNumber,R.string.originalTranSystemTraceAuditNumber_error, InputType.TYPE_CLASS_NUMBER));
        views.add(new InputView(ViewId.phone_number_View, R.string.phone_number,R.string.phone_number_error, InputType.TYPE_CLASS_NUMBER));
        views.add(new InputView(ViewId.seat_number_View, R.string.seat_number,R.string.seat_number_error, InputType.TYPE_CLASS_NUMBER));
        views.add(new InputView(ViewId.serviceId_View, R.string.serviceId,R.string.serviceId_error, InputType.TYPE_CLASS_NUMBER));
        views.add(new InputView(ViewId.student_name_View, R.string.student_name,R.string.student_name_error, InputType.TYPE_CLASS_TEXT));
        views.add(new InputView(ViewId.toCard_View, R.string.toCard,R.string.toCard_error, InputType.TYPE_CLASS_NUMBER));
        views.add(new InputView(ViewId.voucherNumber_View, R.string.voucherNumber,R.string.voucherNumber_error, InputType.TYPE_CLASS_NUMBER));
        views.add(new InputView(ViewId.bank_code_view, R.string.bank_code,R.string.bank_code_error, InputType.TYPE_CLASS_NUMBER));
        views.add(new InputView(ViewId.dec_num_View, R.string.Declarant,R.string.Declarant_error, InputType.TYPE_CLASS_NUMBER));
        views.add(new InputView(ViewId.to_account_view, R.string.toaccount,R.string.toaccount_error, InputType.TYPE_CLASS_NUMBER));

    }
public InputView findViewById(int id){
        for (InputView v:views){
            if(v.id==id){
                return v;
            }
        }
        return null;
}
    class InputView{
        private int id;
        private int title;
        private int errorMessage;
        private int inputType;

        public InputView(int id, int title, int errorMessage, int inputType) {
            this.id = id;
            this.title = title;
            this.errorMessage = errorMessage;
            this.inputType = inputType;
        }

        public int getInputType() {
            return inputType;
        }

        public void setInputType(int inputType) {
            this.inputType = inputType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTitle() {
            return title;
        }

        public void setTitle(int title) {
            this.title = title;
        }

        public int getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(int errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
