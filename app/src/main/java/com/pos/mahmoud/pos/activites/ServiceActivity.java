package com.pos.mahmoud.pos.activites;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.pos.mahmoud.pos.DataBase.TerminalDatabase;
import com.pos.mahmoud.pos.Dialogs.CustomProgressBar;
import com.pos.mahmoud.pos.Helpers.ErrorID;
import com.pos.mahmoud.pos.Helpers.Print;
import com.pos.mahmoud.pos.Helpers.ServicesActivityHelper;
import com.pos.mahmoud.pos.Helpers.TransactionModel;
import com.pos.mahmoud.pos.Helpers.Transactions;
import com.pos.mahmoud.pos.Helpers.TransactionsType;
import com.pos.mahmoud.pos.Helpers.ViewId;
import com.pos.mahmoud.pos.Network.RequestCallBack;
import com.pos.mahmoud.pos.Network.SendRequest;
import com.pos.mahmoud.pos.R;
import com.pos.mahmoud.pos.adapters.InfoAdapter;
import com.pos.mahmoud.pos.models.Configration;
import com.pos.mahmoud.pos.models.TransactionItem;
import com.pos.mahmoud.pos.models.payee;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ServiceActivity extends AppCompatActivity {

    int REQUEST_CODE=1;
    ServicesActivityHelper helper;
    String PAN;
    String PIN;
    String expDate;
    String holderName;
    String track2;
    CustomProgressBar progressBar;
    LinearLayout layout;
     String billAdd="";
    public TransactionItem item;

    private TerminalDatabase terminalDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        iniDB();

        item=Transactions.getInstance().getItemByType(this.getIntent().getIntExtra("type",0));
        toolbar.setBackgroundColor(item.getColor());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layout=(LinearLayout)findViewById(R.id.layout);
        getSupportActionBar().setTitle(item.getName());
        helper=new ServicesActivityHelper(layout,item.getType(),this);

        helper.setupTransaction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Print print = new Print(ServiceActivity.this);

                if (helper.isValied()){
                    int Status= UsbThermalPrinter.STATUS_OK;//print.getPrinterStatus();
                    if (Status == UsbThermalPrinter.STATUS_OK) {

                        if (item.getType() == TransactionsType.CASH_OUT_VOUCHER || item.getType() == TransactionsType.CASH_OUT_VOUCHER_AMOUNT) {
                            layout.setVisibility(View.INVISIBLE);
                            ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar2);
                            bar.setVisibility(View.VISIBLE);
                            TextView text = (TextView) findViewById(R.id.textView4);
                            text.setVisibility(View.VISIBLE);
                            new Thread(new Runnable() {
                                public void run() {
                                    Configration config = terminalDatabase.daoAccess().fetchConfigration("1");
                                    TransactionModel model = helper.getRequestModel(null, null, config);
                                    config.setSystemTraceAuditNumber(config.getSystemTraceAuditNumber() + 1);
                                    terminalDatabase.daoAccess().updateConfigration(config);
                                    sendRequest(config, model);
                                }
                            }).start();
                        } else if (item.getType() == TransactionsType.PURCHASE_MOBILE) {
                            Intent intent = new Intent(ServiceActivity.this, PinEnteryActivity.class);
                            intent.putExtra("name", R.string.pin_label_text);
                            startActivityForResult(intent, 3);
                        } else {
                            Intent intent = new Intent(ServiceActivity.this, SacnCardActivity.class);
                            startActivityForResult(intent, REQUEST_CODE);
                        }

                } else {
                    Toast.makeText(ServiceActivity.this, "NO PAPER", Toast.LENGTH_LONG).show();

                }}} });


    }

    public void iniDB(){
        terminalDatabase = TerminalDatabase.getInstance(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
Log.d("rc",resultCode+"");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
               // Toast.makeText(ServiceActivity.this,data.getExtras().getString("PAN"),Toast.LENGTH_SHORT).show();
               PAN=data.getExtras().getString("PAN");
               expDate=data.getExtras().getString("expDate");
               holderName=data.getExtras().getString("name");
                track2=data.getExtras().getString("track2");
//                    PAN="9222060130947022607";
//                    expDate="1909";

            //    Toast.makeText(ServiceActivity.this,data.getExtras().getString("expDate"),Toast.LENGTH_SHORT).show();
             //   Toast.makeText(ServiceActivity.this,data.getExtras().getString("name"),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ServiceActivity.this,PinEnteryActivity.class);
                intent.putExtra("name",R.string.pin_label_text);
                startActivityForResult(intent,3);
            }
        }else if(requestCode==3) {
            if (resultCode == RESULT_OK) {
                //PAN="6391754099923183";

                layout.setVisibility(View.INVISIBLE);
                ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar2);
                bar.setVisibility(View.VISIBLE);
                TextView text = (TextView) findViewById(R.id.textView4);
                text.setVisibility(View.VISIBLE);
                getSupportActionBar().hide();
                PIN=data.getExtras().getString("pin");
                new Thread(new Runnable() {
            @Override
            public void run() {
                Configration config =terminalDatabase.daoAccess ().fetchConfigration("1");
                final TransactionModel modelKey = new TransactionModel();
                modelKey.setTerminalId(config.getTerminalId());
                modelKey.setClientId(config.getClientId());
                Calendar calander = Calendar.getInstance();
                SimpleDateFormat simpledateformat = new SimpleDateFormat("ddMMyyhhmmss");
                String Date = simpledateformat.format(calander.getTime());
                modelKey.setTranDateTime(Date);
                modelKey.setSystemTraceAuditNumber(config.getSystemTraceAuditNumber());
                config.setSystemTraceAuditNumber(config.getSystemTraceAuditNumber()+1);
                terminalDatabase.daoAccess().updateConfigration(config);

                TransactionItem itemkey=new TransactionItem(0,0,0,"getWorkingKey");
                new SendRequest(config,ServiceActivity.this).sendPost(modelKey, itemkey, new RequestCallBack() {
                    @Override
                    public void onSuccess(final TransactionModel modelKy) {
                if(modelKy.getResponseCode()==0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
//                            PAN="9222060105286242271";
//                            expDate="1904";
//
                            if(item.getType()!=TransactionsType.PURCHASE_MOBILE){
                                PIN = helper.pinCalc(PIN, PAN, modelKy.getWorkingKey());
                            }
                    Log.d("PIN", PIN);
                    Configration config = terminalDatabase.daoAccess().fetchConfigration("1");
                    TransactionModel model = helper.getRequestModel(PIN, PAN, config);
                    model.setExpDate(expDate);
                    config.setSystemTraceAuditNumber(config.getSystemTraceAuditNumber() + 1);
                    terminalDatabase.daoAccess().updateConfigration(config);
                    if(item.getType()==TransactionsType.PURCHASE_MOBILE){
                        PAN=model.getMobileNo();
                        PIN = helper.pinCalc(PIN, PAN, modelKy.getWorkingKey());
                        model.setPIN(PIN);
                    }
                            if(item.getType()==TransactionsType.CHANGE_PIN){
                                model.setNewPIN(helper.pinCalc(model.getNewPIN(), PAN, modelKy.getWorkingKey()));
                            }
                    if (item.getType() == TransactionsType.Mobile_bill_payment || item.getType() == TransactionsType.E15 || item.getType() == TransactionsType.Customs
                            || item.getType() == TransactionsType.mohe|| item.getType() == TransactionsType.mohe_arab ) {
                        TransactionItem qItem = item;
                        model.setTranAmount(null);
                        qItem.setPath("getBill");

                        new SendRequest(config, ServiceActivity.this).sendPost(model, qItem, new RequestCallBack() {
                            @Override
                            public void onSuccess(final TransactionModel model) {
                                Log.d("NET0", new Gson().toJson(model));
                                billAdd = model.getAdditionalData();
                                if (model.getResponseCode() == 0) {
                                    ArrayList<payee> adp = new ArrayList<payee>();
                                    Log.d("NET0", model.getAdditionalData().length()+".");
                                    if(model.getAdditionalData().length()==0&&item.getType()==TransactionsType.mohe){
                                        model.setAdditionalData("StudentName(English)=\" \";StudentName(Arabic)=\" \";ReceiptNumber=\" \";ApplicationID=\" \";dueAmount=\"");
                                    }else if(model.getAdditionalData().length()==0&&item.getType()==TransactionsType.mohe_arab){
                                        model.setAdditionalData("StudentName(English)=\" \";StudentName(Arabic)=\" \";ReceiptNumber=\" \";ApplicationID=\" \";Student No=\" \";dueAmount=\" \"");
                                    }
                                    String[] entries = model.getAdditionalData().split(";");

                                    for (String entry : entries) {
                                        String[] keyValue = entry.split("\\u003d");
                                        if(keyValue.length>1)
                                        adp.add(new payee(keyValue[0], keyValue[1]));
                                    }
                                    final Dialog dialog = new Dialog(ServiceActivity.this, android.R.style.Theme);
                                    dialog.setContentView(R.layout.bill_info_view);
                                    dialog.setCancelable(false);
                                    RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
                                    InfoAdapter radapter = new InfoAdapter(adp, ServiceActivity.this);
                                    recyclerView.setAdapter(radapter);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(ServiceActivity.this));
                                    ((Button) dialog.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Configration config = terminalDatabase.daoAccess().fetchConfigration("1");
                                                    TransactionModel model = helper.getRequestModel(PIN, PAN, config);
                                                    model.setExpDate(expDate);
                                                    config.setSystemTraceAuditNumber(config.getSystemTraceAuditNumber() + 1);
                                                    terminalDatabase.daoAccess().updateConfigration(config);
                                                    item.setPath("payBill");
                                                    if(item.getType() == TransactionsType.E15){
                                                        model.setPersonalPaymentInfo(model.getPersonalPaymentInfo().replaceFirst("2","6"));
                                                    }

                                                    sendRequest(config, model);
                                                }
                                            }).start();
                                        }
                                    });

                                    dialog.show();


                                    ((Button) dialog.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    });
                                    ((Button) dialog.findViewById(R.id.printInfo)).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if(item.getType()==TransactionsType.Mobile_topUp||item.getType()==TransactionsType.Mobile_bill_payment){
                                                        model.setBillAddtionalData(((Spinner)layout.findViewById(ViewId.operator_view)).getSelectedItem().toString());
                                                    }
                                                    TransactionItem itemx=new TransactionItem(item.getName(),item.getType(),TransactionsType.GET_BILL,"",item.getPrintName());
                                                        Print print = new Print(ServiceActivity.this);
                                                        print.prentSuccessCustomer(model, itemx, holderName, expDate,false);
                                                        print.StopPrinter();
                                                }}).start();
                                        }
                                    });

                                } else{
                                    final Dialog dialog=new Dialog(ServiceActivity.this,android.R.style.Theme);
                                    dialog.setContentView(R.layout.activity_response);
                                    dialog.setCancelable(false);
                                    TextView text=(TextView)dialog.findViewById(R.id.textView6);
                                    text.setText(ErrorID.getError(model.getResponseCode()));
                                    TextView text2=(TextView)dialog.findViewById(R.id.textView7);
                                    text2.setText("");
                                    ImageView image=dialog.findViewById(R.id.imageView3);
                                    image.setImageResource(R.mipmap.ic_error_forground);
                                    Button btn=(Button)dialog.findViewById(R.id.okbtn);
                                    Button btnClose=(Button)dialog.findViewById(R.id.closeBtn);

                                    btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    });
                                    btnClose.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    });
                                    dialog.show();
                                }


                            }

                            @Override
                            public void onError() {
                                final Dialog dialog=new Dialog(ServiceActivity.this,android.R.style.Theme);
                                dialog.setContentView(R.layout.activity_response);
                                dialog.setCancelable(false);
                                TextView text=(TextView)dialog.findViewById(R.id.textView6);
                                text.setText("Connection Error!!");
                                TextView text2=(TextView)dialog.findViewById(R.id.textView7);
                                text2.setText("");
                                ImageView image=dialog.findViewById(R.id.imageView3);
                                image.setImageResource(R.mipmap.ic_error_forground);
                                Button btn=(Button)dialog.findViewById(R.id.okbtn);
                                Button btnClose=(Button)dialog.findViewById(R.id.closeBtn);

                                btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                });
                                btnClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                });
                                dialog.show();
                            }
                        });
                    } else {

                        sendRequest(config, model);

                    }
                        }}).start();
                }else{
                    final Dialog dialog=new Dialog(ServiceActivity.this,android.R.style.Theme);
                    dialog.setContentView(R.layout.activity_response);
                    dialog.setCancelable(false);
                    TextView text=(TextView)dialog.findViewById(R.id.textView6);
                    text.setText(ErrorID.getError(modelKy.getResponseCode()));
                    TextView text2=(TextView)dialog.findViewById(R.id.textView7);
                    text2.setText("");
                    ImageView image=dialog.findViewById(R.id.imageView3);
                    image.setImageResource(R.mipmap.ic_error_forground);
                    Button btn=(Button)dialog.findViewById(R.id.okbtn);
                    Button btnClose=(Button)dialog.findViewById(R.id.closeBtn);

                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    dialog.show();
                }
            }   @Override
                    public void onError() {
                        final Dialog dialog=new Dialog(ServiceActivity.this,android.R.style.Theme);
                        dialog.setContentView(R.layout.activity_response);
                        dialog.setCancelable(false);
                        TextView text=(TextView)dialog.findViewById(R.id.textView6);
                        text.setText("Connection Error!!");
                        TextView text2=(TextView)dialog.findViewById(R.id.textView7);
                        text2.setText("");
                        ImageView image=dialog.findViewById(R.id.imageView3);
                        image.setImageResource(R.mipmap.ic_error_forground);
                        Button btn=(Button)dialog.findViewById(R.id.okbtn);
                        Button btnClose=(Button)dialog.findViewById(R.id.closeBtn);

                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        btnClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        dialog.show();
                    }
                });}
                }) .start();
            }
        }

    }

    public void sendRequest(Configration config,TransactionModel model){
        new SendRequest(config,ServiceActivity.this).sendPost(model, item, new RequestCallBack() {
            @Override
            public void onSuccess(final TransactionModel model) {
                Log.d("NET0", new Gson().toJson(model));
                if(item.getType()==TransactionsType.Mobile_topUp||item.getType()==TransactionsType.Mobile_bill_payment){
                    model.setBillAddtionalData(((Spinner)layout.findViewById(ViewId.operator_view)).getSelectedItem().toString());

                }
               if(model.getResponseCode()==0){


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            model.setItem(item);
                            model.setHolderName(holderName);
                            model.setExpDate(expDate);
                            TerminalDatabase.getInstance(ServiceActivity.this).daoAccess().insertHistory(model);
                                            Print print = new Print(ServiceActivity.this);
                                              print.prentSuccessCustomer(model, item, holderName,expDate,false);
                                                print.StopPrinter();
                        }}).start();

                   final Dialog dialog=new Dialog(ServiceActivity.this,android.R.style.Theme);
                   dialog.setContentView(R.layout.activity_response);
                   dialog.setCancelable(false);
                   if(item.getType()==TransactionsType.GET_BALANCE){
                       TextView text=(TextView)dialog.findViewById(R.id.textView7);
                       text.setText(getString(R.string.re_balance)+model.getAdditionalAmount());
                   }if(item.getType()==TransactionsType.GET_MINI_STATEMENT){
                       TextView text=(TextView)dialog.findViewById(R.id.textView7);
                       text.setText(" ");
                   }
                   Button btn=(Button)dialog.findViewById(R.id.okbtn);
                   Button btnClose=(Button)dialog.findViewById(R.id.closeBtn);
                   btn.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           new Thread(new Runnable() {
                               @Override
                               public void run() {
                                   if(item.getType()!=TransactionsType.GET_BALANCE&&item.getType()!=TransactionsType.GET_MINI_STATEMENT) {
                                       Print print = new Print(ServiceActivity.this);
                                       print.prentSuccessMerchant(model, item, holderName, expDate);
                                       print.StopPrinter();
                                   }
                                   finish();
                                   dialog.dismiss();
                               }}).start();
                       }
                   });
                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           dialog.dismiss();
                            finish();
                        }
                    });
                    dialog.show();
               }else{
                   final Dialog dialog=new Dialog(ServiceActivity.this,android.R.style.Theme);
                   dialog.setContentView(R.layout.activity_response);
                   dialog.setCancelable(false);
                   TextView text=(TextView)dialog.findViewById(R.id.textView6);
                   text.setText(ErrorID.getError(model.getResponseCode()));
                   ImageView image=dialog.findViewById(R.id.imageView3);
                   image.setImageResource(R.mipmap.ic_error_forground);
                   Button btn=(Button)dialog.findViewById(R.id.okbtn);
                   Button btnClose=(Button)dialog.findViewById(R.id.closeBtn);

                   btn.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           new Thread(new Runnable() {
                               @Override
                               public void run() {
                                   Print print = new Print(ServiceActivity.this);
                                   print.prentSuccessMerchant(model, item, holderName,expDate);
                                   print.StopPrinter();
                                   dialog.dismiss();
                                   finish();
                               }}).start();
                       }
                   });
                   btnClose.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           dialog.dismiss();
                           finish();
                       }
                   });
               dialog.show();
               }


            }

            @Override
            public void onError() {
                final Dialog dialog=new Dialog(ServiceActivity.this,android.R.style.Theme);
                dialog.setContentView(R.layout.activity_response);
                dialog.setCancelable(false);
                TextView text=(TextView)dialog.findViewById(R.id.textView6);
                text.setText("Connection Error!!");
                TextView text2=(TextView)dialog.findViewById(R.id.textView7);
                text2.setText("");
                ImageView image=dialog.findViewById(R.id.imageView3);
                image.setImageResource(R.mipmap.ic_error_forground);
                Button btn=(Button)dialog.findViewById(R.id.okbtn);
                Button btnClose=(Button)dialog.findViewById(R.id.closeBtn);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       dialog.dismiss();
                        finish();
                    }
                });
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        finish();
                    }
                });
            dialog.show();
            }
        });
    }


    public void onBackPressed(){
        if(layout.getVisibility()==View.VISIBLE){
            super.onBackPressed();
        }
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
