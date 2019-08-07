package com.pos.mahmoud.pos.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pos.mahmoud.pos.DataBase.TerminalDatabase;
import com.pos.mahmoud.pos.Helpers.Print;
import com.pos.mahmoud.pos.Helpers.TransactionModel;
import com.pos.mahmoud.pos.R;

public class ReprintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reprint);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((Button)findViewById(R.id.reprint)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inValue=((EditText)findViewById(R.id.auditnumber)).getText().toString();
              if(inValue.length()>0){
               final int rec= Integer.parseInt(inValue);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TransactionModel model= TerminalDatabase.getInstance(ReprintActivity.this).daoAccess().fetchTransactionModel(rec);
                        if(model!=null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((ProgressBar) findViewById(R.id.progressBarreprint)).setVisibility(View.VISIBLE);
                                    ((TextView) findViewById(R.id.textViewproc)).setVisibility(View.VISIBLE);
                                    ((EditText) findViewById(R.id.auditnumber)).setVisibility(View.INVISIBLE);
                                    ((Button) findViewById(R.id.reprint)).setVisibility(View.INVISIBLE);
                                }});
                            Print print = new Print(ReprintActivity.this);
                            print.prentSuccessCustomer(model, model.getItem(), model.getHolderName(), model.getExpDate(),true);
                            print.StopPrinter();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((ProgressBar) findViewById(R.id.progressBarreprint)).setVisibility(View.INVISIBLE);
                                    ((TextView) findViewById(R.id.textViewproc)).setVisibility(View.INVISIBLE);
                                    ((EditText) findViewById(R.id.auditnumber)).setVisibility(View.VISIBLE);
                                    ((Button) findViewById(R.id.reprint)).setVisibility(View.VISIBLE);
                                    ((EditText) findViewById(R.id.auditnumber)).setText("");
                                    //finish();
                                }});
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((ProgressBar)findViewById(R.id.progressBarreprint)).setVisibility(View.INVISIBLE);
                                    ((TextView)findViewById(R.id.textViewproc)).setVisibility(View.INVISIBLE);
                                    ((EditText)findViewById(R.id.auditnumber)).setVisibility(View.VISIBLE);
                                    ((Button)findViewById(R.id.reprint)).setVisibility(View.VISIBLE);
                                    Toast.makeText(ReprintActivity.this, R.string.transaction_not_foun,Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }}).start();
            }else{
                  ((EditText)findViewById(R.id.auditnumber)).setError(getString(R.string.originalTranSystemTraceAuditNumber_error));
              }}
        });

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
