package com.example.android.bakingapp.Data;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.bakingapp.databinding.RecipeRecyclerViewBinding;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    private RecipeRecyclerViewBinding binding;

    public RecipeViewHolder(View view) {
        super(view);
        binding = DataBindingUtil.bind(view);
    }

    public void bind(Recipe recipe) {
        binding.setRecipe(recipe);
    }
}
