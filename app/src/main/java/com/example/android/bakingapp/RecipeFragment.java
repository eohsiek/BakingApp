package com.example.android.bakingapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.Data.IngredientsAdapter;
import com.example.android.bakingapp.Data.Recipe;
import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.Data.StepsAdapter;
import com.example.android.bakingapp.databinding.FragmentRecipeBinding;

public class RecipeFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler {

    private RecyclerView ingredientsRV;
    private RecyclerView stepsRV;
    private LinearLayoutManager ingredientsLayoutManager;
    private LinearLayoutManager stepsLayoutManager;
    private IngredientsAdapter ingredientsAdapter;
    private StepsAdapter stepsAdapter;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Recipe recipe = getArguments().getParcelable("recipe");
        Ingredients[] ingredients = getArguments().getParcelableArrayList("ingredients").toArray(new Ingredients[0]);
        Steps[] steps = getArguments().getParcelableArrayList("steps").toArray(new Steps[0]);

        FragmentRecipeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false);

        binding.setRecipe(recipe);
        View view = binding.getRoot();

        ingredientsRV = view.findViewById(R.id.rv_ingredients);
        ingredientsLayoutManager = new LinearLayoutManager(this.getActivity());
        ingredientsRV.setLayoutManager(ingredientsLayoutManager);
        ingredientsAdapter = new IngredientsAdapter(ingredients);
        ingredientsRV.setAdapter(ingredientsAdapter);

        stepsRV = view.findViewById(R.id.rv_steps);
        stepsLayoutManager = new LinearLayoutManager(this.getActivity());
        stepsRV.setLayoutManager(stepsLayoutManager);
        stepsAdapter = new StepsAdapter(this, steps);
        stepsRV.setAdapter(stepsAdapter);

        return view;
    }

    @Override
    public void onClick(Steps step) {
        Log.i("Clicked", "fragment");
        Context context =  getActivity();
        CharSequence text = "Clicked the step!";
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();
    }
}
