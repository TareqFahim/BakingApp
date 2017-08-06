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
    public List stepsInstruactions = new ArrayList();
    public List stepsVideos = new ArrayList();
    public int itemIndex;

    public SelectedRecipeData(List ingredients,List steps, List stepsInstructions, List stepsVideos) {
        this.ingredients = ingredients;
        this.steps = steps;
        this.stepsInstruactions = stepsInstructions;
        this.stepsVideos = stepsVideos;
    }

    protected SelectedRecipeData(Parcel in) {
        ingredients = in.readArrayList(ClassLoader.getSystemClassLoader());
        steps = in.readArrayList(ClassLoader.getSystemClassLoader());
        stepsInstruactions = in.readArrayList(ClassLoader.getSystemClassLoader());
        stepsVideos = in.readArrayList(ClassLoader.getSystemClassLoader());
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
        dest.writeList(stepsInstruactions);
        dest.writeList(stepsVideos);
    }
}
