package com.example.android.bakingapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.Data.IngredientsAdapter;
import com.example.android.bakingapp.Data.Recipe;
import com.example.android.bakingapp.databinding.FragmentRecipeBinding;

public class RecipeFragment extends Fragment {

    private RecyclerView ingredientsRV;
    private LinearLayoutManager ingredientsLayoutManager;
    private IngredientsAdapter ingredientsAdapter;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Recipe recipe = getArguments().getParcelable("recipe");
        Ingredients[] ingredients = (Ingredients[]) getArguments().getParcelableArray("ingredients");

        Log.i("FragmentIngredients", ingredients.toString());

        FragmentRecipeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false);

        binding.setRecipe(recipe);
        View view = binding.getRoot();

        ingredientsRV = view.findViewById(R.id.rv_ingredients);
        ingredientsLayoutManager = new LinearLayoutManager(this.getActivity());
        ingredientsRV.setLayoutManager(ingredientsLayoutManager);
        ingredientsAdapter = new IngredientsAdapter(ingredients);
        ingredientsRV.setAdapter(ingredientsAdapter);

        return view;
    }

}
