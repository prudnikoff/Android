package com.example.prudnikoff.rnum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity{

    SeekBar mainSeekBar = null;
    TextView rangeNumberTextView = null;
    CheckBox ifZeroCheckBox = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        mainSeekBar = (SeekBar)findViewById(R.id.mainSeekBar);
        rangeNumberTextView = (TextView)findViewById(R.id.rangeNumberTextView);
        ifZeroCheckBox = (CheckBox)findViewById(R.id.ifZerocheckBox);
        ifZeroCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifZeroCheckBox.isChecked()) {
                    MainActivity.setIfZero(true);
                } else MainActivity.setIfZero(false);
            }
        });
        mainSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rangeNumberTextView.setText(String.valueOf(mainSeekBar.getProgress()));
                MainActivity.setRangeNumber(mainSeekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mainSeekBar.setProgress(MainActivity.getRangeNumber());
        rangeNumberTextView.setText(String.valueOf(MainActivity.getRangeNumber()));
        if (MainActivity.getIfZero()) {
            ifZeroCheckBox.setChecked(true);
        } else ifZeroCheckBox.setChecked(false);
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
            case R.id.main_item: goMainActivity(); break;
            case R.id.info_item: goInfoActivity();
        }
        return true;
    }

    protected void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void goInfoActivity() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }
}
