package com.example.hope.bakingapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.hope.bakingapp.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Hope on 8/1/2017.
 */

public class FetchJSONAsyncTask extends AsyncTask<URL, Void, String> {
    private static final String TAG = FetchJSONAsyncTask.class.getSimpleName();
    private JsonCallBack mReturnJson;

    @Override
    protected String doInBackground(URL... params) {
        String json = null;
        URL url = params[0];
        try {
            json = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
//        Log.v(TAG, json);
        return json;
    }

    public void setCallBackContext(JsonCallBack c) {
        this.mReturnJson = c;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null) {
            super.onPostExecute(s);
            mReturnJson.setJson(s);
        }else {
            mReturnJson.setJson(null);
        }
    }

    public interface JsonCallBack {
        void setJson(String json);
    }
}
