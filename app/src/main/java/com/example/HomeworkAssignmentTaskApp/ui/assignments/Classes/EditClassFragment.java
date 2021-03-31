package com.example.HomeworkAssignmentTaskApp.ui.assignments.Classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditClassFragment extends AddClassFragment {

    int currentClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_class, container, false);
        //ViewModel
        appModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);

        //class data
        currentClass = getArguments().getInt("position");
        ClassData classInfo = appModel.getClassList().getValue().get(currentClass);
        String temp;
        //colors
        spinner = (Spinner) root.findViewById(R.id.spinnerColors);
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
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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
            temp = dateFormat.format(date);
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

    @Override
    protected void finishAddingClass(View view){
        EditText editTextClassName = (EditText) requireActivity().findViewById(R.id.editTextClassName);
        String className = editTextClassName.getText().toString();

        closeKeyboard(view);
        System.out.println(className);

        if(!(className.equals("") || className.equals(" "))) {
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
    }
    private void deleteClass(View view){
        appModel.getClassList().getValue().remove(currentClass);
        Navigation.findNavController(view).navigate(R.id.nav_assignments);
    }
}
