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
        View promptView = layoutInflater.inflate(R.layout.input_category_name_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        final EditText editText = (EditText) promptView.findViewById(R.id.dialog_category_name_editText);

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

    static void createNewWordDialog(final Context context, final CategoryModel category) {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.create_new_word_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        final EditText headwordEditText = (EditText) promptView.findViewById(R.id.dialog_headword_editText);
        final EditText partOfSpeechEditText = (EditText) promptView.findViewById(R.id.dialog_partOfSpeech_textEdit);
        final EditText definitionEditText = (EditText) promptView.findViewById(R.id.dialog_definition_textEdit);
        final EditText exampleEditText = (EditText) promptView.findViewById(R.id.dialog_example_textEdit);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String partOfSpeech = partOfSpeechEditText.getText().toString();
                        if (partOfSpeech.length() == 0) partOfSpeech = null;
                        String example = exampleEditText.getText().toString();
                        if (example.length() == 0) example = null;
                        String headword = headwordEditText.getText().toString();
                        String definition = definitionEditText.getText().toString();
                        if (headword.length() == 0 || definition.length() == 0) {
                            Toast.makeText(context, "Sorry, fields can't be empty",
                                    Toast.LENGTH_SHORT).show();
                            createNewWordDialog(context, category);
                        } else {
                            category.addWord(new WordModel(partOfSpeech, headword, definition, example));
                            CategoryWordsActivity.notifyAboutWordsChanging();
                            MainActivity.notifyAboutCategoriesChanging();
                        }
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

    static void editWordDialog(final Context context, final WordModel word) {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.create_new_word_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        final EditText headwordEditText = (EditText) promptView.findViewById(R.id.dialog_headword_editText);
        final EditText partOfSpeechEditText = (EditText) promptView.findViewById(R.id.dialog_partOfSpeech_textEdit);
        final EditText definitionEditText = (EditText) promptView.findViewById(R.id.dialog_definition_textEdit);
        final EditText exampleEditText = (EditText) promptView.findViewById(R.id.dialog_example_textEdit);
        headwordEditText.setText(word.getHeadWord());
        partOfSpeechEditText.setText(word.getPartOfSpeech());
        definitionEditText.setText(word.getDefinition());
        exampleEditText.setText(word.getExample());
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String partOfSpeech = partOfSpeechEditText.getText().toString();
                        if (partOfSpeech.length() == 0) partOfSpeech = null;
                        String example = exampleEditText.getText().toString();
                        if (example.length() == 0) example = null;
                        String headword = headwordEditText.getText().toString();
                        String definition = definitionEditText.getText().toString();
                        if (headword.length() == 0 || definition.length() == 0) {
                            Toast.makeText(context, "Sorry, fields can't be empty",
                                    Toast.LENGTH_SHORT).show();
                            editWordDialog(context, word);
                        } else {
                            word.setData(partOfSpeech, headword, definition, example);
                            CategoryWordsActivity.notifyAboutWordsChanging();
                            MainActivity.notifyAboutCategoriesChanging();
                        }
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
        View promptView = layoutInflater.inflate(R.layout.input_category_name_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        final EditText editText = (EditText) promptView.findViewById(R.id.dialog_category_name_editText);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CategoriesData.getCategory(position)
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
                    CategoryModel category = CategoriesData.getCategory(item);
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
        String[] items = {"Move to", "Edit", "Delete"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0: {
                       AppDialogs.moveToAnotherCategoryDialog(context, words, wordPosition);
                    } break;
                    case 1: {
                        editWordDialog(context, words.get(wordPosition));
                    } break;
                    case 2: {
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
                    CategoriesData.getCategory(item).addWord(word);
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
        builder.setTitle("Choose a quiz type: ");
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
