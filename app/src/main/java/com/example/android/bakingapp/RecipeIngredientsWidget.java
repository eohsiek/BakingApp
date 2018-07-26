package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.Data.Recipe;
import com.google.gson.Gson;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientsWidget extends AppWidgetProvider {

    public static final String PREF_SELECTED_NAME = "recipeName";
    public static final String RECIPES = "SavedRecipes";
    public static final String PREF_INGREDIENTS = "ingredients";
    public String recipeName;
    public String ingredients;
    public Ingredients[] ingredientslist;
    public ListView listview;

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredients_widget);
        views.setTextViewText(R.id.appwidget_text, this.recipeName + "\nIngredents");
        views.setTextViewText(R.id.ingredientslist, this.ingredients);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SharedPreferences preferences = context.getSharedPreferences(RECIPES,Context.MODE_PRIVATE);
        this.recipeName = preferences.getString(PREF_SELECTED_NAME, null);
        this.ingredients = preferences.getString(PREF_INGREDIENTS, null);

        Gson gson = new Gson();
        this.ingredientslist = gson.fromJson(this.ingredients, Ingredients[].class);
        ingredientsListAsString();
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public void ingredientsListAsString() {
        StringBuilder strBuilder = new StringBuilder("");
        for (Ingredients ingredient: this.ingredientslist) {
            strBuilder.append("- " + ingredient.getIngredientinfo() + "\n");
        }
        this.ingredients = strBuilder.toString();
    }
}

