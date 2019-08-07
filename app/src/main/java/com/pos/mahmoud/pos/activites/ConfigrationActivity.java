package com.pos.mahmoud.pos.activites;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.pos.mahmoud.pos.DataBase.TerminalDatabase;
import com.pos.mahmoud.pos.R;
import com.pos.mahmoud.pos.adapters.ConfigAdapter;
import com.pos.mahmoud.pos.models.Configration;
import com.pos.mahmoud.pos.models.payee;

import java.util.ArrayList;

public class ConfigrationActivity extends AppCompatActivity {


    ArrayList<payee> map=new ArrayList<payee>();
    ConfigAdapter adapter;
    private static final String DATABASE_NAME = "Terminal_db";
  RecyclerView rv;
    MenuItem itemSave;
    MenuItem itemEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String type = getIntent().getStringExtra("parm");
        if (type.equals("net")) {
            getSupportActionBar().setTitle(R.string.network_configration);
        } else if (type.equals("sys")) {
            getSupportActionBar().setTitle(R.string.terminal_configuration);
        } else if (type.equals("merch")){
            getSupportActionBar().setTitle(R.string.merchant_configuration);
        }
        rv=(RecyclerView)findViewById(R.id.congigrv);
        adapter=new ConfigAdapter(this,map);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

//        Button updatePayee=(Button)findViewById(R.id.updatePayee);
//        updatePayee.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        Configration config =terminalDatabase.daoAccess ().fetchConfigration("1");
//                        TransactionModel model = new TransactionModel();
//                        model.setTerminalId(config.getTerminalId());
//                        model.setClientId(config.getClientId());
//                        Calendar calander = Calendar.getInstance();
//                        SimpleDateFormat simpledateformat = new SimpleDateFormat("ddMMyyhhmmss");
//                        String Date = simpledateformat.format(calander.getTime());
//                        model.setTranDateTime(Date);
//                        model.setSystemTraceAuditNumber(config.getSystemTraceAuditNumber());
//                        config.setSystemTraceAuditNumber(config.getSystemTraceAuditNumber()+1);
//                        terminalDatabase.daoAccess().updateConfigration(config);
//
//                        TransactionItem item=new TransactionItem(0,0,0,"getPayeesList");
//                        new SendRequest(config,ConfigrationActivity.this).sendPost(model, item, new RequestCallBack() {
//                            @Override
//                            public void onSuccess(final TransactionModel model) {
//                               Toast.makeText(ConfigrationActivity.this, new Gson().toJson(model),Toast.LENGTH_SHORT).show();
//                                if(model.getResponseCode()==0){
//                                    new Thread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            terminalDatabase.daoAccess().DeleteAllPayee();
//                                            terminalDatabase.daoAccess().insertPayeeList(model.getPayeesList());
//
//                                            String name = terminalDatabase.daoAccess().fetchPayee("MTN Top Up").getPayeeName();
//                                            Log.d("NET0", name);
//                                        }}).start();
//                                }else{
//                                    Log.d("NET0", new Gson().toJson(model));
//                                }
//
//
//                            }
//
//                            @Override
//                            public void onError() {
//                                Toast.makeText(ConfigrationActivity.this, "ERROR",Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }) .start();
//            }
//        });


        new AsyncTask<String, Configration, Configration>() {


            @Override
            protected void onPreExecute() {
            }

            @Override
            protected Configration doInBackground(String... params) {
                // TODO fetch url data do bg process.
                return TerminalDatabase.getInstance(ConfigrationActivity.this).daoAccess().fetchConfigration("1");
            }
            @Override
            protected void onPostExecute(Configration config) {

                if(config!=null) {
                    String type = getIntent().getStringExtra("parm");
                    if (type.equals("net")) {
                        map.add(new payee(R.string.IP+"", config.getIp()));
                        map.add(new payee(R.string.port+"", config.getPort()));
                        map.add(new payee(R.string.path+"", config.getPath()));
                        map.add(new payee(R.string.net_timeout+"", config.getTWK()));
                        map.add(new payee(R.string.network_test+"", ""));
                    } else if (type.equals("sys")) {
                        map.add(new payee(R.string.re_termianlId+"", config.getTerminalId()));
                        map.add(new payee(R.string.re_merchant_name+"", config.getClientId()));
                        map.add(new payee(R.string.tmk+"", config.getTMK()));
                        map.add(new payee(R.string.sys+"", config.getSystemTraceAuditNumber()+""));
                    } else if (type.equals("merch")){
                        map.add(new payee(R.string.bankname+"", config.getBankName()));
                    map.add(new payee(R.string.merchantname+"", config.getMerchantName()));
                    map.add(new payee(R.string.merchantaddress+"", config.getMerchantAddress()));
                }
                adapter.setConfig(config);
                  adapter.notifyDataSetChanged();

            }else{
                     config=new Configration();
                    String type = getIntent().getStringExtra("parm");
                    if (type.equals("net")) {
                        map.add(new payee(R.string.IP+"", ""));
                        map.add(new payee(R.string.port+"", ""));
                        map.add(new payee(R.string.path+"", ""));
                    } else if (type.equals("sys")) {
                        map.add(new payee(R.string.re_termianlId+"", ""));
                        map.add(new payee(R.string.re_merchant_name+"", ""));
                        map.add(new payee(R.string.tmk+"", ""));
                        map.add(new payee(R.string.sys+"", "0"));
                    } else if (type.equals("merch")){
                        map.add(new payee(R.string.bankname+"", ""));
                        map.add(new payee(R.string.merchantname+"", ""));
                        map.add(new payee(R.string.merchantaddress+"", ""));
                    }
                    adapter.setConfig(config);
                    adapter.notifyDataSetChanged();
                }

            }

        }.execute();


    }
    public void editEnabled(boolean enabled){
//        url.setEnabled(enabled);
//        Termianalid.setEnabled(enabled);
//        Client.setEnabled(enabled);
//        TMK.setEnabled(enabled);
//        Audit.setEnabled(enabled);
//        bankName.setEnabled(enabled);
//        merchantName.setEnabled(enabled);
//        merchantAddress.setEnabled(enabled);
   }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
//    public void saveConfigration(){
//         final ProgressDialog p=new ProgressDialog(this);
//        new AsyncTask<Void,Void,Boolean>() {
//
//
//            @Override
//            protected void onPreExecute() {
//                p.setMessage("Saving");
//                p.setIndeterminate(true);
//                p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                p.setCancelable(false);
//                p.show();
//            }
//
//            @Override
//            protected Boolean doInBackground(Void... voids) {
//                boolean notFound=false;
//                Configration config  = terminalDatabase.daoAccess().fetchConfigration("1");
//                if(config==null){
//                    config  =new Configration();
//                    notFound=true;
//                }
//
//                String audit=Audit.getText().toString();
//                if(audit.length()>0){
//                    config.setSystemTraceAuditNumber(Integer.parseInt(audit));
//                }else{
//                    config.setSystemTraceAuditNumber(0);
//                }
//               config.setURL(url.getText().toString());
//               config.setTerminalId(Termianalid.getText().toString());
//               config.setClientId(Client.getText().toString());
//               config.setTMK(TMK.getText().toString());
//               config.setBankName(bankName.getText().toString());
//               config.setMerchantName(merchantName.getText().toString());
//               config.setMerchantAddress(merchantAddress.getText().toString());
//               if(!notFound) {
//                   terminalDatabase.daoAccess().updateConfigration(config);
//               }else{
//                   config.setId("1");
//                   terminalDatabase.daoAccess().insertConfigration(config);
//               }
//                return true;
//            }
//
//
//            @Override
//            protected void onPostExecute(Boolean b) {
//              //  Toast.makeText(getApplicationContext(),config.getURL(),Toast.LENGTH_SHORT);
//                p.dismiss();
//            }
//
//        }.execute();
//    }
}
