package com.example.hope.bakingapp;

import android.os.AsyncTask;
import android.view.SurfaceHolder;

/**
 * Created by Hope on 8/5/2017.
 */

public class FetchVideoFromInternet extends AsyncTask<String , Void, Void> implements SurfaceHolder.Callback {
    @Override
    protected Void doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

}
