package com.example.android.bakingapp;

import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.Data.Steps;


public class RecipeActivity extends AppCompatActivity {

    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Bundle bundle = getIntent().getExtras();

        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setArguments(bundle);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.recipe_container, recipeFragment);

        // This container will only initially exist in the two-pane tablet case
        if(findViewById(R.id.steps_container) != null) {

            Steps[] steps = bundle.getParcelableArrayList("steps").toArray(new Steps[0]);
            Steps firststep = steps[0];
            Bundle bundlesteps = new Bundle();
            bundlesteps.putParcelable("step", firststep);

            StepsFragment stepsFragment = new StepsFragment();
            stepsFragment.setArguments(bundlesteps);

            transaction.add(R.id.steps_container, stepsFragment);
        }

        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}
