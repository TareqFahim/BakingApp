package com.example.hope.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.hope.bakingapp.R;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * Created by Hope on 8/10/2017.
 */

public class WidgetListProvider implements RemoteViewsService.RemoteViewsFactory {

    private List ingredientsList = new ArrayList();
    private Context context;

    public WidgetListProvider(Context context, List list){
        this.ingredientsList = list;
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(ingredientsList == null)
            return 0;
        return ingredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
        remoteViews.setTextViewText(R.id.wideget_textview, (CharSequence) ingredientsList.get(position));

        final Intent activityIntent = new Intent();
        activityIntent.putExtra(context.getResources().getString(R.string.ingredients_intent_extra), (String) ingredientsList.get(position));
        remoteViews.setOnClickFillInIntent(R.id.wideget_textview, activityIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
