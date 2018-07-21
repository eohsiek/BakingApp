package com.example.android.bakingapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.Data.Recipe;
import com.example.android.bakingapp.Data.Steps;

public class RecipeActivity extends AppCompatActivity {

    private Recipe recipe;
    private Ingredients[] ingredients;
    private Steps[] steps;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("recipe");
        ingredients = intent.getParcelableArrayListExtra("ingredients").toArray(new Ingredients[0]);
        steps = intent.getParcelableArrayListExtra("steps").toArray(new Steps[0]);

        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipe);
        bundle.putParcelableArray("ingredients", ingredients);
        bundle.putParcelableArray("steps", steps);

        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setArguments(bundle);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_container, recipeFragment)
                .commit();
    }
}
