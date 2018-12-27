package com.example.suguoqing.expandablekey;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

public class MainActivity extends AppCompatPreferenceActivity {
    private static final String TAG = "MainActivity";
    private Preference f1;
    private Preference f2;
    private Preference left_scan;
    private Preference home;
    private Preference back;
    private Preference menu;
    private Preference right_scan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.name);
        //setContentView(R.layout.activity_main);
        addPreferencesFromResource(R.xml.key_item);
        findPreference("f1").setEnabled(true);
        init();

    }


    /*初始化一些属性*/
    private void init() {
        f1 = findPreference("f1");
        f2 = findPreference("f2");
        left_scan = findPreference("left_scan");
        home = findPreference("home");
        right_scan = findPreference("right_scan");
        menu = findPreference("menu");
        back = findPreference("back");

        f1.setOnPreferenceClickListener(listener);
        f2.setOnPreferenceClickListener(listener);
        left_scan.setOnPreferenceClickListener(listener);
        home.setOnPreferenceClickListener(listener);
        right_scan.setOnPreferenceClickListener(listener);
        menu.setOnPreferenceClickListener(listener);
        back.setOnPreferenceClickListener(listener);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.help:
                Intent intent = new Intent(this,HelpActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }


    /*设置点击事件*/
   private Preference.OnPreferenceClickListener listener = new Preference.OnPreferenceClickListener() {
       @Override
       public boolean onPreferenceClick(Preference preference) {
           Intent intent = new Intent(MainActivity.this,BindKeyActivity.class);
           switch (preference.getKey()){
               case "f1":
                    intent.putExtra("keyValue","f1");
                   break;

               case "f2":
                   intent.putExtra("keyValue","f2");
                   break;

               case "left_scan":
                   intent.putExtra("keyValue","left_scan");
                   break;

               case "home":
                   intent.putExtra("keyValue","home");
                   break;

               case "menu":
                   intent.putExtra("keyValue","menu");
                   break;
               case "right_scan":
                   intent.putExtra("keyValue","right_scan");
                   break;
               case "back":
                   intent.putExtra("keyValue","back");
                   break;
           }
           startActivity(intent);
           return false;
       }
   };



}
