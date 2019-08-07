package com.pos.mahmoud.pos.Helpers;

import android.graphics.Color;

import com.pos.mahmoud.pos.R;
import com.pos.mahmoud.pos.models.TransactionItem;


import java.util.ArrayList;

public class Transactions {
    private static final Transactions ourInstance = new Transactions();
    private ArrayList<TransactionItem>  msgList;
  public static Transactions getInstance() {
        return ourInstance;
    }
    private Transactions() {
        msgList = new ArrayList<TransactionItem>();
        msgList.add(new TransactionItem(R.string.Purchase, Color.parseColor("#c62828"),TransactionsType.PURCHASE,"purchase"));
        msgList.add(new TransactionItem(R.string.Purchase_with_cash_back, Color.parseColor("#AD1457"),TransactionsType.PURCHASE_WITH_CASH_BACK,"purchaseWithCashBack"));
        msgList.add(new TransactionItem(R.string.Purchase_Mobile, Color.parseColor("#2E7D32"),TransactionsType.PURCHASE_MOBILE,"purchaseMobile"));
        msgList.add(new TransactionItem(R.string.Refund, Color.parseColor("#558B2F"),TransactionsType.REFUND,"refund "));
        msgList.add(new TransactionItem(R.string.Mobile_topUp, Color.parseColor("#4527A0"),TransactionsType.Mobile_topUp,"prepayBill"));
        msgList.add(new TransactionItem(R.string.Mobile_bill_payment, Color.parseColor("#F9A825"),TransactionsType.Mobile_bill_payment,"payBill",R.string.Mobile_bill_inq));
        msgList.add(new TransactionItem(R.string.electricity, Color.parseColor("#00695C"),TransactionsType.electricity,"prepayBill",R.string.electricity_inq));
        msgList.add(new TransactionItem(R.string.mohe, Color.parseColor("#6A1B9A"),TransactionsType.mohe,"prepayBill",R.string.mohe_inq));
        msgList.add(new TransactionItem(R.string.mohe_arab, Color.parseColor("#1565C0"),TransactionsType.mohe_arab,"prepayBill",R.string.mohe_arab_inq));
        msgList.add(new TransactionItem(R.string.e15, Color.parseColor("#D84315"),TransactionsType.E15,"prepayBill",R.string.e15_inq));
        msgList.add(new TransactionItem(R.string.Customs, Color.parseColor("#AD1457"),TransactionsType.Customs,"payBill",R.string.Customs_inq));
        msgList.add(new TransactionItem(R.string.Balance_Inquiry, Color.parseColor("#9E9D24"),TransactionsType.GET_BALANCE,"getBalance"));
        msgList.add(new TransactionItem(R.string.Mini_Statement, Color.parseColor("#0277BD"),TransactionsType.GET_MINI_STATEMENT,"getMiniStatement"));
        msgList.add(new TransactionItem(R.string.card_ransfer, Color.parseColor("#4527A0"),TransactionsType.DO_CARD_TRANSFER,"doCardTransfer"));
        msgList.add(new TransactionItem(R.string.account_tranfer, Color.parseColor("#283593"),TransactionsType.DO_ACCOUNT_TRANSFER,"doAccountTransfer"));
        msgList.add(new TransactionItem(R.string.generate_voucher, Color.parseColor("#2E7D32"),TransactionsType.GENERATE_VOUCHER,"generateVoucher"));
       // msgList.add(new TransactionItem(R.string.cash_out_voucher, Color.parseColor("#FF8F00"),TransactionsType.CASH_OUT_VOUCHER,"cashOutVoucher"));
        msgList.add(new TransactionItem(R.string.cash_out_voucher_amount, Color.parseColor("#c62828"),TransactionsType.CASH_OUT_VOUCHER_AMOUNT,"cashOutVoucher"));
        msgList.add(new TransactionItem(R.string.Cash_in, Color.parseColor("#1565C0"),TransactionsType.CASH_IN,"cashIn"));
        msgList.add(new TransactionItem(R.string.cash_in_voucher, Color.parseColor("#00695C"),TransactionsType.VOUCHER_CASH_IN,"voucherCashIn"));
        msgList.add(new TransactionItem(R.string.cash_out, Color.parseColor("#F9A825"),TransactionsType.CASH_OUT,"cashOut"));
        msgList.add(new TransactionItem(R.string.pin_change, Color.parseColor("#283593"),TransactionsType.CHANGE_PIN,"changePin"));
        msgList.add(new TransactionItem(R.string.re_print, Color.parseColor("#b71c1c"),TransactionsType.REVERSE,"Reverse"));

    }

    public TransactionItem getItemByType(int type){
     for(TransactionItem m:msgList) {
         if (m.getType()==type){
             return m;
         }
     }
     return null;
     }

    public ArrayList<TransactionItem> getMsgList() {
        return msgList;
    }

    public void setMsgList(ArrayList<TransactionItem> msgList) {
        this.msgList = msgList;
    }


}
