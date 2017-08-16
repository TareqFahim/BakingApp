package com.example.hope.bakingapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hope.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hope on 8/1/2017.
 */

public class RecipeGridAdapter extends RecyclerView.Adapter<RecipeGridAdapter.RecipeListViewHolder> {

    private List<String> recipesNames = new ArrayList();
    private List recipeImagesURL = new ArrayList();
    private Context context;
    private GridItemClickListener mGridItemClickListener;

    public RecipeGridAdapter(Context c, List recipesNames, List recipeImagesURL, GridItemClickListener clickListener) {
        this.context = c;
        this.recipesNames = recipesNames;
        this.mGridItemClickListener = clickListener;
        this.recipeImagesURL = recipeImagesURL;
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
        if (recipeImagesURL.get(position).equals(""))
            viewHolder.recipeImageView.setVisibility(View.GONE);
        else {
            viewHolder.recipeImageView.setVisibility(View.VISIBLE);
            try {
                Picasso.with(context).load(Uri.parse((String) recipeImagesURL.get(position))).into(viewHolder.recipeImageView);
            } catch (Exception ex) {
                Toast.makeText(context, "Error Loading the Image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public int getItemCount() {
        return recipesNames.size();
    }

    public class RecipeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_title_tv)
        TextView recipeCardTextView;
        @BindView(R.id.recipe_image)
        ImageView recipeImageView;

        public RecipeListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
