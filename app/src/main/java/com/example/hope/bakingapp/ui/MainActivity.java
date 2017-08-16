package com.example.hope.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.hope.bakingapp.FetchJSONAsyncTask;
import com.example.hope.bakingapp.R;
import com.example.hope.bakingapp.adapters.RecipeGridAdapter;
import com.example.hope.bakingapp.db.FavouriteRecipeData;
import com.example.hope.bakingapp.db.RecipeDbHelper;
import com.example.hope.bakingapp.utilities.NetworkUtils;
import com.example.hope.bakingapp.utilities.RecipeData;
import com.example.hope.bakingapp.utilities.RecipeJSONParser;
import com.example.hope.bakingapp.utilities.SelectedRecipeData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FetchJSONAsyncTask.JsonCallBack, RecipeGridAdapter.GridItemClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private RecipeGridAdapter mRecipeGridAdapter;
    private URL jsonUrl;
    private RecipeData recipesData;
    private SelectedRecipeData mSelectedRecipeData;
    private RecipeDbHelper mRecipeDbHelper;
    FavouriteRecipeData mFavouriteRecipeData;

    @BindView(R.id.rv_recipes_list)
    RecyclerView mRecipeGrid;
    ;

    @Override
    protected void onStart() {
        super.onStart();

        mRecipeDbHelper = new RecipeDbHelper(this);
        mFavouriteRecipeData = mRecipeDbHelper.getData();

        FetchJSONAsyncTask recipesAsyncTask = new FetchJSONAsyncTask();
        recipesAsyncTask.setCallBackContext(this);
        jsonUrl = NetworkUtils.buildRecipeURL();
        recipesAsyncTask.execute(jsonUrl);
    }

    public boolean isTablet(Context context) {
        return getResources().getBoolean(R.bool.two_pane);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        int noOfColumns;
        if (isTablet(this)) {
            noOfColumns = 4;
        } else {
            noOfColumns = 1;
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this, noOfColumns);
        mRecipeGrid.setLayoutManager(layoutManager);
        mRecipeGrid.setHasFixedSize(true);
    }

    @Override
    public void setJson(String json) {
        if (json == null) {
            Toast.makeText(this, "Error while fetching Recipes from Network", Toast.LENGTH_LONG).show();
        } else {
            callJsonParser(json);
            setMovieGridAdapter();
        }
    }

    private void callJsonParser(String json) {
        try {
            recipesData = new RecipeJSONParser().parseJson(json);
            for (int i = 0; i < recipesData.recipeName.size(); i++) {
                if (mFavouriteRecipeData != null && recipesData.recipeName.get(i).equals(mFavouriteRecipeData.recipeName.get(i)))
                    continue;
                else
                    addDataToDb(i);
            }
        } catch (JSONException ex) {
            Log.e(LOG_TAG, "JSON EXCEPTION");
        }
    }

    private void addDataToDb(int id) {
        mRecipeDbHelper = new RecipeDbHelper(this);
        // Convert List to JSON
        JSONObject ingredientsJson = new JSONObject();
        try {
            ingredientsJson.put("ingredients_json", new JSONArray(recipesData.recipeIngridient.get(Integer.toString(id + 1))));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String ingredientJsonStr = ingredientsJson.toString();
        mRecipeDbHelper.insertDateToTable(Integer.toString(id + 1), (String) recipesData.recipeName.get(id), ingredientJsonStr);
    }

    private void setMovieGridAdapter() {
        mRecipeGridAdapter = new RecipeGridAdapter(this, recipesData.recipeName, recipesData.recipeImage, this);
        mRecipeGrid.setAdapter(mRecipeGridAdapter);
    }

    @Override
    public void onGridItemClick(int itemIndex, String clickedItemName) {
//        Toast.makeText(this, (String) recipesData.recipeName.get(itemIndex), Toast.LENGTH_LONG).show();
        if (clickedItemName.equals("FavBtn")) {
//            addDataToDb(itemIndex);
            Toast.makeText(this, "Marked As Favourite", Toast.LENGTH_SHORT).show();
        } else {
            String index = Integer.toString(itemIndex + 1);

            mSelectedRecipeData = new SelectedRecipeData(recipesData.recipeIngridient.get(index)
                    , recipesData.recipeStepDescription.get(index)
                    , recipesData.recipeStepInstruction.get(index)
                    , recipesData.recipeStepVideo.get(index)
                    , (String) recipesData.recipeImage.get(itemIndex));
            mSelectedRecipeData.itemIndex = itemIndex;
            mSelectedRecipeData.stepImages = recipesData.recipeStepImage.get(index);
            Intent startRecipeDetailsIntent = new Intent(this, RecipeDetailsActivity.class);
            startRecipeDetailsIntent.putExtra(getString(R.string.recipe_intent_extra), mSelectedRecipeData);
            startActivity(startRecipeDetailsIntent);
        }
    }
}
