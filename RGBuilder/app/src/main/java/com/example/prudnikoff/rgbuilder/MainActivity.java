package com.example.prudnikoff.rgbuilder;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText editRed = null;
    private EditText editGreen= null;
    private EditText editBlue = null;
    private RelativeLayout mainLayout = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.main_menu: goInfoActivity(); break;
        }
        return true;
    }

    protected void goInfoActivity() {
        Intent intent = new Intent(this, InfoActivity.class);
        this.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mainLayout = (RelativeLayout)findViewById(R.id.relativeLayoutMain);
        editRed = (EditText)findViewById(R.id.editTextRed);
        editGreen = (EditText)findViewById(R.id.editTextGreen);
        editBlue = (EditText)findViewById(R.id.editTextBlue);
        editRed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                setMainColor();

            }
        });

        editGreen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                setMainColor();

            }
        });

        editBlue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                setMainColor();

            }
        });

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRandomColor();
            }
        });

    }

    @Override protected void onStart() {
        super.onStart();
        setMainColor();
    }

    protected void setRandomColor() {
        Random rnd = new Random();
        int red = rnd.nextInt(255);
        int green = rnd.nextInt(255);
        int blue = rnd.nextInt(255);
        int randomColor = Color.rgb(red, green, blue);
        editRed.setText(Integer.toHexString(red));
        editGreen.setText(Integer.toHexString(green));
        editBlue.setText(Integer.toHexString(blue));
        mainLayout.setBackgroundColor(randomColor);
    }

    protected boolean checkTypedColor(String color) {

        final String allowed = "ABCDEFabcdef0123456789";
        boolean result = true;

        if (color.length() > 2 | color.length() < 1) {
            result = false;
        }

        for (int i = 0; i < color.length() && result; i++) {
            if (!allowed.contains(color.charAt(i) + "")) {
                result = false;
            }
        }
        return result;
    }

    protected void setMainColor() {

        String sRed = editRed.getText().toString();
        String sGreen = editGreen.getText().toString();
        String sBlue = editBlue.getText().toString();
        int red = 0;
        int green = 0;
        int blue = 0;
        if (checkTypedColor(sRed)) {
            red = Integer.parseInt(sRed, 16);
        } else {
            if (editRed.getText().length() > 0) {
                editRed.setText("00");
            }
        }
        if (checkTypedColor(sGreen)) {
            green = Integer.parseInt(sGreen, 16);
        } else {
            if (editGreen.getText().length() > 0) {
                editGreen.setText("00");
            }
        }
        if (checkTypedColor(sBlue)) {
            blue = Integer.parseInt(sBlue, 16);
        } else {
            if (editBlue.getText().length() > 0) {
                editBlue.setText("00");
            }
        }

        int mainColor = Color.rgb(red, green, blue);
        mainLayout.setBackgroundColor(mainColor);

    }
}
