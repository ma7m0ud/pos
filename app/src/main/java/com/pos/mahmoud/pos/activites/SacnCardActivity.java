package com.pos.mahmoud.pos.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.magnetic.MagneticCard;
import com.pos.mahmoud.pos.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SacnCardActivity extends AppCompatActivity {
    Handler handler;
    Thread readThread;
    int REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sacn_card);

        handler = new Handler()
        {

            @Override
            public void handleMessage(Message msg)
            {
                String[] TracData = (String[])msg.obj;
                if(TracData[1]!=null&&TracData[0]!=null) {
                    checkCard(TracData);
                }
            }

        };

        ((Button)findViewById(R.id.cancel_scan)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try {
            MagneticCard.open(SacnCardActivity.this);
            readThread = new ReadThread();
            readThread.start();
        } catch (Exception e) {
           // click.setEnabled(false);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("error");
            alertDialog.setMessage("error");
            alertDialog.setPositiveButton("ok",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SacnCardActivity.this.finish();
                }
            });
            alertDialog.show();
        }

    }
    public void checkCard(String[] data){
       String PAN= data[1].substring(0,data[1].indexOf("="));
       String expDate=data[1].substring(data[1].indexOf("=")+1,data[1].indexOf("=")+5);
        String name="";
        if(data[0].contains("^")) {
          name = data[0].substring(data[0].indexOf("^") + 1, data[0].lastIndexOf("^"));
       }else{
           name=data[0];
       }
      //  Toast.makeText(SacnCardActivity.this, PAN+"      "+expDate+"         "+name, Toast.LENGTH_LONG).show();
        if(PAN.length()>12) {
            readThread.interrupt();
            Intent intent = new Intent();
            intent.putExtra("PAN", PAN);
            intent.putExtra("expDate", expDate);
            intent.putExtra("name", name);
        //    intent.putExtra("track2",data[1]);
          // Toast.makeText(SacnCardActivity.this,data[1], Toast.LENGTH_LONG).show();
            setResult(RESULT_OK, intent);
              finish();
        }else{
            Toast.makeText(SacnCardActivity.this,"try again", Toast.LENGTH_LONG).show();

        }
    }
    protected void onDestroy() {

        if (readThread != null)
        {
            readThread.interrupt();
        }
        MagneticCard.close();
        super.onDestroy();

    }

    private class ReadThread extends Thread
    {
        String[] TracData = null;

        @Override
        public void run()
        {
            MagneticCard.startReading();
            while (!Thread.interrupted()){
                try{
                    TracData = MagneticCard.check(1000);
                    Log.d("tag",TracData.length+"" );
                    handler.sendMessage(handler.obtainMessage(1, TracData));
                    MagneticCard.startReading();

                }catch (TelpoException e){
                }
            }
        }

    }


}
