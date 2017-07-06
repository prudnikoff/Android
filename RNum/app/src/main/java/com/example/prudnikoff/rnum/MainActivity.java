package com.example.prudnikoff.rnum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Random;


public class MainActivity extends AppCompatActivity {


    static int rangeNumber = 100;
    static boolean ifZero = false;
    TextView rndNumberTextView = null;
    RelativeLayout mainLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        rndNumberTextView.setText(getRandomNumber());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info_item: goInfoActivity(); break;
            case R.id.settings_item: goSettingsActivity();
        }
        return true;
    }

    public static void setRangeNumber(int _rangeNumber) {
        rangeNumber = _rangeNumber;
    }

    public static int getRangeNumber() {
        return rangeNumber;
    }

    public static void setIfZero(boolean _ifZero) {
        ifZero = _ifZero;
    }

    public static boolean getIfZero() {
        return ifZero;
    }

    protected void goInfoActivity() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    protected void goSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    protected void setUp() {
        rndNumberTextView = (TextView)findViewById(R.id.rndNumbertextView);
        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rndNumberTextView.setText(getRandomNumber());
            }
        });
    }

    protected String getRandomNumber() {
        Random rnd = new Random();
        if (ifZero) {
            return String.valueOf(rnd.nextInt(rangeNumber + 1));
        } else {
            return String.valueOf(rnd.nextInt(rangeNumber) + 1);
        }
    }
}
