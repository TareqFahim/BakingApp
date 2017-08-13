package com.example.hope.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.hope.bakingapp.R;
import com.example.hope.bakingapp.utilities.SelectedRecipeData;

import java.util.zip.Inflater;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class RecipeStepActivity extends AppCompatActivity implements RecipeStepFragment.Callback {
    private SelectedRecipeData mSelectedRecipeData;
    private Bundle b;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_recipe_step);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.step_intent_extra))) {
            mSelectedRecipeData = intent.getParcelableExtra(getString(R.string.step_intent_extra));
            createNewStepFragment();
        }
    }

    private void createNewStepFragment(){
        b = new Bundle();
        b.putParcelable(getString(R.string.recipe_intent_extra), mSelectedRecipeData);
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_recipe_step, recipeStepFragment).commit();
    }

    @Override
    public void fragmentCallback(int itemIndex) {
        mSelectedRecipeData.itemIndex = itemIndex;
        createNewStepFragment();
    }
}
