package com.example.hope.bakingapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hope.bakingapp.widget.RecipeWidgetProvider;

/**
 * Created by Hope on 8/12/2017.
 */

public class RecipeDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorite_recipes.db";
    private static final int DATABASE_VERSION = 6;

    public RecipeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_FAVOURITE_TABLE = "CREATE TABLE IF NOT EXISTS " + RecipesDbContract.TABLE_NAME + " (" +
                RecipesDbContract.COLUMN_RECIPE_NAME + " TEXT, " +
                RecipesDbContract.COLUMN_RECIPE_INGREDIENTS + "  TEXT, " +
                RecipesDbContract.COLUMN_RECIPE_ID + " TEXT PRIMARY KEY" +
                ");";
        db.execSQL(SQL_CREATE_FAVOURITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + RecipesDbContract.TABLE_NAME;
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void insertDateToTable(String recipe_id, String recipe_name, String recipe_ingredients){
        SQLiteDatabase ddb = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(RecipesDbContract.COLUMN_RECIPE_ID, recipe_id);
        val.put(RecipesDbContract.COLUMN_RECIPE_NAME, recipe_name);
        val.put(RecipesDbContract.COLUMN_RECIPE_INGREDIENTS, recipe_ingredients);

        ddb.insert(RecipesDbContract.TABLE_NAME, null, val);
        ddb.close();
    }

    public FavouriteRecipeData getData(){
        SQLiteDatabase datab = this.getReadableDatabase();

        Cursor favRecipeData = datab.rawQuery("select * from " + RecipesDbContract.TABLE_NAME + ";", null);

        if(favRecipeData.getCount() <= 0)
            return null;
        favRecipeData.moveToFirst();
        FavouriteRecipeData favRecipe = new FavouriteRecipeData();
        do{
            favRecipe.recipeID.add(favRecipeData.getString(favRecipeData.getColumnIndex(RecipesDbContract.COLUMN_RECIPE_ID)));
            favRecipe.recipeName.add(favRecipeData.getString(favRecipeData.getColumnIndex(RecipesDbContract.COLUMN_RECIPE_NAME)));
            favRecipe.recipeIngredients.add(favRecipeData.getString(favRecipeData.getColumnIndex(RecipesDbContract.COLUMN_RECIPE_INGREDIENTS)));
        }while (favRecipeData.moveToNext());
        datab.close();
        return favRecipe;
    }
}
