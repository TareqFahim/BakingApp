package com.example.hope.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    List favRecipesNames;
    SelectedRecipeData mSelectedRecipeData;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        mRecipeDbHelper = new RecipeDbHelper(context);
        mFavouriteRecipeData = mRecipeDbHelper.getData();
        if(mFavouriteRecipeData == null){
            favRecipesNames = new ArrayList();
            favRecipesNames.add("");
            mSelectedRecipeData = new SelectedRecipeData(new ArrayList(), null, null, null, null);
        }else{
            favRecipesNames = mFavouriteRecipeData.recipeName;
            mSelectedRecipeData = new SelectedRecipeData(mFavouriteRecipeData.recipeIngredients, null, null, null, null);
        }
        for (int appWidgetId : appWidgetIds) {
//            Intent activityIntent = new Intent(context, MainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);
            Intent activityIntent = new Intent(context, RecipeIngredientsActivity.class);
            activityIntent.putExtra("selected_recipe_data", mSelectedRecipeData);
//            activityIntent.setAction("com.blah.Action");
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredient_widget);
            rv.setPendingIntentTemplate(R.id.widget_listview, pendingIntent);
            if (favRecipesNames == null) {
                rv.setViewVisibility(R.id.empty_text_view, View.VISIBLE);
                appWidgetManager.updateAppWidget(appWidgetId, rv);
            } else {
                Intent intent = new Intent(context, WidgetService.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(context.getString(R.string.ingredients_intent_extra), (ArrayList<String>) favRecipesNames);
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

