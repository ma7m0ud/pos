package com.pos.mahmoud.pos;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.pos.mahmoud.pos.activites.HistoryActivity;
import com.pos.mahmoud.pos.activites.MainConfigActivity;
import com.pos.mahmoud.pos.adapters.RAdapter;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.pos.mahmoud.pos.R.layout.activity_main);
        pref= getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String lang=pref.getString("lang", "ar");
       Locale myLocale;
        android.content.res.Configuration config = new android.content.res.Configuration();

        if(lang=="ar")
            myLocale = new Locale(lang,"MA");
        else
            myLocale = new Locale(lang);

        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        RecyclerView recyclerView = (RecyclerView) findViewById(com.pos.mahmoud.pos.R.id.R_view);
        RAdapter radapter = new RAdapter(this);
        recyclerView.setAdapter(radapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.pos.mahmoud.pos.R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case com.pos.mahmoud.pos.R.id.action_lang:
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                CharSequence items[] = new CharSequence[] {"English", "العربية"};
                final SharedPreferences.Editor edit=pref.edit();
                adb.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface d, int which) {
                        switch (which) {
                            case 0:
                                edit.putString("lang","en");
                                edit.commit();
                                changeLanguage("en");
                                break;
                            case 1:
                                edit.putString("lang","ar");
                                edit.commit();
                                changeLanguage("ar");
                                break;
                        }
                    }

                });
                adb.setNegativeButton(getString(com.pos.mahmoud.pos.R.string.cancel), null);
                adb.setTitle(com.pos.mahmoud.pos.R.string.select_language);
                adb.show();
                return true;
            case com.pos.mahmoud.pos.R.id.action_settings:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(this.getString(com.pos.mahmoud.pos.R.string.merch_pass));
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                //input.setText(msgList.get(i).getPayeeId());
                builder.setView(input);

                builder.setPositiveButton(this.getString(com.pos.mahmoud.pos.R.string.yes), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pass=pref.getString("pass", "0000");
                        if(pass.equals(input.getText().toString())){
                            startActivity(new Intent(MainActivity.this,MainConfigActivity.class));
                        }else{
                            Toast.makeText(MainActivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton(this.getString(com.pos.mahmoud.pos.R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                return true;
//            case R.id.action_reprint:
//                startActivity(new Intent(this,ReprintActivity.class));
//
//                return true;
            case com.pos.mahmoud.pos.R.id.action_summery:

                startActivity(new Intent(this,HistoryActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){


        }
    }
    public void changeLanguage(String lang) {
        Locale myLocale;
        if (lang.equalsIgnoreCase(""))
            return;
        if(lang=="ar")
        myLocale = new Locale(lang,"MA");
        else
            myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();

    }
}
