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
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AssignmentInfoFragment extends Fragment {

    protected int currentAssignment;
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
            currentAssignment = getArguments().getInt(UpcomingFragment.ASSIGNMENT_ID);

        /*AssignmentData assignmentInfo = null;
        for(AssignmentData assignmentData: appModel.getAssignmentList().getValue()){
            if(assignmentData.getAssignmentId()==currentAssignment){
                assignmentInfo = assignmentData;
                break;
            }
        }*/
            AssignmentData assignmentInfo = appModel.getAssignmentData(currentAssignment);
            if (assignmentInfo == null) {
                assignmentInfo = new AssignmentData("error");
            }
            String temp;
            //Assignment Name
            TextView assignmentName = root.findViewById(R.id.textViewAssignmentName);
            assignmentName.setText(assignmentInfo.getAssignmentName());
            //Class Name
            int classId = assignmentInfo.getClassId();
            if (classId != -1) {
                TextView className = root.findViewById(R.id.textViewAssignmentClassName);
                className.setText(appModel.getClassData(classId).getClassName());
            }
            //Priority
            TextView priority = root.findViewById(R.id.textViewAssignmentPriority);
            int priorityNum = assignmentInfo.getPriority();
            setPriority(priority, priorityNum);

            //Due Date
            TextView dueDate = root.findViewById(R.id.textViewAssignmentDueDate);
            Date date = assignmentInfo.getDueDate();
            if (date != null) {
                dueDate.setText(ApplicationViewModel.setDateFormat.format(date));
            } else {
                dueDate.setText("");
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
            if (assignmentInfo.isComplete()) {
                buttonCompleteAssignment.setVisibility(View.GONE);
            } else {
                buttonCompleteAssignment.setOnClickListener(view -> {
                    finalAssignmentInfo.setComplete(true);
                    appModel.updateAssignment(finalAssignmentInfo);
                    Navigation.findNavController(view).navigateUp();
                });
            }
        }


        return root;
    }

    public static void setPriority(TextView p, int pNum){
        String[] priorities = p.getResources().getStringArray(R.array.priorities);
        if(pNum>=0 && pNum<priorities.length){
            p.setText(priorities[pNum]);
        }
        else {
            p.setText(p.getResources().getString(R.string.data_error));
        }
    }
}