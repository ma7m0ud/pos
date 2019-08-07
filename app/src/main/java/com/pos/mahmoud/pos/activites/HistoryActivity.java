package com.pos.mahmoud.pos.activites;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.pos.mahmoud.pos.DataBase.TerminalDatabase;
import com.pos.mahmoud.pos.Helpers.MyEditTextDatePicker;
import com.pos.mahmoud.pos.Helpers.TransactionModel;
import com.pos.mahmoud.pos.R;
import com.pos.mahmoud.pos.adapters.historyAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    ArrayList<TransactionModel> msgList=new ArrayList<TransactionModel>();
    historyAdapter adapter;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new MyEditTextDatePicker(this,R.id.textView10);
        EditText text=(EditText)findViewById(R.id.textView10);
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat simpledateformatDB = new SimpleDateFormat("ddMMyy");
        date=simpledateformatDB.format(calander.getTime());
        String ShowDate = simpledateformat.format(calander.getTime());
        text.setText(ShowDate);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                date=s.toString();
                SimpleDateFormat simpledateformatDB = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date d=simpledateformatDB.parse(date);
                   SimpleDateFormat simpledateformated = new SimpleDateFormat("ddMMyy");
                   date=simpledateformated.format(d);
                   Log.d("Date",date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new myTask().execute();

            }
        });
        RecyclerView rv=findViewById(R.id.rvhistory);
       adapter= new historyAdapter(this,msgList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));



      new myTask().execute();


    }
     class myTask extends AsyncTask<String, List<TransactionModel>, List<TransactionModel>> {


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected List<TransactionModel> doInBackground(String... params) {

            return TerminalDatabase.getInstance(HistoryActivity.this).daoAccess().fetchAllTransactionModel(date+"%");

        }
        @Override
        protected void onPostExecute(List<TransactionModel> config) {

            adapter.setMsgList((ArrayList<TransactionModel>) config);
            adapter.notifyDataSetChanged();

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
