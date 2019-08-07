package com.pos.mahmoud.pos.adapters;

        import android.content.Context;
        import android.content.DialogInterface;
        import android.support.annotation.NonNull;
        import android.support.constraint.ConstraintLayout;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.pos.mahmoud.pos.DataBase.TerminalDatabase;
        import com.pos.mahmoud.pos.Helpers.Print;
        import com.pos.mahmoud.pos.Helpers.TransactionModel;
        import com.pos.mahmoud.pos.Helpers.Transactions;
        import com.pos.mahmoud.pos.R;
        import com.pos.mahmoud.pos.models.TransactionItem;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.ViewHolder>
{
    Context context;

    ArrayList<TransactionModel> msgList;
    public historyAdapter(Context c,ArrayList<TransactionModel> msgList) {
        context=c;
        this.msgList = msgList;
    }

    public ArrayList<TransactionModel> getMsgList() {
        return msgList;
    }

    public void setMsgList(ArrayList<TransactionModel> msgList) {
        this.msgList = msgList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.his_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(historyAdapter.ViewHolder viewHolder, final int i) {
       final TransactionItem item=Transactions.getInstance().getItemByType(msgList.get(i).getItem().getType());
        if(item==null) {
            viewHolder.type.setText(context.getString(R.string.re_billInq));
        }else{
            viewHolder.type.setText(context.getString(item.getName()));
        }
       String datetime="";
        SimpleDateFormat simpledateformat = new SimpleDateFormat("ddMMyyhhmmss");
        Date date = null;
        try {
            date = simpledateformat.parse(msgList.get(i).getTranDateTime());
            SimpleDateFormat formated = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            datetime=formated.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.date.setText(datetime);
        viewHolder.num.setText(msgList.get(i).getSystemTraceAuditNumber()+"");
        viewHolder.amount.setText(msgList.get(i).getTranAmount()+"");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getString(R.string.d_print));
                builder.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {


                                TransactionModel model= TerminalDatabase.getInstance(context).daoAccess().fetchTransactionModel(msgList.get(i).getSystemTraceAuditNumber());
                                Print print = new Print(context);
                                print.prentSuccessCustomer(model, item, model.getHolderName(),model.getExpDate(),true);
                                print.StopPrinter();

                            }}).start();
                    }
                });
                builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout row;
        public TextView type;
        public TextView date;
        public TextView num;
        public TextView amount;

        public ViewHolder(View itemView) {
            super(itemView);

           // row = (ConstraintLayout) itemView.findViewById(R.id.a_row);
            type = (TextView) itemView.findViewById(R.id.texttype);
            date = (TextView) itemView.findViewById(R.id.textDate);
            num = (TextView) itemView.findViewById(R.id.textnum);
            amount = (TextView) itemView.findViewById(R.id.textamount);}
    }


}
