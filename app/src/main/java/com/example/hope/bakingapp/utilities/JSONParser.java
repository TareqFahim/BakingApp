package com.example.hope.bakingapp.utilities;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Hope on 8/1/2017.
 */

public interface JSONParser {
    RecipeData parseJson (String jsonStr)throws JSONException;
}
