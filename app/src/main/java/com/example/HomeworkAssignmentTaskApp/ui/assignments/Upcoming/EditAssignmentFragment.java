package com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.HomeworkAssignmentTaskApp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

public class EditAssignmentFragment extends AddAssignmentFragment{

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = setUpViews(inflater, container);

        buttonDeleteAssignment = root.findViewById(R.id.buttonDeleteAssignment);
        buttonDeleteAssignment.setOnClickListener(view -> {

        });

        //fab
        FloatingActionButton fab = root.findViewById(R.id.fabAddAssignment);
        fab.setOnClickListener(this::finishAddingAssignment);

        return root;
    }
}
