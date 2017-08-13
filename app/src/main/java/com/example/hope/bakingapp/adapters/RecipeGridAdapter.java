package com.example.hope.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hope.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hope on 8/1/2017.
 */

public class RecipeGridAdapter extends RecyclerView.Adapter<RecipeGridAdapter.RecipeListViewHolder> {

    private List<String> recipesNames = new ArrayList();
    private Context context;
    private GridItemClickListener mGridItemClickListener;

    public RecipeGridAdapter(Context c, List recipesNames, GridItemClickListener clickListener) {
        this.context = c;
        this.recipesNames = recipesNames;
        this.mGridItemClickListener = clickListener;
    }

    @Override
    public RecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.recipes_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        RecipeListViewHolder viewHolder = new RecipeListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeListViewHolder viewHolder, final int position) {
        viewHolder.recipeCardTextView.setText(recipesNames.get(position));
        viewHolder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGridItemClickListener.onGridItemClick(position, "FavBtn");
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipesNames.size();
    }

    public class RecipeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeCardTextView;
        Button favBtn;


        public RecipeListViewHolder(View itemView) {
            super(itemView);
            recipeCardTextView = (TextView) itemView.findViewById(R.id.recipe_title_tv);
            favBtn = (Button) itemView.findViewById(R.id.fav_btn);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedItemPosition = getAdapterPosition();
            mGridItemClickListener.onGridItemClick(clickedItemPosition, "CardView");
        }
    }

    public interface GridItemClickListener {
        void onGridItemClick(int itemIndex, String clickedItemName);
    }
}
