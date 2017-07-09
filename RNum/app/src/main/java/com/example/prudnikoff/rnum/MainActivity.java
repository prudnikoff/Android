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

    public static final int SETTINGS_REQUEST = 1;
    static int rangeNumber = 100;
    static boolean ifZero = false;
    TextView rndNumberTextView = null;
    RelativeLayout mainLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setUp();
        rndNumberTextView.setText(getRandomNumber());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SETTINGS_REQUEST) {
            Bundle extras = data.getExtras();
            rangeNumber = extras.getInt("rangeNumber");
            if(data.hasExtra("ifZero")) ifZero = extras.getBoolean("ifZero");
        }

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

    protected void goInfoActivity() {
        Intent goInfoActivity = new Intent(this, InfoActivity.class);
        startActivity(goInfoActivity);
    }

    protected void goSettingsActivity() {
        Intent goSettingsActivity = new Intent(this, SettingsActivity.class);
        goSettingsActivity.putExtra("rangeNumber", rangeNumber);
        goSettingsActivity.putExtra("ifZero", ifZero);
        startActivityForResult(goSettingsActivity, SETTINGS_REQUEST);
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
