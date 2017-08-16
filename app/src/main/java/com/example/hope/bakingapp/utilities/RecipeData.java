package com.example.hope.bakingapp.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Hope on 8/1/2017.
 */

public class RecipeData {
    public List recipeName = new ArrayList();
    public HashMap<String, ArrayList> recipeIngridient = new HashMap();
    public HashMap<String, ArrayList> recipeStepDescription = new HashMap();
    public HashMap<String, ArrayList> recipeStepInstruction = new HashMap();
    public HashMap<String, ArrayList> recipeStepVideo = new HashMap();
    public HashMap<String, ArrayList> recipeStepImage = new HashMap<>();
    public List recipeImage = new ArrayList();
}
