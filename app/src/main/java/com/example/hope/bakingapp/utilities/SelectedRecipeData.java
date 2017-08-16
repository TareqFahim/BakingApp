package com.example.hope.bakingapp.utilities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hope on 8/2/2017.
 */

public class SelectedRecipeData implements Parcelable{

    public List ingredients = new ArrayList();
    public List steps = new ArrayList();
    public List stepsInstructions = new ArrayList();
    public List stepsVideos = new ArrayList();
    public List stepImages = new ArrayList();
    public int itemIndex;
//    public String recipeImage;

    public SelectedRecipeData(List ingredients,List steps, List stepsInstructions, List stepsVideos, String recipeImage) {
        this.ingredients = ingredients;
        this.steps = steps;
        this.stepsInstructions = stepsInstructions;
        this.stepsVideos = stepsVideos;
//        this.recipeImage = recipeImage;
    }

    protected SelectedRecipeData(Parcel in) {
        ingredients = in.readArrayList(ClassLoader.getSystemClassLoader());
        steps = in.readArrayList(ClassLoader.getSystemClassLoader());
        stepsInstructions = in.readArrayList(ClassLoader.getSystemClassLoader());
        stepsVideos = in.readArrayList(ClassLoader.getSystemClassLoader());
        stepImages = in.readArrayList(ClassLoader.getSystemClassLoader());
        itemIndex = in.readInt();
//        recipeImage = in.readString();
    }

    public static final Creator<SelectedRecipeData> CREATOR = new Creator<SelectedRecipeData>() {
        @Override
        public SelectedRecipeData createFromParcel(Parcel in) {
            return new SelectedRecipeData(in);
        }

        @Override
        public SelectedRecipeData[] newArray(int size) {
            return new SelectedRecipeData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(ingredients);
        dest.writeList(steps);
        dest.writeList(stepsInstructions);
        dest.writeList(stepsVideos);
        dest.writeList(stepImages);
        dest.writeInt(itemIndex);
//        dest.writeString(recipeImage);
    }
}
