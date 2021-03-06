/*
used some sample methods from
https://github.com/googlecodelabs/exoplayer-intro/blob/master/exoplayer-codelab-01/player-lib/src/main/java/com/example/exoplayer/PlayerActivity.java
 */

package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.bakingapp.data.Steps;
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
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.Arrays;


public class StepsFragment extends Fragment {

    private SimpleExoPlayer player;
    private PlayerView playerView;
    private Context context;
    private String videoURL;
    private Button previousButton;
    private Button nextButton;

    private long playbackPosition;
    private static String PLAYBACK_POSITION = "playbackpositiongextra";
    private int currentWindow;
    private static String CURRENT_WINDOW = "currentwindowextra";
    private boolean playWhenReady = true;
    private static String PLAY_WHEN_READY = "playwhenreadyextra";
    private Steps[] steps;
    private int numberSteps;
    private int currentStep;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();
        Steps step = getArguments().getParcelable("step");
        steps = getArguments().getParcelableArrayList("steps").toArray(new Steps[0]);
        numberSteps = getArguments().getInt("numberSteps");
        currentStep = getArguments().getInt("currentStep");
        videoURL = step.getVideoURL();

        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW,0);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
        }


        FragmentStepsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_steps, container, false);

        binding.setStep(step);
        View view = binding.getRoot();

        if(getActivity().findViewById(R.id.previousButton) != null) {
            previousButton = getActivity().findViewById(R.id.previousButton);
            nextButton = getActivity().findViewById(R.id.nextButton);
            setButtonVisibility();
            if (currentStep != 0) {
                addListenerPreviousButton();
            }
            if (currentStep != numberSteps-1) {
                addListenerNextButton();
            }
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        playerView = getView().findViewById(R.id.video_view);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
        }
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        if(TextUtils.isEmpty(videoURL)){
            playerView.setVisibility(View.GONE);
        }
        else {
            playerView.setVisibility(View.VISIBLE);
            if (player == null) {
                player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(context),
                        new DefaultTrackSelector(), new DefaultLoadControl());
            }
            playerView.setPlayer(player);
            player.seekTo(currentWindow, playbackPosition);
            player.setPlayWhenReady(playWhenReady);
            MediaSource mediaSource = buildMediaSource(Uri.parse(videoURL));
            player.prepare(mediaSource, false, false);
        }
    }

    private void releasePlayer() {
        if (player != null) {

            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab"))
                .createMediaSource(uri);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYBACK_POSITION, playbackPosition);
        outState.putInt(CURRENT_WINDOW, currentWindow);
        outState.putBoolean(PLAY_WHEN_READY, playWhenReady);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void addListenerPreviousButton() {
        previousButton.setOnClickListener(new View.OnClickListener() {
            int stepNumber = currentStep-1;
            Steps laststep = steps[stepNumber];
            @Override
            public void onClick(View view) {
                goToStep(laststep, stepNumber);
            }
        });

    }

    public void addListenerNextButton() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            int stepNumber = currentStep+1;
            Steps nextstep = steps[stepNumber];

            @Override
            public void onClick(View view) {
                goToStep(nextstep, stepNumber);
            }
        });
    }

    public void setButtonVisibility() {
        if (currentStep == 0) {
            previousButton.setVisibility(View.GONE);
        }
        if (currentStep == numberSteps-1) {
            nextButton.setVisibility(View.GONE);
        }
    }

    public void goToStep(Steps step, int stepNumber) {

        ArrayList<Steps> arraySteps = new ArrayList<Steps>(Arrays.asList(steps));

        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);
        bundle.putParcelableArrayList("steps", arraySteps);
        bundle.putInt("numberSteps", numberSteps);
        bundle.putInt("currentStep", stepNumber);

        Intent intent = new Intent(getActivity(), StepsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}

