package com.example.hope.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.hope.bakingapp.ui.MainActivity;
import com.example.hope.bakingapp.utilities.NetworkUtils;
import com.example.hope.bakingapp.utilities.RecipeData;
import com.example.hope.bakingapp.utilities.RecipeJSONParser;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URL;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    private RecipeData mRecipeData;
    String json;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void getRecipesData(){
        URL jsonUrl = NetworkUtils.buildRecipeURL();
        try {
            json = NetworkUtils.getResponseFromHttpUrl(jsonUrl);
            mRecipeData = new RecipeJSONParser().parseJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkRecipeNames() throws Exception {
        onView(withId(R.id.rv_recipes_list))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText((String) mRecipeData.recipeName.get(0)))));
    }

    @Test
    public void checkRecipeCount() throws Exception {
        onView(withId(R.id.rv_recipes_list))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText((String) mRecipeData.recipeName.get(mRecipeData.recipeName.size() - 1)))));
    }

    @Test
    public void checkRecipeDetails() throws Exception {
        onView(withId(R.id.rv_recipes_list))
                .perform(RecyclerViewActions.scrollToPosition(0)).perform(click());
        onView(withId(R.id.rv_recipe_details_list))
                .perform(RecyclerViewActions.scrollToPosition(1)).perform(click());
//        onView(withId(R.id.recipe_step_rv)).check(matches(hasDescendant(withId(R.id.exoplayer_view))));
        onView(withId(R.id.recipe_step_rv))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withId(R.id.exoplayer_view))))
                .check(matches(isDisplayed()));

    }
}