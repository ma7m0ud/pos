package com.pos.mahmoud.pos.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pos.mahmoud.pos.Helpers.Transactions;
import com.pos.mahmoud.pos.Helpers.TransactionsType;
import com.pos.mahmoud.pos.R;
import com.pos.mahmoud.pos.activites.ReprintActivity;
import com.pos.mahmoud.pos.activites.ServiceActivity;
import com.pos.mahmoud.pos.models.TransactionItem;

import java.util.ArrayList;

public class RAdapter extends RecyclerView.Adapter<RAdapter.ViewHolder>
{
    Context context;

    ArrayList<TransactionItem> msgList;
    public RAdapter(Context c) {
        context=c;
        msgList = Transactions.getInstance().getMsgList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.row, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RAdapter.ViewHolder viewHolder, final int i) {
        TextView textView = viewHolder.textView;
        viewHolder.row.setBackgroundColor(msgList.get(i).getColor());
        textView.setText(msgList.get(i).getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(msgList.get(i).getType()== TransactionsType.CASH_IN||msgList.get(i).getType()==TransactionsType.REFUND){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(context.getString(R.string.merch_pass));
                    final EditText input = new EditText(context);
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //input.setText(msgList.get(i).getPayeeId());
                    builder.setView(input);

                    builder.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                   SharedPreferences pref= context.getSharedPreferences("MyPref", 0);
                    String pass=pref.getString("pass", "0000");
                    if(pass.equals(input.getText().toString())){
                        Intent intent = new Intent(context, ServiceActivity.class);
                        intent.putExtra("type", msgList.get(i).getType());
                        context.startActivity(intent);
                    }else{
                        Toast.makeText(context, R.string.w_merchant_pass,Toast.LENGTH_SHORT).show();
                    }}});
                    builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }else if(msgList.get(i).getType()==TransactionsType.REVERSE){
                    Intent intent = new Intent(context, ReprintActivity.class);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, ServiceActivity.class);
                    intent.putExtra("type", msgList.get(i).getType());
                    context.startActivity(intent);
                }}
        });
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout row;
        public TextView textView;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            row = (ConstraintLayout) itemView.findViewById(R.id.a_row);
            textView = (TextView) itemView.findViewById(R.id.textView);
            img = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }


}
