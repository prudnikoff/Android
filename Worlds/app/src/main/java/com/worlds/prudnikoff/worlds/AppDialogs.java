package com.worlds.prudnikoff.worlds;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;


public class AppDialogs {

    public static void inputNameOfCategoryDialog(final Context context) {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.input_category_name, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        final EditText editText = (EditText) promptView.findViewById(R.id.category_name_editText);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CategoriesData.createNewCategory(context, editText.getText().toString());
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        alert.show();
    }

    public static void showCategoryOptionsDialog(final Context context, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String[] items = {"Rename", "Delete"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0: {
                        renameCategoryDialog(context, position);
                    } break;
                    case 1: {
                        CategoriesData.deleteCategory(position);
                        MainActivity.notifyAboutCategoriesChanging();
                    }
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void renameCategoryDialog(Context context, final int position) {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.input_category_name, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        final EditText editText = (EditText) promptView.findViewById(R.id.category_name_editText);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CategoriesData.getCategoryByPosition(position)
                                .setNameOfCategory(editText.getText().toString());
                        MainActivity.notifyAboutCategoriesChanging();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        alert.show();
    }

    public static void addWordToCategoryDialog(final Context context, final DefinitionModel word) {

        final String[] namesOfCategories = CategoriesData.getStringListOfCategories();

        if (namesOfCategories.length > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Choose a category: ");
            builder.setItems(namesOfCategories, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    CategoryModel category = CategoriesData.getCategoryByPosition(item);
                    category.addWord(word);
                    Toast.makeText(context, "The word has been successfully added",
                            Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else Toast.makeText(context, "You should add at least one category at first",
                Toast.LENGTH_SHORT).show();
    }

    public static void showWordOptionsDialog(final Context context,
                                             final int categoryPosition, final int wordPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String[] items = {"Move to", "Delete"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0: {
                       AppDialogs.moveToAnotherCategoryDialog(context, categoryPosition, wordPosition);
                    } break;
                    case 1: {
                        CategoriesData.getCategoryByPosition(categoryPosition)
                                .getWords().remove(wordPosition);
                        CategoryWordsActivity.notifyAboutWordsChanging();
                    }
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private static void moveToAnotherCategoryDialog
            (final Context context, final int fromCategoryPosition, final int wordPosition) {
        final String[] namesOfCategories = CategoriesData.getStringListOfCategories();

        if (namesOfCategories.length > 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Choose a category: ");
            builder.setItems(namesOfCategories, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {

                    CategoryModel fromCategory = CategoriesData
                            .getCategoryByPosition(fromCategoryPosition);
                    DefinitionModel word = fromCategory.getWords().get(wordPosition);
                    fromCategory.getWords().remove(wordPosition);
                    CategoriesData.getCategoryByPosition(item).addWord(word);
                    CategoryWordsActivity.notifyAboutWordsChanging();

                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else Toast.makeText(context, "You should have at least two categories",
                Toast.LENGTH_SHORT).show();
    }
}
