package com.example.hope.bakingapp.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hope.bakingapp.R;
import com.example.hope.bakingapp.ui.RecipeStepFragment;
import com.example.hope.bakingapp.utilities.SelectedRecipeData;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hope on 8/9/2017.
 */

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepViewHolder> {

    private ListItemClickListner mListItemClickListner;
    private Context c;
    public SelectedRecipeData mSelectedRecipeData;
    public SimpleExoPlayer mExoPlayer;
    public RecipeStepViewHolder viewHolder;
    private RecipeStepFragment.Callback mCallback;

    public RecipeStepAdapter(Context c, SelectedRecipeData selectedRecipeData, ListItemClickListner listItemClickListner) {
        this.c = c;
        this.mListItemClickListner = listItemClickListner;
        this.mSelectedRecipeData = selectedRecipeData;
    }

    @Override
    public RecipeStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(R.layout.recipe_step_recyclerview_item, parent, shouldAttachToParentImmediately);
        RecipeStepViewHolder viewHolder = new RecipeStepViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeStepViewHolder viewHolder, int position) {
        this.viewHolder = viewHolder;
        viewHolder.instructionsTextView.setText((String) mSelectedRecipeData.stepsInstructions.get(mSelectedRecipeData.itemIndex));

        if (mSelectedRecipeData.stepImages.get(position).equals("")) {
            viewHolder.imageView.setVisibility(View.GONE);
        } else {
            viewHolder.imageView.setVisibility(View.VISIBLE);
            try {
                Picasso.with(c).load((String) mSelectedRecipeData.stepImages.get(position)).into(viewHolder.imageView);
            }catch (Exception ex){
                Toast.makeText(c, "Error Loading the Image", Toast.LENGTH_SHORT).show();
            }
        }

        if (mSelectedRecipeData.stepsVideos.get(mSelectedRecipeData.itemIndex).equals("")) {
            viewHolder.mExoPlayerView.setVisibility(View.GONE);
        }

        setBtnsVisability(viewHolder, mSelectedRecipeData.itemIndex);
        if (!isTablet(c)) {
            viewHolder.nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedRecipeData.itemIndex++;
                    mExoPlayer.stop();
                    mExoPlayer.release();
                    mCallback = (RecipeStepFragment.Callback) c;
                    mCallback.fragmentCallback(mSelectedRecipeData.itemIndex);

                }
            });

            viewHolder.previousBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedRecipeData.itemIndex--;
                    mExoPlayer.stop();
                    mExoPlayer.release();
                    mCallback = (RecipeStepFragment.Callback) c;
                    mCallback.fragmentCallback(mSelectedRecipeData.itemIndex);
                }
            });
        }
        Uri uri = Uri.parse((String) mSelectedRecipeData.stepsVideos.get(mSelectedRecipeData.itemIndex));
        intializeExoplayer(viewHolder, uri);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class RecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.instructions_tv)
        TextView instructionsTextView;
        @BindView(R.id.next_step_button)
        Button nextBtn;
        @BindView(R.id.previous_step_button)
        Button previousBtn;
        @BindView(R.id.exoplayer_view)
        SimpleExoPlayerView mExoPlayerView;
        @BindView(R.id.recipe_step_imageview)
        ImageView imageView;

        public RecipeStepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedItemPosition = getAdapterPosition();
            mListItemClickListner.onItemClicked(clickedItemPosition);
        }
    }

    public interface ListItemClickListner {
        void onItemClicked(int itemIndex);
    }

    private void setBtnsVisability(RecipeStepViewHolder viewHolder, int itemIndex) {
        if (isTablet(c)) {
            viewHolder.previousBtn.setVisibility(View.GONE);
            viewHolder.nextBtn.setVisibility(View.GONE);
        } else if (itemIndex == 0) {
            viewHolder.previousBtn.setVisibility(View.GONE);
            viewHolder.nextBtn.setVisibility(View.VISIBLE);
        } else if (itemIndex == mSelectedRecipeData.stepsInstructions.size() - 1) {
            viewHolder.nextBtn.setVisibility(View.GONE);
            viewHolder.previousBtn.setVisibility(View.VISIBLE);
        } else {
            viewHolder.nextBtn.setVisibility(View.VISIBLE);
            viewHolder.previousBtn.setVisibility(View.VISIBLE);
        }
    }

    private void intializeExoplayer(RecipeStepViewHolder viewHolder, Uri videoUri) {
        if (mExoPlayer == null) {
            // Create an instance of the exoplayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(c, trackSelector, loadControl);
            viewHolder.mExoPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource
            String userAgent = Util.getUserAgent(c, c.getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(videoUri
                    , new DefaultDataSourceFactory(c, userAgent)
                    , new DefaultExtractorsFactory()
                    , null
                    , null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    public static boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.two_pane);
    }
}
