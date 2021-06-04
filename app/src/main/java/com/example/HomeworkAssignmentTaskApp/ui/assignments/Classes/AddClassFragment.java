package com.example.HomeworkAssignmentTaskApp.ui.assignments.Classes;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;
import com.example.HomeworkAssignmentTaskApp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//fix when user clicks on back button
public class AddClassFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    //ApplicationViewModel appModel;
    protected ApplicationViewModel appModel;
    protected Spinner spinner;
    protected Button buttonStartDate, buttonEndDate, buttonColor, buttonDeleteClass;
    protected Date startDate, endDate;
    protected final String errorClassName = "Class name must not be blank!";
    protected final DateFormat dateFormat = ApplicationViewModel.setDateFormat;
    //String tab = "tab";
    //int classTab = 1;
    //String startDate = "startDate", endDate = "endDate";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = setUpViews(inflater, container);

        //fab
        FloatingActionButton fab = root.findViewById(R.id.fabAddClass);
        fab.setOnClickListener(view -> {
            ClassData classInfo = finishAddingClass(view, null);
            if(classInfo!=null) {
                appModel.insertClass(classInfo);
                Navigation.findNavController(view).navigateUp();
            }
        });

        //delete class
        buttonDeleteClass = root.findViewById(R.id.buttonDeleteClass);
        buttonDeleteClass.setVisibility(View.GONE);

        return root;
    }

    protected View setUpViews(LayoutInflater inflater, ViewGroup container){
        View root =  inflater.inflate(R.layout.fragment_add_class, container, false);

        //ViewModel
        appModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);

        //buttons
        buttonColor = root.findViewById(R.id.buttonColor);
        buttonColor.setOnClickListener(v -> {

        });
        //dates
        buttonStartDate = root.findViewById(R.id.buttonStartDate);
        buttonStartDate.setOnClickListener(v -> showDatePickerDialog(buttonStartDate));
        buttonEndDate = root.findViewById(R.id.buttonEndDate);
        buttonEndDate.setOnClickListener(v -> showDatePickerDialog(buttonEndDate));

        return root;
    }

    protected ClassData finishAddingClass(View view, ClassData classInfo){
        EditText editTextClassName = requireActivity().findViewById(R.id.editTextClassName);
        String className = editTextClassName.getText().toString();

        if(className.equals("") || className.equals(" ")) {
            Toast.makeText(requireContext(), errorClassName, Toast.LENGTH_LONG).show();
            return null;
        }
        else {
            closeKeyboard(view);
            if(classInfo==null) {
                classInfo = new ClassData(className);
            }

            //optional information
            EditText editTextInstructorName = requireActivity().findViewById(R.id.editTextInstructorName);
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

            return classInfo;
        }
    }

    protected void closeKeyboard(View view) {
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) requireActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showDatePickerDialog(Button button) {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, c.get(Calendar.YEAR),
                c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setTag(button);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar c = Calendar.getInstance();
        c.set(i, i1, i2);
        Date dateSet = c.getTime();
        String text = ApplicationViewModel.setDateFormat.format(dateSet);
        if(datePicker.getTag().equals(buttonStartDate)) {
            buttonStartDate.setText(text);
            startDate = dateSet;
        }
        else if(datePicker.getTag().equals(buttonEndDate)){
            buttonEndDate.setText(text);
            endDate = dateSet;
        }
    }

}


    /*public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            /*Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);*/
            /*if(getTag().equals("start_date")){
                Button buttonStartDate = (Button) view.findViewById(R.id.buttonStartDate);
                String text = String.format("%d/%d/%d", month, day, year);
                buttonStartDate.setText(text);
            }
       }
    }*/

    /*public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getParentFragmentManager(), "timePicker");
    }

    class SpinnerListener implements AdapterView.OnItemSelectedListener {

        public SpinnerListener(){

        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }*/

        /*//spinner
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
        });*/


//custom back button
        /*OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                //NavBackStackEntry navBackStackEntry = Navigation.findNavController(root).getBackStackEntry(R.id.nav_classes);
                //Navigation.findNavController(root).navigateUp();
                //Navigation.findNavController(root).navigate(R.id.nav_assignments);
                //Navigation.findNavController(root).navigate(R.id.nav_classes);

                //Bundle bundle = new Bundle();
                //bundle.putInt(tab, classTab);
                //Navigation.findNavController(root).navigate(R.id.action_exit_add_class, bundle);

                Navigation.findNavController(root).navigate(R.id.action_exit_add_class);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this.getViewLifecycleOwner(), callback);

                FloatingActionButton fab = root.findViewById(R.id.fabAddClass);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassData classInfo = finishAddingClass(view);
                if(classInfo!=null) {
                    appModel.insertClass(classInfo);
                    Navigation.findNavController(view).navigateUp();
                }
            }
        });

            protected void finishAddingClass2(View view){
        EditText editTextClassName = (EditText) requireActivity().findViewById(R.id.editTextClassName);
        String className = editTextClassName.getText().toString();

        if(className.equals("") || className.equals(" ")) {
            Toast.makeText(requireContext(), errorClassName, Toast.LENGTH_LONG).show();
        }
        else {
            closeKeyboard(view);
            ClassData classInfo = new ClassData(className);

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

            appModel.insertClass(classInfo);
            Navigation.findNavController(view).navigateUp();
        }
    }

        */