package com.example.hope.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hope.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hope on 8/1/2017.
 */

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsAdapter.RecipeDetailsViewHolder> {

    private List<String> recipesNames = new ArrayList();
    private Context context;
    private GridItemClickListener mGridItemClickListener;

    public RecipeDetailsAdapter(Context c, List recipesNames, GridItemClickListener clickListener) {
        this.context = c;
        this.recipesNames = recipesNames;
        this.mGridItemClickListener = clickListener;
    }

    @Override
    public RecipeDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.details_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        RecipeDetailsViewHolder viewHolder = new RecipeDetailsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeDetailsViewHolder viewHolder, int position) {
        viewHolder.recipeCardTextView.setText(recipesNames.get(position));
    }

    @Override
    public int getItemCount() {
        return recipesNames.size();
    }

    public class RecipeDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeCardTextView;


        public RecipeDetailsViewHolder(View itemView) {
            super(itemView);
            recipeCardTextView = (TextView) itemView.findViewById(R.id.details_card_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedItemPosition = getAdapterPosition();
            mGridItemClickListener.onGridItemClick(clickedItemPosition);
        }
    }

    public interface GridItemClickListener {
        void onGridItemClick(int itemIndex);
    }
}
