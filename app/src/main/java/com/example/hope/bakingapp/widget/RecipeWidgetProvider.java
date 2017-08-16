package com.example.hope.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.example.hope.bakingapp.R;
import com.example.hope.bakingapp.adapters.RecipeDetailsAdapter;
import com.example.hope.bakingapp.db.FavouriteRecipeData;
import com.example.hope.bakingapp.db.RecipeDbHelper;
import com.example.hope.bakingapp.ui.MainActivity;
import com.example.hope.bakingapp.ui.RecipeIngredientsActivity;
import com.example.hope.bakingapp.utilities.SelectedRecipeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    RecipeDbHelper mRecipeDbHelper;
    FavouriteRecipeData mFavouriteRecipeData;
    List favRecipesNames, lastVistedRecipeingredient;
    SelectedRecipeData mSelectedRecipeData;
    SharedPreferences prefs;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        mRecipeDbHelper = new RecipeDbHelper(context);
        mFavouriteRecipeData = mRecipeDbHelper.getData();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int recipeIndex = prefs.getInt(context.getString(R.string.preferences_recipe_index), 0);
        if(mFavouriteRecipeData == null){
            favRecipesNames = new ArrayList();
            favRecipesNames.add("");
            mSelectedRecipeData = new SelectedRecipeData(new ArrayList(), null, null, null, null);
        }else{
            favRecipesNames = mFavouriteRecipeData.recipeName;
            try {
                lastVistedRecipeingredient = new IngredientsJsonParser().parseJson((String) mFavouriteRecipeData.recipeIngredients.get(recipeIndex));
            } catch (Exception e) {
                e.printStackTrace();
            }
            mSelectedRecipeData = new SelectedRecipeData(mFavouriteRecipeData.recipeIngredients, null, null, null, null);
        }
        for (int appWidgetId : appWidgetIds) {
            Intent activityIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredient_widget);
            rv.setPendingIntentTemplate(R.id.widget_listview, pendingIntent);
            if (favRecipesNames == null) {
                rv.setViewVisibility(R.id.empty_text_view, View.VISIBLE);
                appWidgetManager.updateAppWidget(appWidgetId, rv);
            } else {
                Intent intent = new Intent(context, WidgetService.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(context.getString(R.string.ingredients_intent_extra), (ArrayList<String>) lastVistedRecipeingredient);
                intent.putExtra(context.getString(R.string.ingredients_intent_extra), bundle);

                rv.setRemoteAdapter(R.id.widget_listview, intent);
                appWidgetManager.updateAppWidget(appWidgetId, rv);
            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}

