package com.example.HomeworkAssignmentTaskApp.ui.assignments.Classes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.AssignmentsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ClassInfoFragment extends Fragment {

    int classId, classTab = 1;

    public ClassInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_class_info, container, false);
        //ViewModel
        ApplicationViewModel appModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);

        //class info
        if (getArguments() != null) {
            classId = getArguments().getInt(ClassesFragment.CLASS_ID);

            ClassData classInfo = appModel.getClassData(classId);
            String temp;
            //class name
            TextView className = root.findViewById(R.id.textViewClassName);
            temp = classInfo.getClassName();
            className.setText(temp);
            //instructor
            TextView instructorName = root.findViewById(R.id.textViewInstructorName);
            temp = classInfo.getInstructorName();
            if (temp != null) {
                instructorName.setText(temp);
            }
            //start date
            TextView startDate = root.findViewById(R.id.textViewStartDate);
            Date date = classInfo.getStartDate();
            if (date != null) {
                temp = ApplicationViewModel.setDateFormat.format(date);
                startDate.setText(temp);
            }
            //end date
            TextView endDate = root.findViewById(R.id.textViewEndDate);
            date = classInfo.getEndDate();
            if (date != null) {
                temp = ApplicationViewModel.setDateFormat.format(date);
                endDate.setText(temp);
            }

            //fab
            FloatingActionButton fab = root.findViewById(R.id.fabEditClass);
            fab.setOnClickListener(view -> {
                Bundle bundle;

                if (getArguments() != null) {
                    bundle = getArguments();
                } else {
                    bundle = new Bundle();
                }
                bundle.putInt(ClassesFragment.CLASS_ID, classId);

                Navigation.findNavController(root).navigate(R.id.action_edit_class, bundle);
                //Navigation.findNavController(root).navigate(R.id.action_exit_add_class);
            });

            //custom back button
       /* OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Bundle bundle = new Bundle();
                bundle.putInt(tab, classTab);
                Navigation.findNavController(root).navigate(R.id.action_exit_class_info, bundle);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this.getViewLifecycleOwner(), callback);*/
        }

        return root;
    }
}