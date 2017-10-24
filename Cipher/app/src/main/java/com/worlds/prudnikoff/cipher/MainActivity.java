package com.worlds.prudnikoff.cipher;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static String inputFilePath;
    private static ViewPager viewPager;
    private static SectionsPagerAdapter sectionsPagerAdapter;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = null;
        final FilePickerDialog dialog = new FilePickerDialog(MainActivity.this,properties);
        dialog.setTitle("Select a file: ");
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                if (files.length == 1) {
                    inputFilePath = files[0];
                    setTitle("Cipher: .. " + inputFilePath.substring(inputFilePath.lastIndexOf("/")));
                    Toast.makeText(MainActivity.this, "The file was successfully selected",
                            Toast.LENGTH_SHORT).show();
                } else Toast.makeText(MainActivity.this, "Incorrect files were chosen",
                        Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }

    static void crypt(int position, String key) {
        if (inputFilePath != null) {
            switch (position) {
                case 0:
                    ThreadCipher threadCipher = new ThreadCipher(inputFilePath, key);
                    new Background(context, threadCipher).execute();
                    break;
                case 1:
                    Geffe geffe = new Geffe(inputFilePath, key);
                    new Background(context, geffe).execute();
                    break;
                case 2:
                    RC4 rc4 = new RC4(inputFilePath, key);
                    new Background(context, rc4).execute();
                    break;
            }
        } else Toast.makeText(context, "Please, choose any file", Toast.LENGTH_SHORT).show();
    }

    static CipherFragment getCurrentFragment() {
        return (CipherFragment)sectionsPagerAdapter.getFragment(viewPager.getCurrentItem());
    }
}
