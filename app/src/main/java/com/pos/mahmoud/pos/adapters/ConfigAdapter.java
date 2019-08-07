package com.pos.mahmoud.pos.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pos.mahmoud.pos.DataBase.TerminalDatabase;
import com.pos.mahmoud.pos.Helpers.TransactionModel;
import com.pos.mahmoud.pos.Network.RequestCallBack;
import com.pos.mahmoud.pos.Network.SendRequest;
import com.pos.mahmoud.pos.R;
import com.pos.mahmoud.pos.models.Configration;
import com.pos.mahmoud.pos.models.TransactionItem;
import com.pos.mahmoud.pos.models.payee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ConfigAdapter extends RecyclerView.Adapter<ConfigAdapter.ViewHolder>
{
    Context context;
    Configration config;
    ArrayList<payee>  msgList;
    public ConfigAdapter(Context c,ArrayList<payee>  map) {
        context=c;
        msgList = map;
    }

    public Configration getConfig() {
        return config;
    }

    public void setConfig(Configration config) {
        this.config = config;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.config_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ConfigAdapter.ViewHolder viewHolder, final int i) {
        TextView label = viewHolder.label;
       final TextView value = viewHolder.data;
        label.setText(context.getString(Integer.parseInt(msgList.get(i).getPayeeName())));

        if((msgList.get(i).getPayeeName()==R.string.tmk+""))
        value.setText("****************");
        else
            value.setText(msgList.get(i).getPayeeId());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Integer.parseInt(msgList.get(i).getPayeeName())==R.string.network_test){
                 doNetworkTest();
                }else{
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getString(Integer.parseInt(msgList.get(i).getPayeeName())));

// Set up the input
                final EditText input = new EditText(context);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                if((msgList.get(i).getPayeeName()==R.string.tmk+"")) {
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else if ((msgList.get(i).getPayeeName()==R.string.sys+""||msgList.get(i).getPayeeName()==R.string.net_timeout+"")){
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                }else{
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                input.setText(msgList.get(i).getPayeeId());
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                               final String in= input.getText().toString();
                                switch (Integer.parseInt(msgList.get(i).getPayeeName())){
                                    case R.string.IP:
                                        config.setIp(in);
                                        break;
                                    case R.string.port:
                                        config.setPort(in);
                                        break;
                                    case R.string.path:
                                        config.setPath(in);
                                        break;
                                    case R.string.net_timeout:
                                        config.setTWK(in);
                                        break;
                                    case R.string.re_termianlId:
                                        config.setTerminalId(in);
                                        break;
                                    case R.string.re_merchant_name:
                                        config.setClientId(in);
                                        break;
                                    case R.string.tmk:
                                        if(in.length()!=16){
                                            ( (Activity) context).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    new AlertDialog.Builder(context).setMessage("Invalid TMK").create().show();
                                                }
                                            });

                                        }else {
                                            config.setTMK(in);
                                        }
                                        break;
                                    case R.string.bankname:
                                        config.setBankName(in);
                                        break;
                                    case R.string.merchantname:
                                        config.setMerchantName(in);
                                        break;
                                    case R.string.merchantaddress:
                                        config.setMerchantAddress(in);
                                        break;
                                    case R.string.sys:
                                        config.setSystemTraceAuditNumber(Integer.parseInt(in));
                                        break;

                                }
                                if(config.getId()!=null) {
                                    TerminalDatabase.getInstance(context).daoAccess().updateConfigration(config);
                                }else {
                                 config.setId("1");
                                    TerminalDatabase.getInstance(context).daoAccess().insertConfigration(config);
                                }
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if((msgList.get(i).getPayeeName()!=R.string.tmk+"")) {
                                            value.setText(in);
                                        }

                                    }
                                });

                            }
                        }).start();


                    }
                });
                builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }}
        });
    }

    private void doNetworkTest() {
        final ProgressDialog dialog = ProgressDialog.show(context, "",
                context.getString(R.string.please_wait), true);
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final TerminalDatabase terminalDatabase=TerminalDatabase.getInstance(context);
                final Configration config = terminalDatabase.daoAccess ().fetchConfigration("1");
                TransactionModel model = new TransactionModel();
                model.setTerminalId(config.getTerminalId());
                model.setClientId(config.getClientId());
                Calendar calander = Calendar.getInstance();
                SimpleDateFormat simpledateformat = new SimpleDateFormat("ddMMyyhhmmss");
                String Date = simpledateformat.format(calander.getTime());
                model.setTranDateTime(Date);
                model.setSystemTraceAuditNumber(config.getSystemTraceAuditNumber());
                config.setSystemTraceAuditNumber(config.getSystemTraceAuditNumber()+1);
                terminalDatabase.daoAccess().updateConfigration(config);

                TransactionItem item=new TransactionItem(0,0,0,"isAlive");
                new SendRequest(config,context).sendPost(model, item, new RequestCallBack() {
                    @Override
                    public void onSuccess(final TransactionModel model) {
                      dialog.dismiss();
                        if(model.getResponseCode()==0){
                            Toast.makeText(context, context.getString(R.string.re_success),Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, context.getString(R.string.re_failed),Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onError() {
                        dialog.dismiss();
                        Toast.makeText(context, context.getString(R.string.re_failed),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }) .start();
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout row;
        public TextView label;
        public TextView data;

        public ViewHolder(View itemView) {
            super(itemView);

            row = (ConstraintLayout) itemView.findViewById(R.id.configitemview);
            label = (TextView) itemView.findViewById(R.id.textView8);
            data = (TextView) itemView.findViewById(R.id.textView9);
        }
    }


}
