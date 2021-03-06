package com.example.android.bakingapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.data.Ingredients;
import com.example.android.bakingapp.data.IngredientsAdapter;
import com.example.android.bakingapp.data.Recipe;
import com.example.android.bakingapp.data.Steps;
import com.example.android.bakingapp.data.StepsAdapter;
import com.example.android.bakingapp.databinding.FragmentRecipeBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler {

    private RecyclerView ingredientsRV;
    private RecyclerView stepsRV;
    private LinearLayoutManager ingredientsLayoutManager;
    private LinearLayoutManager stepsLayoutManager;
    private IngredientsAdapter ingredientsAdapter;
    private StepsAdapter stepsAdapter;
    private View view;
    private Steps[] steps;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        Recipe recipe = getArguments().getParcelable("recipe");
        Ingredients[] ingredients = getArguments().getParcelableArrayList("ingredients").toArray(new Ingredients[0]);
        steps = getArguments().getParcelableArrayList("steps").toArray(new Steps[0]);

        FragmentRecipeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false);

        binding.setRecipe(recipe);
        view = binding.getRoot();

        ingredientsRV = view.findViewById(R.id.rv_ingredients);
        ingredientsRV.setNestedScrollingEnabled(false);
        ingredientsLayoutManager = new LinearLayoutManager(this.getActivity());
        ingredientsRV.setLayoutManager(ingredientsLayoutManager);
        ingredientsAdapter = new IngredientsAdapter(ingredients);
        ingredientsRV.setAdapter(ingredientsAdapter);

        stepsRV = view.findViewById(R.id.rv_steps);
        stepsRV.setNestedScrollingEnabled(false);
        stepsLayoutManager = new LinearLayoutManager(this.getActivity());
        stepsRV.setLayoutManager(stepsLayoutManager);
        stepsAdapter = new StepsAdapter(this, steps);
        stepsRV.setAdapter(stepsAdapter);

        return view;
    }

    @Override
    public void onClick(Steps step, int position) {

        int arraylength = steps.length;
        ArrayList<Steps> arraySteps = new ArrayList<Steps>(Arrays.asList(steps));

        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);
        bundle.putParcelableArrayList("steps", arraySteps);
        bundle.putInt("numberSteps", arraylength);
        bundle.putInt("currentStep", position);

        if(getActivity().findViewById(R.id.steps_container) != null) {
            android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            StepsFragment stepsFragment = new StepsFragment();
            stepsFragment.setArguments(bundle);

            transaction.replace(R.id.steps_container, stepsFragment);
            transaction.commit();
        }
        else {
            Intent intent = new Intent(getActivity(), StepsActivity.class);

            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
