package com.worlds.prudnikoff.worlds;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


class AppDialogs {

    static void inputNameOfCategoryDialog(final Context context) {
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

    static void showCategoryOptionsDialog(final Context context, final int position) {
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

    private static void renameCategoryDialog(Context context, final int position) {
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

    static void addWordToCategoryDialog(final Context context, final WordModel word) {

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

    static void showWordOptionsDialog(final Context context, final ArrayList<WordModel> words,
                                      final int wordPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String[] items = {"Move to", "Delete"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0: {
                       AppDialogs.moveToAnotherCategoryDialog(context, words, wordPosition);
                    } break;
                    case 1: {
                        words.remove(wordPosition);
                        CategoryWordsActivity.notifyAboutWordsChanging();
                    }
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private static void moveToAnotherCategoryDialog
            (final Context context, final ArrayList<WordModel> words, final int wordPosition) {
        final String[] namesOfCategories = CategoriesData.getStringListOfCategories();

        if (namesOfCategories.length > 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Choose a category: ");
            builder.setItems(namesOfCategories, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {

                    WordModel word = words.get(wordPosition);
                    words.remove(wordPosition);
                    CategoriesData.getCategoryByPosition(item).addWord(word);
                    CategoryWordsActivity.notifyAboutWordsChanging();

                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else Toast.makeText(context, "You should have at least two categories",
                Toast.LENGTH_SHORT).show();
    }

    static void chooseQuizOptionDialog(final Context context, final int categoryPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final String[] items = {"By words (not memorized)", "By definitions (not memorized)",
                "By words (all)", "By definitions (all)"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Intent intent = new Intent(context, WordsSlideQuizActivity.class);
                switch (item) {
                    case 0: {
                        intent.putExtra("categoryPosition", categoryPosition);
                        intent.putExtra("notMemorized", true);
                        intent.putExtra("byWords", true);
                        context.startActivity(intent);
                    } break;
                    case 1: {
                        intent.putExtra("categoryPosition", categoryPosition);
                        intent.putExtra("notMemorized", true);
                        intent.putExtra("byWords", false);
                        context.startActivity(intent);
                    } break;
                    case 2: {
                        intent.putExtra("categoryPosition", categoryPosition);
                        intent.putExtra("notMemorized", false);
                        intent.putExtra("byWords", true);
                        context.startActivity(intent);
                    } break;
                    case 3: {
                        intent.putExtra("categoryPosition", categoryPosition);
                        intent.putExtra("notMemorized", false);
                        intent.putExtra("byWords", false);
                        context.startActivity(intent);
                    }
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
