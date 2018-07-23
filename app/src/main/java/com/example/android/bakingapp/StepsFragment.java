/*
used some sample methods from
https://github.com/googlecodelabs/exoplayer-intro/blob/master/exoplayer-codelab-01/player-lib/src/main/java/com/example/exoplayer/PlayerActivity.java
 */

package com.example.android.bakingapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.databinding.FragmentStepsBinding;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;


public class StepsFragment extends Fragment {

    private SimpleExoPlayer player;
    private PlayerView playerView;
    private Context context;
    private String videoURL;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();
        Steps step = getArguments().getParcelable("step");
        videoURL = step.getVideoURL();


        FragmentStepsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_steps, container, false);

        binding.setStep(step);
        View view = binding.getRoot();



        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        playerView = getView().findViewById(R.id.video_view);
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player == null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void initializePlayer() {
        if(TextUtils.isEmpty(videoURL)){
            playerView.setVisibility(View.GONE);
        }
        else {
            playerView.setVisibility(View.VISIBLE);
            Log.i("videoURL", "this" + videoURL);
            if (player == null) {
                player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(context),
                        new DefaultTrackSelector(), new DefaultLoadControl());
                playerView.setPlayer(player);
                player.setPlayWhenReady(playWhenReady);
                player.seekTo(currentWindow, playbackPosition);
            }
            MediaSource mediaSource = buildMediaSource(Uri.parse(videoURL));
            player.prepare(mediaSource, true, false);
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab"))
                .createMediaSource(uri);
    }


}

