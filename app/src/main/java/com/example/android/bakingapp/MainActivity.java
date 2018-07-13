package com.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.bakingapp.Network.GetRecipeAsync;

public class MainActivity extends AppCompatActivity  implements
        GetRecipeAsync.OnTaskCompleted {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textfield);
        GetRecipeAsync task = new GetRecipeAsync(MainActivity.this);
        task.execute();
    }


    @Override
    public void onTaskCompleted(String response) {
        textView.setText(response);
    }
}
