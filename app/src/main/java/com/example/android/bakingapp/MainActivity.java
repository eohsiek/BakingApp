package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.Data.Recipe;
import com.example.android.bakingapp.Data.RecipeAdapter;
import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.Network.GetRecipeAsync;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity  implements
        GetRecipeAsync.OnTaskCompleted, RecipeAdapter.RecipeAdapterOnClickHandler {

    private int columns;
    private TextView textView;
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        columns = getResources().getInteger(R.integer.columns);

        GetRecipeAsync task = new GetRecipeAsync(MainActivity.this);
        task.execute();
    }

    @Override
    public void onTaskCompleted(Recipe[] response) {
        adapter = new RecipeAdapter(this, response);
        recyclerView.setAdapter(adapter);
        layoutManager = new GridLayoutManager(this, columns);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(Recipe recipe) {
        Ingredients[] ingredients = recipe.getIngredients();
        Steps[] steps = recipe.getSteps();

        ArrayList<Ingredients> arrayIngredients = new ArrayList<Ingredients>(Arrays.asList(ingredients));
        ArrayList<Steps> arraySteps = new ArrayList<Steps>(Arrays.asList(steps));

        Intent intent = new Intent(this, RecipeActivity.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipe);
        bundle.putParcelableArrayList("ingredients", arrayIngredients);
        bundle.putParcelableArrayList("steps", arraySteps);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
