package com.example.hope.bakingapp.widget;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViewsService;

import com.example.hope.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hope on 8/10/2017.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        Bundle b = intent.getBundleExtra(getString(R.string.ingredients_intent_extra));
        List ingredientsList;
        ingredientsList = b.getStringArrayList(getString(R.string.ingredients_intent_extra));

        return new WidgetListProvider(getApplicationContext(), ingredientsList);
    }
}
