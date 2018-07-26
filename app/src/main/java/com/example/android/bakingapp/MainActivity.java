package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.Data.Recipe;
import com.example.android.bakingapp.Data.RecipeAdapter;
import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.Network.GetRecipeAsync;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity  implements
        GetRecipeAsync.OnTaskCompleted, RecipeAdapter.RecipeAdapterOnClickHandler {

    private int columns;
    private TextView textView;
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private TextView errorMessage;
    private TextView recipeHeader;
    public static final String RECIPES = "SavedRecipes";
    private String recipeJSON;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences(RECIPES, MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        columns = getResources().getInteger(R.integer.columns);
        errorMessage =  findViewById(R.id.networkerror);
        recipeHeader = findViewById(R.id.selectrecipe);

        recipeJSON = preferences.getString("recipeJSON", null);

        if (recipeJSON == null) {
            //recipes not in shared preferences, get from source if available, else show error
            if(isOnline()) {
                GetRecipeAsync task = new GetRecipeAsync(MainActivity.this);
                task.execute();
            } else {  // no network or shared pref - show error
                showError();
            }
        }
        else{
            showRecipes();
        }

    }


    @Override
    public void onTaskCompleted(String jsonString) {
        recipeJSON = jsonString;
        SharedPreferences.Editor editor = getSharedPreferences(RECIPES, MODE_PRIVATE).edit();
        editor.putString("recipeJSON", recipeJSON);
        editor.apply();

        showRecipes();
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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private  void showRecipes() {
        errorMessage.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        recipeHeader.setVisibility(View.VISIBLE);

        Gson gson = new Gson();
        Recipe[] recipeArray = gson.fromJson(recipeJSON, Recipe[].class);

        adapter = new RecipeAdapter(this, recipeArray);
        recyclerView.setAdapter(adapter);
        layoutManager = new GridLayoutManager(this, columns);
        recyclerView.setLayoutManager(layoutManager);
    }

    private  void showError() {
        errorMessage.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        recipeHeader.setVisibility(View.INVISIBLE);
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo network = connectivityManager.getActiveNetworkInfo();
        return network != null && network.isConnectedOrConnecting();
    }
}
