package com.example.android.bakingapp.Data;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.RecipeRecyclerViewBinding;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Recipe[] recipes;
    private RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler, Recipe[] recipes) {
        this.recipes = recipes;
        mClickHandler = clickHandler;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecipeRecyclerViewBinding binding;

        public RecipeViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
            view.setOnClickListener(this);
        }

        public void bind(Recipe recipe) {
            binding.setRecipe(recipe);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = recipes[adapterPosition];
            mClickHandler.onClick(recipe);
        }
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
