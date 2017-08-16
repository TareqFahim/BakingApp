package com.example.hope.bakingapp.ui;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hope.bakingapp.R;
import com.example.hope.bakingapp.adapters.RecipeStepAdapter;
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
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

/**
 * A placeholder fragment containing a mSimple view.
 */
public class RecipeStepFragment extends Fragment implements PlaybackControlView.VisibilityListener, RecipeStepAdapter.ListItemClickListner {
    private static final String TAG = RecipeStepFragment.class.getSimpleName();

    View rootView;
    private SelectedRecipeData mSelectedRecipeData;
    private RecipeStepAdapter mRecipeStepAdapter;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mExoPlayerView;

    @BindView(R.id.recipe_step_rv) RecyclerView mRecyclerView;

    @Override
    public void onStart() {
        super.onStart();
        Bundle b = getArguments();
        if (b != null) {
            mSelectedRecipeData = b.getParcelable(getString(R.string.recipe_intent_extra));
            if (getActivity().getResources().getConfiguration().orientation != ORIENTATION_LANDSCAPE) {
                mRecipeStepAdapter = new RecipeStepAdapter(getActivity(), mSelectedRecipeData, this);
                mRecyclerView.setAdapter(mRecipeStepAdapter);
            } else {
                Uri uri = Uri.parse((String) mSelectedRecipeData.stepsVideos.get(mSelectedRecipeData.itemIndex));
                intializeExoplayer(uri);
            }
        }
    }

    public RecipeStepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        if (getActivity().getResources().getConfiguration().orientation != ORIENTATION_LANDSCAPE) {
            ButterKnife.bind(this, rootView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);
        } else {
            // Exoplayer Implementation
            mExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.exoplayer_view);
            mExoPlayerView.setControllerVisibilityListener(this);
            mExoPlayerView.requestFocus();
        }

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().getResources().getConfiguration().orientation != ORIENTATION_LANDSCAPE && mRecipeStepAdapter != null && mRecipeStepAdapter.mExoPlayer != null) {
            mRecipeStepAdapter.mExoPlayer.stop();
            mRecipeStepAdapter.mExoPlayer.release();
            mRecipeStepAdapter.mExoPlayer = null;
        } else if(mExoPlayer != null){
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity().getResources().getConfiguration().orientation != ORIENTATION_LANDSCAPE && mRecipeStepAdapter != null && mRecipeStepAdapter.mExoPlayer != null) {
            mRecipeStepAdapter.mExoPlayer.stop();
            mRecipeStepAdapter.mExoPlayer.release();
            mRecipeStepAdapter.mExoPlayer = null;
        } else if(mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onVisibilityChange(int visibility) {

    }

    public void intializeExoplayer(Uri videoUri) {
        if (mExoPlayer == null) {
            // Create an instance of the exoplayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mExoPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource
            String userAgent = Util.getUserAgent(getActivity(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(videoUri
                    , new DefaultDataSourceFactory(getActivity(), userAgent)
                    , new DefaultExtractorsFactory()
                    , null
                    , null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onItemClicked(final int itemIndex) {
    }

    public interface Callback {
        void fragmentCallback(int itemIndex);
    }
}
