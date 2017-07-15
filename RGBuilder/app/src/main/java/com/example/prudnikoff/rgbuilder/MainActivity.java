package com.example.prudnikoff.rgbuilder;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
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
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText editRed = null;
    private EditText editGreen= null;
    private EditText editBlue = null;
    private RelativeLayout mainLayout = null;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String[] colorsNames = null;
    private String[] colorsCodes = null;
    private int currentColor = 0;
    private boolean isLongClickTyped = false;

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
            case R.id.picture_item: takePhoto(); break;
            case R.id.about_item: goInfoActivity(); break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            setColor(parseBitmap(imageBitmap));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Resources res = getResources();
        colorsNames = res.getStringArray(R.array.colors_names);
        colorsCodes = res.getStringArray(R.array.colors_codes);
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

                setTypedColor();

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

                setTypedColor();

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

                setTypedColor();

            }
        });

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLongClickTyped) {
                    setColor(getRandomColor());
                } else isLongClickTyped = false;
            }
        });

        mainLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showCurrentColor();
                isLongClickTyped = true;
                return false;
            }
        });

        setTypedColor();
    }

    protected void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    protected int parseBitmap(Bitmap picture) {
        int centralX = picture.getWidth()/2;
        int centralY = picture.getHeight()/2;
        int averageRed = 0;
        int averageGreen = 0;
        int averageBlue = 0;
        int counter = 0;
        int averageX = centralX / 5;
        int averageY = centralY / 5;
        for (int x = -averageX; x <= averageX; x += averageX / 10) {
            for (int y = -averageY; y <= averageY; y += averageY / 10) {
                int pixel = picture.getPixel(centralX + x, centralY + y);
                averageRed += Color.red(pixel);
                averageGreen += Color.green(pixel);
                averageBlue += Color.blue(pixel);
                counter++;
            }
        }
        return Color.rgb(averageRed / counter, averageGreen / counter, averageBlue / counter);
    }

    protected void goInfoActivity() {
        Intent intent = new Intent(this, InfoActivity.class);
        this.startActivity(intent);
    }

    protected int getRandomColor() {
        Random rnd = new Random();
        int red = rnd.nextInt(255);
        int green = rnd.nextInt(255);
        int blue = rnd.nextInt(255);
        return Color.rgb(red, green, blue);
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

    protected void setTypedColor() {

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

        currentColor = Color.rgb(red, green, blue);
        mainLayout.setBackgroundColor(currentColor);

    }

    protected String addZero(String data) {
        if (data.length() < 2) data = "0" + data;
        return data;
    }

    protected void showCurrentColor() {
        setColor(currentColor);
    }

    protected void setColor(int color) {
        String red = addZero(Integer.toHexString(Color.red(color)));
        String green = addZero(Integer.toHexString(Color.green(color)));
        String blue = addZero(Integer.toHexString(Color.blue(color)));
        editRed.setText(red);
        editGreen.setText(green);
        editBlue.setText(blue);
        mainLayout.setBackgroundColor(color);
        String textForToast = "#" + red + green + blue;
        Toast.makeText(this, textForToast + "\n" + getColorName(color), Toast.LENGTH_SHORT).show();
    }

    protected String getColorName(int color) {
        currentColor = color;
        int differenceRed = Math.abs(Integer.parseInt(colorsCodes[0].split(" ")[0]) - Color.red(color));
        int differenceGreen = Math.abs(Integer.parseInt(colorsCodes[0].split(" ")[1]) - Color.green(color));
        int differenceBlue = Math.abs(Integer.parseInt(colorsCodes[0].split(" ")[2]) - Color.blue(color));
        int totalDifference = differenceBlue + differenceGreen + differenceRed;
        int minIndex = 0;
        for (int i = 1; i < colorsCodes.length; i++) {
            differenceRed = Math.abs(Integer.parseInt(colorsCodes[i].split(" ")[0]) - Color.red(color));
            differenceGreen = Math.abs(Integer.parseInt(colorsCodes[i].split(" ")[1]) - Color.green(color));
            differenceBlue = Math.abs(Integer.parseInt(colorsCodes[i].split(" ")[2]) - Color.blue(color));
            if (differenceBlue + differenceGreen + differenceRed < totalDifference) {
                totalDifference = differenceBlue + differenceGreen + differenceRed;
                minIndex = i;
            }
        }
        return colorsNames[minIndex];
    }
}
