package com.example.hope.bakingapp.ui;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.hope.bakingapp.R;
import com.example.hope.bakingapp.utilities.SelectedRecipeData;
import com.example.hope.bakingapp.utilities.SelectedStepData;
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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeStepFragment extends Fragment implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
    private static final String TAG = RecipeStepFragment.class.getSimpleName();

    View rootView;
    TextView instructionsTextView;
    private Button previousBtn, nextBtn;
    private SelectedStepData mSelectedStepData;
    private SelectedRecipeData mSelectedRecipeData;
    private int itemIndex;

    private MediaPlayer mediaPlayer;
    private SurfaceHolder vidHolder;
    private SurfaceView vidSurface;

    @Override
    public void onStart() {
        super.onStart();
        Bundle b = getArguments();
        if (b != null) {
            mSelectedRecipeData = b.getParcelable(getString(R.string.recipe_intent_extra));
            if (getActivity().getResources().getConfiguration().orientation != ORIENTATION_LANDSCAPE) {

                itemIndex = mSelectedRecipeData.itemIndex;
                setBtnsVisability();
                instructionsTextView.setText((String) mSelectedRecipeData.stepsInstruactions.get(itemIndex));

                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemIndex += 1;
                        instructionsTextView.setText((String) mSelectedRecipeData.stepsInstruactions.get(itemIndex));
                        intializePlayer();
                        setBtnsVisability();
                        Log.v(TAG, Integer.toString(itemIndex));
                    }
                });
                previousBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemIndex -= 1;
                        instructionsTextView.setText((String) mSelectedRecipeData.stepsInstruactions.get(itemIndex));
                        intializePlayer();
                        setBtnsVisability();
                        Log.v(TAG, Integer.toString(itemIndex));
                    }
                });
            }
        }
    }

    private void setBtnsVisability(){
        if (itemIndex == 0) {
            previousBtn.setVisibility(View.INVISIBLE);
            nextBtn.setVisibility(View.VISIBLE);
        } else if (itemIndex == mSelectedRecipeData.stepsInstruactions.size() - 1) {
            nextBtn.setVisibility(View.INVISIBLE);
            previousBtn.setVisibility(View.VISIBLE);
        }else{
            nextBtn.setVisibility(View.VISIBLE);
            previousBtn.setVisibility(View.VISIBLE);
        }
    }

    public RecipeStepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        if (getActivity().getResources().getConfiguration().orientation != ORIENTATION_LANDSCAPE) {
            instructionsTextView = (TextView) rootView.findViewById(R.id.instructions_tv);
            previousBtn = (Button) rootView.findViewById(R.id.previous_step_button);
            nextBtn = (Button) rootView.findViewById(R.id.next_step_button);
        }
        vidSurface = (SurfaceView) rootView.findViewById(R.id.surface_view);
        vidHolder = vidSurface.getHolder();
        vidHolder.addCallback(this);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
//        mediaPlayer.release();
//        mediaPlayer = null;
    }

    @Override
    public void onPause() {
        super.onPause();
//        mediaPlayer.stop();
//        mediaPlayer.release();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        intializePlayer();
    }

    private void intializePlayer(){
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDisplay(vidHolder);
            mediaPlayer.setDataSource((String) mSelectedRecipeData.stepsVideos.get(itemIndex));
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }

    public interface Callback {
        void fragmentCallback(int itemIndex);
    }
}
