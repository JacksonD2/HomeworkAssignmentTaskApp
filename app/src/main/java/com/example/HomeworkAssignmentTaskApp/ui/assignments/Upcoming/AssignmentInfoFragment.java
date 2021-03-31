package com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
        currentAssignment = getArguments().getInt("position");
        AssignmentData assignmentInfo = appModel.getAssignmentList().getValue().get(currentAssignment);
        String temp = null;
        //Assignment Name
        TextView assignmentName = root.findViewById(R.id.textViewAssignmentName);
        assignmentName.setText(assignmentInfo.getAssignmentName());
        //Class Name
        TextView className = root.findViewById(R.id.textViewAssignmentClassName);
        //assignmentInfo.getClassId()
        className.setText("still working on it");
        //Priority
        TextView priority = root.findViewById(R.id.textViewAssignmentPriority);
        int priorityNum = assignmentInfo.getPriority();
        setPriority(priority, priorityNum);

        //Due Date
        TextView dueDate = root.findViewById(R.id.textViewAssignmentDueDate);
        Date date = assignmentInfo.getDueDate();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        if(date!=null) {
            dueDate.setText(dateFormat.format(date));
        }
        else {
            dueDate.setText("");
        }
        //Description
        TextView description = root.findViewById(R.id.textViewAssignmentDescription);
        temp = assignmentInfo.getDescription();
        if(temp!=null){
            description.setText(temp);
        }

        //Complete Assignment Button
        buttonCompleteAssignment = root.findViewById(R.id.buttonCompleteAssignment);


        return root;
    }

    void setPriority(TextView p, int pNum){
        String[] priorities = getResources().getStringArray(R.array.priorities);
        if(pNum>=0 && pNum<priorities.length){
            p.setText(priorities[pNum]);
        }
        else {
            p.setText(getResources().getString(R.string.data_error));
        }
    }
}