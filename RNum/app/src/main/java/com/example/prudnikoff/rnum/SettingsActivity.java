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
    Intent returnIntent = new Intent();

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
                if (mainSeekBar.getProgress() == 0) ifZeroCheckBox.setChecked(true);
                returnIntent.putExtra("ifZero", ifZeroCheckBox.isChecked());
            }
        });

        mainSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mainSeekBar.getProgress() == 0) {
                    ifZeroCheckBox.setChecked(true);
                    rangeNumberTextView.setText("0");
                    returnIntent.putExtra("ifZero", ifZeroCheckBox.isChecked());
                } else {
                    rangeNumberTextView.setText(String.valueOf(mainSeekBar.getProgress()));
                }
                returnIntent.putExtra("rangeNumber", mainSeekBar.getProgress());
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
        Bundle extras = getIntent().getExtras();
        mainSeekBar.setProgress(extras.getInt("rangeNumber"));
        rangeNumberTextView.setText(String.valueOf(extras.getInt("rangeNumber")));
        ifZeroCheckBox.setChecked(extras.getBoolean("ifZero"));
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
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    protected void goInfoActivity() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }
}
