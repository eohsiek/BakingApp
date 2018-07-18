package com.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.android.bakingapp.Data.Recipe;
import com.example.android.bakingapp.Data.RecipeAdapter;
import com.example.android.bakingapp.Network.GetRecipeAsync;

public class MainActivity extends AppCompatActivity  implements
        GetRecipeAsync.OnTaskCompleted {

    private TextView textView;
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        GetRecipeAsync task = new GetRecipeAsync(MainActivity.this);
        task.execute();
    }


    @Override
    public void onTaskCompleted(Recipe[] response) {
        adapter = new RecipeAdapter(response);
        recyclerView.setAdapter(adapter);
    }
}
