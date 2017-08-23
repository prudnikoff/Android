package com.worlds.prudnikoff.worlds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import java.util.ArrayList;

public class InternetDefinitionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.internet_definitions_activity);
        RecyclerView internetDefinitionsRecyclerView = (RecyclerView)findViewById
                (R.id.internet_definitions_recyclerView);
        internetDefinitionsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        internetDefinitionsRecyclerView.setLayoutManager(layoutManager);
        final ArrayList<DefinitionModel> definitions = (ArrayList<DefinitionModel>) getIntent()
                .getSerializableExtra("definitions");
        setTitle(getIntent().getExtras().getString("query"));
        InternetDefinitionsAdapter adapter = new InternetDefinitionsAdapter(definitions);
        internetDefinitionsRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
