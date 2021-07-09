package com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.FormattingHelper;
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class EditAssignmentFragment extends AddAssignmentFragment{
    private long assignmentId;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = setUpViews(inflater, container);

        //class data
        final AssignmentData assignmentInfo;
        if(getArguments()!=null) {
            assignmentId = getArguments().getLong(FormattingHelper.ASSIGNMENT_ID);
            AssignmentData temp = appModel.getAssignmentData(assignmentId);
            if(temp==null){
                assignmentInfo = new AssignmentData();
                assignmentId = assignmentInfo.getAssignmentId();
            }
            else assignmentInfo = temp;
        }
        else {
            assignmentInfo = new AssignmentData();
            assignmentId = assignmentInfo.getAssignmentId();
        }

        EditText editTextAssignmentName = root.findViewById(R.id.editTextAssignmentName);
        editTextAssignmentName.setText(assignmentInfo.getAssignmentName());

        ClassData classData = appModel.getClassData(assignmentInfo.getClassId());
        if(classData!=null) {
            classId = assignmentInfo.getClassId();
            buttonClassName.setText(classData.getClassName());
            FormattingHelper.setPriority(buttonPriority, assignmentInfo.getPriority());
        }

        if((dueDate = assignmentInfo.getDueDate())!=null) {
            buttonDueDate.setText(FormattingHelper.setDateFormat.format(dueDate));
            buttonDueTime.setText(FormattingHelper.timeFormat.format(dueDate));
            Calendar c = Calendar.getInstance();
            c.setTime(dueDate);
            dueHourOfDay = c.get(Calendar.HOUR_OF_DAY);
            dueMinute = c.get(Calendar.MINUTE);
            isTimeSet = true;
        }

        FormattingHelper.setPriority(buttonPriority, assignmentInfo.getPriority());

        EditText editTextAssignmentDescription = root.findViewById(R.id.editTextAssignmentDescription);
        editTextAssignmentDescription.setText(assignmentInfo.getDescription());

        buttonDeleteAssignment = root.findViewById(R.id.buttonDeleteAssignment);
        buttonDeleteAssignment.setOnClickListener(v->deleteAssignment());

        //fab
        FloatingActionButton fab = root.findViewById(R.id.fabAddAssignment);
        fab.setOnClickListener(view -> {
            AssignmentData assignmentData = finishAddingAssignment(view, assignmentInfo);
            if(assignmentData!=null){
                appModel.updateAssignment(assignmentData);
                Navigation.findNavController(view).navigateUp();
            }
        });

        appModel.getDeletedAssignmentId().observe(getViewLifecycleOwner(), integer -> {
            if(integer==assignmentId) {
                appModel.deleteAssignmentById();
                Navigation.findNavController(requireView()).navigateUp();
                Navigation.findNavController(requireView()).navigateUp();
            }
        });

        return root;
    }

    private void deleteAssignment(){
        DialogFragment newFragment = new DeleteAssignmentDialogFragment(assignmentId);
        newFragment.show(requireActivity().getSupportFragmentManager(), "class_picker");
    }

    public static class DeleteAssignmentDialogFragment extends DialogFragment {
        ApplicationViewModel viewModel;
        long assignmentId;

        public DeleteAssignmentDialogFragment(long assignmentId){
            super();
            this.assignmentId = assignmentId;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            viewModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);
        }

        @NotNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage(R.string.dialog_delete_assignment)
                    .setPositiveButton(R.string.yes, (dialog, id) -> {
                        viewModel.setAssignmentDeleted(true);
                        viewModel.setAssignmentInserted(false);
                        viewModel.setDeletedAssignmentId(assignmentId);
                    })
                    .setNegativeButton(R.string.no, (dialog, id) -> {
                        // User cancelled the dialog
                    });

            return builder.create();
        }
    }
}
