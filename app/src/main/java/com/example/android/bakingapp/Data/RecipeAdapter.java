package com.example.android.bakingapp.Data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private Recipe[] recipes;

    public RecipeAdapter(Recipe[] recipes) {
        this.recipes = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View statusContainer = inflater.inflate(R.layout.recipe_recycler_view, parent, false);

        return new RecipeViewHolder(statusContainer);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe status = recipes[position];
        holder.bind(status);
    }


    @Override
    public int getItemCount() {
        return recipes.length;
    }
}
