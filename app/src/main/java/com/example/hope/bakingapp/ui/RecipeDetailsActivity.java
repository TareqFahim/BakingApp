package com.example.hope.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hope.bakingapp.R;
import com.example.hope.bakingapp.adapters.RecipeGridAdapter;
import com.example.hope.bakingapp.utilities.FragmentReplacerCallBack;
import com.example.hope.bakingapp.utilities.SelectedRecipeData;

public class RecipeDetailsActivity extends AppCompatActivity implements FragmentReplacerCallBack {

    Fragment fragment;
    FragmentManager fragmentManager;
    boolean mTwoPane;
    private SelectedRecipeData mSelectedRecipeData;
    SharedPreferences prefs;
    private int itemIndex;

    @Override
    protected void onStart() {
        super.onStart();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        Intent getIntent = getIntent();
        if(getIntent.hasExtra(getString(R.string.recipe_intent_extra))){
            mSelectedRecipeData = getIntent.getParcelableExtra(getString(R.string.recipe_intent_extra));
            itemIndex = mSelectedRecipeData.itemIndex;
            editor.putInt(getString(R.string.preferences_recipe_index), itemIndex);
            editor.commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!isTablet(this)) {
            mTwoPane = false;
        } else {
            mTwoPane = true;
        }

        if(savedInstanceState == null) {
            fragment = new RecipeDetailsFragment();
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.master_container, fragment)
                    .commit();
        }
    }

    public boolean isTablet(Context context) {
        return getResources().getBoolean(R.bool.two_pane);
    }

    @Override
    public void replaceFragment(Fragment fragment, int itemIndex) {
        Bundle b;
        mSelectedRecipeData.itemIndex = itemIndex - 1;
        if(mTwoPane) {
            this.fragment = fragment;
            fragmentManager = getSupportFragmentManager();
            b = new Bundle();
            b.putParcelable(getString(R.string.recipe_intent_extra), mSelectedRecipeData);
            fragment.setArguments(b);
            fragmentManager.beginTransaction()
                    .replace(R.id.details_container, fragment)
                    .commit();
        }else {
            Intent intent;
            if (itemIndex == 0) {
                intent = new Intent(this, RecipeIngredientsActivity.class);
                intent.putExtra(getString(R.string.recipe_intent_extra), mSelectedRecipeData);
                startActivity(intent);
            } else {
                intent = new Intent(this, RecipeStepActivity.class);
                mSelectedRecipeData.itemIndex = itemIndex - 1;
                intent.putExtra(getString(R.string.step_intent_extra), mSelectedRecipeData);
                startActivity(intent);
            }
        }
    }
}
