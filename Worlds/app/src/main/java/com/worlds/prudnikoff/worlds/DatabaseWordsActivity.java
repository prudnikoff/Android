package com.worlds.prudnikoff.worlds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import java.util.ArrayList;

public class DatabaseWordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_words_activity);
        RecyclerView internetDefinitionsRecyclerView = (RecyclerView)findViewById
                (R.id.internet_definitions_recyclerView);
        internetDefinitionsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        internetDefinitionsRecyclerView.setLayoutManager(layoutManager);
        final ArrayList<WordModel> definitions = (ArrayList<WordModel>) getIntent()
                .getSerializableExtra("definitions");
        setTitle(getIntent().getExtras().getString("query"));
        DatabaseWordsAdapter adapter = new DatabaseWordsAdapter(definitions);
        internetDefinitionsRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
