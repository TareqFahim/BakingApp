package com.example.hope.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hope.bakingapp.R;
import com.example.hope.bakingapp.adapters.RecipeGridAdapter;
import com.example.hope.bakingapp.utilities.SelectedRecipeData;

/**
 * Created by Hope on 8/3/2017.
 */

public class RecipeIngredientsFragment extends Fragment implements RecipeGridAdapter.GridItemClickListener {

    View rootView;
    private RecyclerView mIngredientsRecyclerView;
    private RecipeGridAdapter mIngredientListAdapter;
    private SelectedRecipeData mSelectedRecipeIngredients;

    public RecipeIngredientsFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle b = getArguments();
        if(b != null){
//            mSelectedRecipeIngredients = intent.getParcelableExtra(getString(R.string.ingredients_intent_extra));
            mSelectedRecipeIngredients = b.getParcelable(getString(R.string.recipe_intent_extra));
            mIngredientListAdapter = new RecipeGridAdapter(getActivity(), mSelectedRecipeIngredients.ingredients, this);
            mIngredientsRecyclerView.setAdapter(mIngredientListAdapter);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        mIngredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recipe_ingredients_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mIngredientsRecyclerView.setLayoutManager(layoutManager);
        mIngredientsRecyclerView.setHasFixedSize(true);
        return rootView;
    }

    @Override
    public void onGridItemClick(int itemIndex) {

    }
}
