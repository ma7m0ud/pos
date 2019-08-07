package com.pos.mahmoud.pos.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pos.mahmoud.pos.R;


public class BillInqueryActivity extends AppCompatActivity {
    String PAN;
    String PIN;
    String expDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_inquery);
        getIntent().getStringExtra("PAN");
        getIntent().getStringExtra("PIN");
        getIntent().getStringExtra("expDate");

    }
}
