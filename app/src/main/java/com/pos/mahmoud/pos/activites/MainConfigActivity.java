package com.pos.mahmoud.pos.activites;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pos.mahmoud.pos.DataBase.TerminalDatabase;
import com.pos.mahmoud.pos.Helpers.TransactionModel;
import com.pos.mahmoud.pos.Network.RequestCallBack;
import com.pos.mahmoud.pos.Network.SendRequest;
import com.pos.mahmoud.pos.R;
import com.pos.mahmoud.pos.models.Configration;
import com.pos.mahmoud.pos.models.TransactionItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainConfigActivity extends AppCompatActivity {

    EditText input;
    String newPIN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_config);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((ConstraintLayout)findViewById(R.id.constraintLayout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainConfigActivity.this, ConfigrationActivity.class);
                intent.putExtra("parm","net");
                startActivity(intent);
            }
        });
        ((ConstraintLayout)findViewById(R.id.constraintLayout2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainConfigActivity.this, ConfigrationActivity.class);
                intent.putExtra("parm","sys");
                startActivity(intent);
            }
        });
        ((ConstraintLayout)findViewById(R.id.constraintLayout3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainConfigActivity.this, ConfigrationActivity.class);
                intent.putExtra("parm","merch");
                startActivity(intent);
            }
        });
        ((ConstraintLayout)findViewById(R.id.constraintLayout5)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = ProgressDialog.show(MainConfigActivity.this, "",
                        MainConfigActivity.this.getString(R.string.please_wait), true);
                dialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                          final TerminalDatabase terminalDatabase=TerminalDatabase.getInstance(MainConfigActivity.this);
                        Configration config = terminalDatabase.daoAccess ().fetchConfigration("1");
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

                        TransactionItem item=new TransactionItem(0,0,0,"getPayeesList");
                        new SendRequest(config,MainConfigActivity.this).sendPost(model, item, new RequestCallBack() {
                            @Override
                            public void onSuccess(final TransactionModel model) {
                               dialog.dismiss();
                                Toast.makeText(MainConfigActivity.this, getString(R.string.re_success),Toast.LENGTH_SHORT).show();

                                if(model.getResponseCode()==0){
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            terminalDatabase.daoAccess().DeleteAllPayee();
                                            terminalDatabase.daoAccess().insertPayeeList(model.getPayeesList());

                                            String name = terminalDatabase.daoAccess().fetchPayee("MTN Top Up").getPayeeName();
                                            Log.d("NET0", name);
                                        }}).start();
                                }else{dialog.dismiss();
                                    Toast.makeText(MainConfigActivity.this, getString(R.string.re_failed),Toast.LENGTH_SHORT).show();

                                }


                            }

                            @Override
                            public void onError() {
                                dialog.dismiss();
                                Toast.makeText(MainConfigActivity.this, getString(R.string.re_failed),Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }) .start();

            }
        });
        ((ConstraintLayout)findViewById(R.id.constraintLayout4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDialog(getString(R.string.merch_pass), new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       final SharedPreferences pref= getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                       String pass=pref.getString("pass", "0000");
                       if(pass.equals(input.getText().toString())){

                           showDialog(getString(R.string.newPIN), new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                             newPIN=input.getText().toString();
                             showDialog(getString(R.string.con_newPIN), new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                if(newPIN.equals(input.getText().toString())){
                                   SharedPreferences.Editor edit= pref.edit();
                                   edit.remove("pass");
                                   edit.putString("pass",input.getText().toString());
                                   edit.commit();
                                }
                                 }
                             }, new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                     dialog.cancel();
                                 }
                             });
                               }
                           }, new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   dialog.cancel();
                               }
                           });

                       }else{
                           Toast.makeText(MainConfigActivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                       }
                   }
               }, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                   dialog.cancel();
                   }
               });


            }
        });
    }

    public void showDialog(String tital,DialogInterface.OnClickListener Oklistener,DialogInterface.OnClickListener cancllistener){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tital);
        input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton(getString(R.string.yes), Oklistener );
        builder.setNegativeButton(getString(R.string.cancel),cancllistener);
        builder.show();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;



        }
        return super.onOptionsItemSelected(item);
    }

}
