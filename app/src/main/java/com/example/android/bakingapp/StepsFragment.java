package com.example.android.bakingapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.databinding.FragmentStepsBinding;

public class StepsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Steps step = getArguments().getParcelable("step");

        FragmentStepsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_steps, container, false);

        binding.setStep(step);
        View view = binding.getRoot();

        return view;
    }
}
