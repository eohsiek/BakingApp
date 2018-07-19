package com.example.android.bakingapp.Data;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.IngredientsRecyclerViewBinding;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private Ingredients[] ingredients;

    public IngredientsAdapter(Ingredients[] ingredients) {
        this.ingredients = ingredients;
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder  {

        private IngredientsRecyclerViewBinding binding;

        public IngredientsViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }

        public void bind(Ingredients ingredient) {
            binding.setIngredient(ingredient);
        }

    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View statusContainer = inflater.inflate(R.layout.ingredients_recycler_view, parent, false);

        return new IngredientsViewHolder(statusContainer);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        Ingredients status = ingredients[position];
        holder.bind(status);

    }

    @Override
    public int getItemCount() {
        return ingredients.length;
    }
}
