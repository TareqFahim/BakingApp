package com.example.hope.bakingapp.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hope on 8/1/2017.
 */

public class RecipeJSONParser implements JSONParser {
    @Override
    public RecipeData parseJson(String jsonStr) throws JSONException {
        final String OWM_RECIPE_NAME = "name";
        final String OWM_RECIPE_ID = "id";

        final String OWM_RECIPE_INGREDIENTS = "ingredients";
        final String OWM_INGREDIENT_QUANTITY = "quantity";
        final String OWM_INGREDIENT_MEASURE = "measure";
        final String OWM_INGREDIENT_NAME = "ingredient";

        final String OWM_RECIPE_STEPS = "steps";
        final String OWM_STEP_DESCRIPTION = "shortDescription";
        final String OWM_STEP_INSTRUCTION = "description";
        final String OWM_STEP_VIDEO = "videoURL";
        final String OWM_STEP_IMAGE = "thumbnailURL";

        final String OWM_RECIPE_IMAGE = "image";

        RecipeData recipeData = new RecipeData();
        JSONArray recipesArray = new JSONArray(jsonStr);
        for (int i = 0; i < recipesArray.length(); i++) {
            String recipeName, recipeId;
            List recipeIngridient = new ArrayList();
            List recipeStepDescription = new ArrayList();
            List recipeStepInstruction = new ArrayList();
            List recipeStepVideo = new ArrayList();
            List recipeStepImage = new ArrayList();

            JSONObject recipeObject = recipesArray.getJSONObject(i);

            recipeName = recipeObject.getString(OWM_RECIPE_NAME);
            recipeData.recipeName.add(recipeName);

            recipeId = recipeObject.getString(OWM_RECIPE_ID);

            JSONArray recipeIngredientsArray = recipeObject.getJSONArray(OWM_RECIPE_INGREDIENTS);
            for (int j = 0; j < recipeIngredientsArray.length(); j++) {
                String quantity, measure, ingredientName, ingredient;

                JSONObject ingredientObject = recipeIngredientsArray.getJSONObject(j);

                quantity = ingredientObject.getString(OWM_INGREDIENT_QUANTITY);
                measure = ingredientObject.getString(OWM_INGREDIENT_MEASURE);
                ingredientName = ingredientObject.getString(OWM_INGREDIENT_NAME);
                ingredient = quantity + " " + measure + " of " + ingredientName;

                recipeIngridient.add(ingredient);
            }
            recipeData.recipeIngridient.put(recipeId, (ArrayList) recipeIngridient);

            JSONArray stepsArray = recipeObject.getJSONArray(OWM_RECIPE_STEPS);
            for (int j = 0; j < stepsArray.length(); j++) {
                String description, instruction, video, stepImage;

                JSONObject stepObject = stepsArray.getJSONObject(j);

                description = stepObject.getString(OWM_STEP_DESCRIPTION);
                recipeStepDescription.add(description);

                instruction = stepObject.getString(OWM_STEP_INSTRUCTION);
                recipeStepInstruction.add(instruction);

                video = stepObject.getString(OWM_STEP_VIDEO);
                recipeStepVideo.add(video);

                stepImage = stepObject.getString(OWM_STEP_IMAGE);
                recipeStepImage.add(stepImage);

            }
            recipeData.recipeStepDescription.put(recipeId, (ArrayList) recipeStepDescription);
            recipeData.recipeStepInstruction.put(recipeId, (ArrayList) recipeStepInstruction);
            recipeData.recipeStepVideo.put(recipeId, (ArrayList) recipeStepVideo);
            recipeData.recipeStepImage.put(recipeId, (ArrayList) recipeStepImage);

            String image = recipeObject.getString(OWM_RECIPE_IMAGE);
            recipeData.recipeImage.add(image);
        }

        return recipeData;
    }
}
