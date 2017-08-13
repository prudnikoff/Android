package com.worlds.prudnikoff.worlds;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class WordActivity extends AppCompatActivity {

    private static CategoriesData categoriesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_activity);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rowDefinitionsRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        categoriesData = (CategoriesData)getIntent().getSerializableExtra("categoriesData");
        ArrayList<DefinitionModel> definitions = (ArrayList<DefinitionModel>) getIntent().getSerializableExtra("definitions");
        setTitle(getIntent().getExtras().getString("query"));
        DefinitionListAdapter adapter = new DefinitionListAdapter(definitions);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    public static void addDefinitionToCategory(final View view, final DefinitionModel definition) {

        final String[] namesOfCategories = categoriesData.getStringListOfCategories();
        if (namesOfCategories.length > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Choose a category: ");
            builder.setItems(namesOfCategories, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    categoriesData.getCategoryByPosition(item).addDefinition(definition);
                    Toast.makeText(view.getContext(), "The word has been successfully added",
                            Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else Toast.makeText(view.getContext(), "You should add at least one category at first",
                Toast.LENGTH_LONG).show();
    }
}
