package com.pos.mahmoud.pos.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.mahmoud.pos.R;
import com.pos.mahmoud.pos.models.payee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {

    ArrayList<payee> list;
    Map<String,Integer> ids=new HashMap<>();
    Context c;
    public InfoAdapter(ArrayList<payee> list,Context c) {
        this.list = list;
        this.c=c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Setupmap();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.info_item, parent, false);

        InfoAdapter.ViewHolder viewHolder = new InfoAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            holder.title.setText(c.getString(ids.get(list.get(position).getPayeeName())));
            if(list.get(position).getPayeeName().equals("InvoiceStatus")){
                String status="";
                switch(Integer.parseInt(list.get(position).getPayeeId())){
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
                holder.value.setText(status);
            }else{
                holder.value.setText(list.get(position).getPayeeId());
            }

        }catch (Exception c){
            Log.d("position",position+"");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView value;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            value = (TextView) itemView.findViewById(R.id.value);

        }
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
        ids.put("Student No",R.string.Student_number);
        ids.put("ReceiptDate",R.string.ReceiptDate);
    }

}
