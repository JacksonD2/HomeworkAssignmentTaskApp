package com.example.HomeworkAssignmentTaskApp.ui.assignments.Classes;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;
import com.example.HomeworkAssignmentTaskApp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;

//fix when user clicks on back button
public class AddClassFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    //ApplicationViewModel appModel;
    protected ApplicationViewModel appModel;
    protected Spinner spinner;
    protected Button buttonStartDate, buttonEndDate, buttonDeleteClass;
    protected Date startDate, endDate;
    //String tab = "tab";
    //int classTab = 1;
    //String startDate = "startDate", endDate = "endDate";

    public AddClassFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_add_class, container, false);

        //ViewModel
        appModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);

        //spinner
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

        //buttons
        buttonDeleteClass = root.findViewById(R.id.buttonDeleteClass);
        buttonDeleteClass.setVisibility(View.GONE);
        //dates
        buttonStartDate = (Button) root.findViewById(R.id.buttonStartDate);
        buttonStartDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePickerDialog(buttonStartDate);
            }
        });
        buttonEndDate = (Button) root.findViewById(R.id.buttonEndDate);
        buttonEndDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePickerDialog(buttonEndDate);
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
        requireActivity().getOnBackPressedDispatcher().addCallback(this.getViewLifecycleOwner(), callback);*/

        return root;
    }

    protected void finishAddingClass(View view){
        closeKeyboard(view);
        EditText editTextClassName = (EditText) requireActivity().findViewById(R.id.editTextClassName);
        String className = editTextClassName.getText().toString();
        System.out.println(className);
        //Boolean wasAdded = FileManager.addNewClass(message, view, model);
        if(!(className.equals("") || className.equals(" "))) {
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
            //Bundle bundle = new Bundle();
            //bundle.putInt(tab, classTab);
            //Navigation.findNavController(view).navigate(R.id.action_exit_add_class, bundle);

            Navigation.findNavController(view).navigate(R.id.action_exit_add_class);
        }
    }

    protected void closeKeyboard(View view) {
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) requireActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void showDatePickerDialog(Button button) {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, c.get(Calendar.YEAR),
                c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setTag(button);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String text = String.format("%d/%d/%d", i1+1, i2, i);
        Calendar c = Calendar.getInstance();
        c.set(i, i1, i2);
        if(datePicker.getTag().equals(buttonStartDate)) {
            buttonStartDate.setText(text);
            startDate = c.getTime();
        }
        else if(datePicker.getTag().equals(buttonEndDate)){
            buttonEndDate.setText(text);
            endDate = c.getTime();
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