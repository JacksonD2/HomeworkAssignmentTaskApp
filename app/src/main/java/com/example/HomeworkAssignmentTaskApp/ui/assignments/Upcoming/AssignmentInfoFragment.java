package com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.FormattingHelper;
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.ListHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class AssignmentInfoFragment extends Fragment {

    protected long currentAssignment;
    protected Button buttonCompleteAssignment;

    public AssignmentInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_assignment_info, container, false);

        //ViewModel
        ApplicationViewModel appModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);

        //Assignment Info
        //currentAssignment = getArguments().getInt("position");
        if (getArguments() != null) {
            currentAssignment = getArguments().getLong(FormattingHelper.ASSIGNMENT_ID);

            AssignmentData assignmentInfo = appModel.getAssignmentData(currentAssignment);
            if (assignmentInfo == null) assignmentInfo = new AssignmentData("error");
            String temp;

            //Assignment Name
            TextView assignmentName = root.findViewById(R.id.textViewAssignmentName);
            assignmentName.setText(assignmentInfo.getAssignmentName());
            //Class Name
            int classId = assignmentInfo.getClassId();
            ClassData classData = appModel.getClassData(classId);
            if (classData!=null) {
                TextView className = root.findViewById(R.id.textViewAssignmentClassName);
                className.setText(classData.getClassName());
            }
            //Priority
            TextView priority = root.findViewById(R.id.textViewAssignmentPriority);
            int priorityNum = assignmentInfo.getPriority();
            FormattingHelper.setPriority(priority, priorityNum);

            //Due Date/Time
            TextView dueDate = root.findViewById(R.id.textViewAssignmentDueDate),
                    dueTime = root.findViewById(R.id.textViewAssignmentDueTime);
            Date date = assignmentInfo.getDueDate();
            if (date != null) {
                dueDate.setText(FormattingHelper.setDateFormat.format(date));
                dueTime.setText(FormattingHelper.timeFormat.format(date));
            } else {
                dueDate.setText("");
                dueTime.setText("");
            }
            //Description
            TextView description = root.findViewById(R.id.textViewAssignmentDescription);
            temp = assignmentInfo.getDescription();
            if (temp != null) {
                description.setText(temp);
            }

            //Complete Assignment Button
            buttonCompleteAssignment = root.findViewById(R.id.buttonCompleteAssignment);
            AssignmentData finalAssignmentInfo = assignmentInfo;
            if (assignmentInfo.isComplete())
                buttonCompleteAssignment.setVisibility(View.GONE);
            else {
                buttonCompleteAssignment.setOnClickListener(view -> {
                    finalAssignmentInfo.setComplete(true);
                    appModel.updateAssignment(finalAssignmentInfo);
                    ListHelper.removeAssignment(finalAssignmentInfo, appModel.sIncompleteAssignmentList);
                    ListHelper.sortedInsert(finalAssignmentInfo, appModel.sCompleteAssignmentList);
                    appModel.assignmentListModified.setValue(null);
                    Navigation.findNavController(view).navigateUp();
                });
            }


            FloatingActionButton fabEditAssignment = root.findViewById(R.id.fabEditAssignment);
            fabEditAssignment.setOnClickListener(view -> {
                Bundle bundle;

                if (getArguments() != null) bundle = getArguments();
                else bundle = new Bundle();
                bundle.putLong(FormattingHelper.ASSIGNMENT_ID, currentAssignment);

                Navigation.findNavController(root).navigate(R.id.action_edit_assignment, bundle);
            });
        }


        return root;
    }
}