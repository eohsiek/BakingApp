package com.example.android.bakingapp.Network;

import android.os.AsyncTask;

import com.example.android.bakingapp.MainActivity;

import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetRecipeAsync extends AsyncTask<String, String, String> {

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
    protected String doInBackground(String... params) {
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            String responsestring = response.body().string();
            return responsestring;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        taskCompleted.onTaskCompleted(result);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(String response);
    }
}

