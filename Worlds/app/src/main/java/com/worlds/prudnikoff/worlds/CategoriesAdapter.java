package com.worlds.prudnikoff.worlds;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;

class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryHolder> {

    private ArrayList<CategoryModel> categories;

    CategoriesAdapter(ArrayList<CategoryModel> categories) {
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
        categoryHolder.numOfWords.setText(String.valueOf(category.getNumOfWords()));
        if (category.isTop()) {
            categoryHolder.starButton.setImageResource(R.drawable.ic_star);
        } else categoryHolder.starButton.setImageResource(R.drawable.ic_star_border);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        TextView nameOfCategory;
        TextView dateAndTime;
        TextView numOfWords;
        ImageButton starButton;
        CardView cardView;

        CategoryHolder(View itemView) {
            super(itemView);
            nameOfCategory = (TextView)itemView.findViewById(R.id.name_of_category_textView);
            dateAndTime = (TextView)itemView.findViewById(R.id.date_and_time_textView);
            numOfWords = (TextView)itemView.findViewById(R.id.num_of_words_textView);
            starButton = (ImageButton)itemView.findViewById(R.id.star_button);
            cardView = (CardView)itemView.findViewById(R.id.category_cardView);
            cardView.setOnClickListener(this);
            cardView.setOnLongClickListener(this);
            starButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            CategoryModel category = CategoriesData.getCategory(adapterPosition);
            if (v.getId() == starButton.getId()) {
                category.changeTop();
                if (category.isTop()) {
                    cardView.startAnimation(AnimationUtils.loadAnimation(v.getContext(),
                            android.R.anim.slide_in_left));
                    CategoriesData.toTop(adapterPosition);
                } else CategoriesData.toLow(adapterPosition);
                cardView.startAnimation(AnimationUtils.loadAnimation(v.getContext(),
                        android.R.anim.slide_in_left));
                MainActivity.notifyAboutCategoriesChanging();
                Animation animation = AnimationUtils.loadAnimation(v.getContext(), android.R.anim.slide_in_left);
                cardView.startAnimation(animation);
            }
            if (v.getId() == cardView.getId()) {
                Intent intent = new Intent(v.getContext(), CategoryWordsActivity.class);
                intent.putExtra("categoryPosition", getAdapterPosition());
                intent.putExtra("ifCategoryWord", false);
                intent.putExtra("headword", "");
                intent.putExtra("nameOfCategory", category.getNameOfCategory());
                v.getContext().startActivity(intent);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (v.getId() == cardView.getId()) {
                AppDialogs.showCategoryOptionsDialog(v.getContext(), getAdapterPosition());
            }
            return true;
        }
    }
}
