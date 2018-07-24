package com.example.android.bakingapp.Network;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.bakingapp.Data.Recipe;
import com.example.android.bakingapp.MainActivity;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetRecipeAsync extends AsyncTask<String, String, Recipe[]> {

    private Context context;

    final OkHttpClient client = new OkHttpClient();
    final Request request = new Request.Builder()
            .cacheControl(new CacheControl.Builder()
                    .maxStale(365, TimeUnit.DAYS)
                    .build())
            .url("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
            .build();
    private OnTaskCompleted taskCompleted;

    public GetRecipeAsync(MainActivity mainActivity){
        this.taskCompleted = mainActivity;
    }

    @Override
    protected Recipe[] doInBackground(String... params) {
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            String responsestring = response.body().string();
            Gson gson = new Gson();
            Recipe[] recipeArray = gson.fromJson(responsestring, Recipe[].class);
            return recipeArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Recipe[] result) {

        taskCompleted.onTaskCompleted(result);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(Recipe[] response);
    }
}

