package com.pos.mahmoud.pos.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;
import com.pos.mahmoud.pos.DataBase.TerminalDatabase;
import com.pos.mahmoud.pos.Helpers.TransactionModel;
import com.pos.mahmoud.pos.Helpers.Transactions;
import com.pos.mahmoud.pos.R;
import com.pos.mahmoud.pos.adapters.BasicTableFixHeaderAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistorySummeryActivity extends AppCompatActivity {
    private TableFixHeaders tablefixheaders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sumery_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tablefixheaders = (TableFixHeaders) findViewById(R.id.tablefixheaders);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final BaseTableAdapter adp=getAdapter();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tablefixheaders.setAdapter(adp);
                    }
                });


            }
        }).start();

    }

    public BaseTableAdapter getAdapter() {
        BasicTableFixHeaderAdapter adapter = new BasicTableFixHeaderAdapter(this);
        List<List<String>> body = getBody(adapter);

        adapter.setFirstHeader("Transaction Name");
        adapter.setHeader(getHeader());

        adapter.setBody(body);
        adapter.setSection(body);

        return adapter;
    }

    private List<String> getHeader() {
        List<String> header = new ArrayList<>();
        header.add("PAN");
        header.add("Auth No.");
        header.add("Amount");
        header.add("DateTime");
        header.add("Receipt No.");

        return header;
    }

    private List<List<String>> getBody(BasicTableFixHeaderAdapter adapter) {
        List<List<String>> rows = new ArrayList<>();
        List<List<String>> frow = new ArrayList<>();
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("ddMMyy");
        String Date = simpledateformat.format(calander.getTime());
        List<TransactionModel> models = TerminalDatabase.getInstance(HistorySummeryActivity.this).daoAccess().fetchAllTransactionModel(Date+"%");
        for (TransactionModel model : models) {
            List<String> cols = new ArrayList<>();

            Log.d("NAME",getString(Transactions.getInstance().getItemByType(model.getItem().getType()).getName()));
            cols.add(getString(Transactions.getInstance().getItemByType(model.getItem().getType()).getName()));
           // frow.add(cols);
            cols.add(model.getPAN());
           //
            cols.add(model.getApprovalCode());
           // rows.add(cols);
            cols.add(model.getTranAmount()+"");
           // rows.add(cols);
            cols.add(model.getTranDateTime());
           // rows.add(cols);
            cols.add(model.getSystemTraceAuditNumber()+"");
            rows.add(cols);
        }

        adapter.setFirstBody(rows);

        return rows;
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