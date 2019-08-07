
package com.pos.mahmoud.pos.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.pos.mahmoud.pos.R;

public class ProccessingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proccessing);

    }

    @Override
    public void onBackPressed() {

        Toast.makeText(this,"Can't cancle on going transaction",Toast.LENGTH_SHORT).show();
        // It's expensive, if running turn it off.
     //   super.onBackPressed();
    }
}
