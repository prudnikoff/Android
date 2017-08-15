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

public class InternetDefinitionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.internet_definitions_activity);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.internet_definitions_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<DefinitionModel> definitions = (ArrayList<DefinitionModel>) getIntent().getSerializableExtra("definitions");
        setTitle(getIntent().getExtras().getString("query"));
        InternetDefinitionsAdapter adapter = new InternetDefinitionsAdapter(definitions);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);

    }

    public static void addDefinitionToCategory(final View view, final DefinitionModel definition) {

        final String[] namesOfCategories = CategoriesData.getStringListOfCategories();

        if (namesOfCategories.length > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Choose a category: ");
            builder.setItems(namesOfCategories, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    CategoryModel category = CategoriesData.getCategoryByPosition(item);
                    category.addDefinition(definition);
                    Toast.makeText(view.getContext(), "The word has been successfully added",
                            Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else Toast.makeText(view.getContext(), "You should add at least one category at first",
                Toast.LENGTH_SHORT).show();

    }

}
