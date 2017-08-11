package com.worlds.prudnikoff.worlds;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryHolder> {

    private ArrayList<CategoryModel> categories;

    public CategoryListAdapter(ArrayList<CategoryModel> categories) {
        this.categories = categories;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_view,
                parent, false);
        return new CategoryHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(CategoryHolder categoryHolder, int position) {
        CategoryModel category = categories.get(position);
        categoryHolder.nameOfCategory.setText(category.getNameOfCategory());
        categoryHolder.dateAndTime.setText(category.getDateAndTime());
        categoryHolder.numOfWords.setText(category.getNumOfWords());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder {

        TextView nameOfCategory;
        TextView dateAndTime;
        TextView numOfWords;

        CategoryHolder(View itemView) {
            super(itemView);
            nameOfCategory = (TextView)itemView.findViewById(R.id.name_of_category_textView);
            dateAndTime = (TextView)itemView.findViewById(R.id.date_and_time_textView);
            numOfWords = (TextView)itemView.findViewById(R.id.num_of_words_textView);
        }
    }
}
