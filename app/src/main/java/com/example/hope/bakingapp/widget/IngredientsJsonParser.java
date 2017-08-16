package com.example.hope.bakingapp.widget;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hope on 8/16/2017.
 */

public class IngredientsJsonParser {

    final String OWM_RECIPE_INGREDIENTS = "ingredients_json";
    List ingredients;

    public List parseJson(String json) throws Exception{

        JSONObject ingredientsJSONObject = new JSONObject(json);
        JSONArray ingredientsArray = ingredientsJSONObject.getJSONArray(OWM_RECIPE_INGREDIENTS);
        ingredients = new ArrayList();
        for (int i = 0; i < ingredientsArray.length(); i++) {
            this.ingredients.add(ingredientsArray.get(i));
        }
        return ingredients;
    }
}
