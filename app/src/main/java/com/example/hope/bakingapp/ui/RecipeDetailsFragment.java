package com.example.hope.bakingapp.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hope.bakingapp.R;
import com.example.hope.bakingapp.adapters.RecipeGridAdapter;
import com.example.hope.bakingapp.utilities.FragmentReplacerCallBack;
import com.example.hope.bakingapp.utilities.SelectedRecipeData;
import com.example.hope.bakingapp.utilities.SelectedStepData;

import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeDetailsFragment extends Fragment implements RecipeGridAdapter.GridItemClickListener {

    private View rootView;
    private RecyclerView mDetailsRecyclerView;
    private SelectedRecipeData mSelectedRecipeData;
    private RecipeGridAdapter mRecipeGridAdapter;
    private FragmentReplacerCallBack mFragmentReplacerCallBack;
    List recipeDetailsList;
    private SelectedStepData mSelectedStepData;

    public RecipeDetailsFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra(getString(R.string.recipe_intent_extra))) {
            mSelectedRecipeData = intent.getParcelableExtra(getString(R.string.recipe_intent_extra));
            recipeDetailsList = new ArrayList();
            recipeDetailsList.add("Recipe Ingredients");
            recipeDetailsList.addAll(mSelectedRecipeData.steps);
            mRecipeGridAdapter = new RecipeGridAdapter(getActivity(), recipeDetailsList, this);
            mDetailsRecyclerView.setAdapter(mRecipeGridAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        mDetailsRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recipe_details_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mDetailsRecyclerView.setLayoutManager(layoutManager);
        mDetailsRecyclerView.setHasFixedSize(true);
        return rootView;
    }

    @Override
    public void onGridItemClick(int itemIndex) {
        mFragmentReplacerCallBack = (FragmentReplacerCallBack) getActivity();
        if (itemIndex == 0) {
            mFragmentReplacerCallBack.replaceFragment(new RecipeIngredientsFragment(), itemIndex);
        } else {
            mFragmentReplacerCallBack.replaceFragment(new RecipeStepFragment(), itemIndex);
        }
    }
}
