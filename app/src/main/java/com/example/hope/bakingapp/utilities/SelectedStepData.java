package com.example.hope.bakingapp.utilities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Hope on 8/5/2017.
 */

public class SelectedStepData implements Parcelable {
    public String stepVideo;
    public String stepInstruction;

    public SelectedStepData(String video, String instruction){
        this.stepVideo = video;
        this.stepInstruction = instruction;
    }

    protected SelectedStepData(Parcel in) {
        stepVideo = in.readString();
        stepInstruction = in.readString();
    }

    public static final Creator<SelectedStepData> CREATOR = new Creator<SelectedStepData>() {
        @Override
        public SelectedStepData createFromParcel(Parcel in) {
            return new SelectedStepData(in);
        }

        @Override
        public SelectedStepData[] newArray(int size) {
            return new SelectedStepData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stepVideo);
        dest.writeString(stepInstruction);
    }
}
