package com.example.HomeworkAssignmentTaskApp.ui.assignments.Classes;

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
import com.example.HomeworkAssignmentTaskApp.data.ClassData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class EditClassFragment extends AddClassFragment {
    private int classId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View root = inflater.inflate(R.layout.fragment_add_class, container, false);
        View root = setUpViews(inflater, container);

        //class data
        ClassData classInfo;
        if(getArguments()!=null) {
            classId = getArguments().getInt(FormattingHelper.CLASS_ID);
            classInfo = appModel.getClassData(classId);
        }
        else {
            classInfo = new ClassData();
            classId = classInfo.getClassId();
        }
        String temp;

        //class name
        EditText className = root.findViewById(R.id.editTextClassName);
        className.setText(classInfo.getClassName());
        //instructor
        EditText instructorName = root.findViewById(R.id.editTextInstructorName);
        temp = classInfo.getInstructorName();
        if(temp!=null) {
            instructorName.setText(temp);
        }
        //dates
        Date date = classInfo.getStartDate();
        if(date!=null) {
            temp = FormattingHelper.setDateFormat.format(date);
            buttonStartDate.setText(temp);
        }
        date = classInfo.getEndDate();
        if(date!=null) {
            temp = FormattingHelper.setDateFormat.format(date);
            buttonEndDate.setText(temp);
        }

        //color
        if((classColor = classInfo.getClassColor())!=0){
            buttonColor.setBackgroundColor(classColor);
            buttonColor.setText(R.string.blank);
        }

        //fab
        FloatingActionButton fab = root.findViewById(R.id.fabAddClass);
        fab.setOnClickListener(view -> {
            if(finishAddingClass(view, classInfo)!=null) {
                appModel.updateClass(classInfo);
                Navigation.findNavController(view).navigateUp();
            }
        });

        //delete class
        buttonDeleteClass = root.findViewById(R.id.buttonDeleteClass);
        buttonDeleteClass.setOnClickListener(v -> deleteClass());

        System.out.println("Reached Here!1");

        appModel.getDeletedClassId().observe(getViewLifecycleOwner(), integer -> {
            if(integer==classId) {
                System.out.println("Reached Here!99");
                appModel.deleteClassById();
                //appModel.getDeletedClassId().removeObservers(getViewLifecycleOwner());
                //Navigation.findNavController(requireView()).navigate(R.id.nav_assignments, getArguments());
                appModel.assignmentListModified.setValue(null);
                Navigation.findNavController(requireView()).navigateUp();
                Navigation.findNavController(requireView()).navigateUp();
            }
        });
        System.out.println("Reached Here!2");

        return root;
    }

    private void deleteClass(){
        /*appModel.getClassList().getValue().remove(classId);
        Navigation.findNavController(view).navigate(R.id.nav_assignments);*/
        System.out.println("Reached Here!100");
        DialogFragment newFragment = new DeleteClassDialogFragment(classId);
        newFragment.show(requireActivity().getSupportFragmentManager(), "class_picker");
    }

    public static class DeleteClassDialogFragment extends DialogFragment {
        ApplicationViewModel viewModel;
        int classId;

        public DeleteClassDialogFragment(int classId){
            super();
            this.classId = classId;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            viewModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);
        }

        @NotNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.dialog_delete_class)
                    .setPositiveButton(R.string.yes, (dialog, id) -> {
                        // FIRE ZE MISSILES!
                        viewModel.setDeletedClassId(classId);
                        //viewModel.deleteClassById(classId);
                    })
                    .setNegativeButton(R.string.no, (dialog, id) -> {
                        // User cancelled the dialog
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}

/*
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View root = inflater.inflate(R.layout.fragment_add_class, container, false);
        View root = setUpViews(inflater, container);
        //ViewModel
        appModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);

        //class data
        currentClass = getArguments().getInt("position");
        ClassData classInfo = appModel.getClassList().getValue().get(currentClass);
        String temp;
        //colors
        /*spinner = (Spinner) root.findViewById(R.id.spinnerColors);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.color_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//class name
EditText className = root.findViewById(R.id.editTextClassName);
        className.setText(classInfo.getClassName());
                //instructor
                EditText instructorName = root.findViewById(R.id.editTextInstructorName);
                temp = classInfo.getInstructorName();
                if(temp!=null) {
                instructorName.setText(temp);
                }
                //dates
                Date date;
                //start date
                buttonStartDate = (Button) root.findViewById(R.id.buttonStartDate);
                buttonStartDate.setOnClickListener(new View.OnClickListener() {
public void onClick(View v) {
        showDatePickerDialog(buttonStartDate);
        }
        });
        date = classInfo.getStartDate();
        if(date!=null) {
        temp = setDateFormat.format(date);
        buttonStartDate.setText(temp);
        }
        //end date
        buttonEndDate = (Button) root.findViewById(R.id.buttonEndDate);
        buttonEndDate.setOnClickListener(new View.OnClickListener() {
public void onClick(View v) {
        showDatePickerDialog(buttonEndDate);
        }
        });
        date = classInfo.getEndDate();
        if(date!=null) {
        temp = dateFormat.format(date);
        buttonEndDate.setText(temp);
        }
        //delete class
        buttonDeleteClass = root.findViewById(R.id.buttonDeleteClass);
        buttonDeleteClass.setOnClickListener(new View.OnClickListener() {
public void onClick(View v) {
        deleteClass(v);
        }
        });

        //fab
        FloatingActionButton fab = root.findViewById(R.id.fabAddClass);
        fab.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        finishAddingClass(view);
        }
        });

        return root;
        }
 */

    /*@Override
    protected void finishAddingClass(View view){
        EditText editTextClassName = (EditText) requireActivity().findViewById(R.id.editTextClassName);
        String className = editTextClassName.getText().toString();

        closeKeyboard(view);
        if(className.equals("") || className.equals(" ")) {
            Toast.makeText(requireContext(), errorClassName, Toast.LENGTH_LONG).show();
        }
        else {
            ClassData classInfo = appModel.getClassList().getValue().get(currentClass);

            //optional information
            EditText editTextInstructorName = (EditText) requireActivity().findViewById(R.id.editTextInstructorName);
            String instructorName = editTextInstructorName.getText().toString();
            if(!(instructorName.equals("") || instructorName.equals(" "))){
                classInfo.setInstructorName(instructorName);
            }
            if(startDate!=null){
                classInfo.setStartDate(startDate);
            }
            if(endDate!=null){
                classInfo.setEndDate(endDate);
            }

            appModel.updateClass(classInfo);
            Navigation.findNavController(view).navigateUp();
        }
    }*/
