package com.example.HomeworkAssignmentTaskApp.ui.assignments.Completed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.HomeworkAssignmentTaskApp.R;

public class CompletedFragment extends Fragment {


    public CompletedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_completed, container, false);

        return root;
    }
}