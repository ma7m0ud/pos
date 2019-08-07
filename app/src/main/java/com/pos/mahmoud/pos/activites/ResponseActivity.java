package com.pos.mahmoud.pos.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pos.mahmoud.pos.R;

public class ResponseActivity extends AppCompatActivity {

    ImageView image;
    TextView text;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        image=(ImageView)findViewById(R.id.imageView3);
        text=(TextView)findViewById(R.id.textView6);
        btn=(Button)findViewById(R.id.button);

        int res=getIntent().getIntExtra("responseCode",0);
        Log.d("c",res+"");
        if(res!=1){
            image.setImageResource(R.mipmap.ic_error);
            text.setText(getIntent().getStringExtra("ResponseMessage"));
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
