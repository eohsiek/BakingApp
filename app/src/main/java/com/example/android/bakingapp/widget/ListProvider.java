package com.example.android.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Ingredients;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList listItemList = new ArrayList();
    private Context context = null;
    private int appWidgetId;
    public static final String RECIPES = "SavedRecipes";
    public static final String PREF_INGREDIENTS = "ingredients";
    public String ingredients;
    public Ingredients[] ingredientslist;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        populateListItem();
    }

    private void populateListItem() {
        SharedPreferences preferences = context.getSharedPreferences(RECIPES,Context.MODE_PRIVATE);

        this.ingredients = preferences.getString(PREF_INGREDIENTS, null);
        Gson gson = new Gson();
        this.ingredientslist = gson.fromJson(this.ingredients, Ingredients[].class);

        if(this.ingredientslist == null){
            listItemList.add(new Ingredients());
        }
        else {
            for (Ingredients ingredient: this.ingredientslist) {
                listItemList.add(ingredient);
            }
        }

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /*
     *Similar to getView of Adapter where instead of View
     *we return RemoteViews
     *
     */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.ingredient_item_widget);
        Ingredients ingredient = (Ingredients) listItemList.get(position);
        remoteView.setTextViewText(R.id.ingredientitem, ingredient.getIngredientinfo());;

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}
