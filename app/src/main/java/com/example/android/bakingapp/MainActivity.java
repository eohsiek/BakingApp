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


        layoutManager = new GridLayoutManager(this, columns);
        recyclerView.setLayoutManager(layoutManager);

        GetRecipeAsync task = new GetRecipeAsync(MainActivity.this);
        task.execute();

    }

    @Override
    public void onTaskCompleted(Recipe[] response) {
        adapter = new RecipeAdapter(this, response);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(Recipe recipe) {
        Ingredients[] ingredients = recipe.getIngredients();
        ArrayList<Ingredients> arrayIngredients = new ArrayList<Ingredients>(Arrays.asList(ingredients));
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("recipe", recipe);
        intent.putParcelableArrayListExtra("ingredients", arrayIngredients);
        startActivity(intent);
    }
}
