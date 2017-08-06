package com.example.hope.bakingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hope.bakingapp.R;
import com.example.hope.bakingapp.utilities.SelectedRecipeData;

public class RecipeIngredientsActivity extends AppCompatActivity {
    private SelectedRecipeData mSelectedRecipeData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients);
        Intent intent = getIntent();
        Bundle b = new Bundle();
        if(intent.hasExtra(getString(R.string.recipe_intent_extra))){
            mSelectedRecipeData = intent.getParcelableExtra(getString(R.string.recipe_intent_extra));
            b.putParcelable(getString(R.string.recipe_intent_extra), mSelectedRecipeData);
            RecipeIngredientsFragment recipeIngredientsFragment =  new RecipeIngredientsFragment();
            recipeIngredientsFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_recipe_ingredients, recipeIngredientsFragment).commit();
        }
    }
}
