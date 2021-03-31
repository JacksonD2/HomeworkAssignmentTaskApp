package com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming;

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
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddAssignmentFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    ApplicationViewModel appModel;
    Spinner spinnerClass, spinnerPriority;
    Button buttonDueDate, buttonDeleteAssignment;
    Date dueDate;
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    int priorityNum;

    public AddAssignmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_assignment, container, false);
        priorityNum = 0;

        //ViewModel
        appModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);

        //Priority
        spinnerPriority = (Spinner) root.findViewById(R.id.spinnerPriority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.priorities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(adapter);
        spinnerPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                priorityNum = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonDueDate = root.findViewById(R.id.buttonDueDate);
        buttonDueDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        buttonDeleteAssignment = root.findViewById(R.id.buttonDeleteAssignment);
        buttonDeleteAssignment.setVisibility(View.GONE);

        //fab
        FloatingActionButton fab = root.findViewById(R.id.fabAddAssignment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAddingAssignment(view);
            }
        });

        return root;
    }

    protected void finishAddingAssignment(View view){
        closeKeyboard(view);
        EditText editTextAssignmentName = requireActivity().findViewById(R.id.editTextAssignmentName);
        String assignmentName = editTextAssignmentName.getText().toString();

        if(!(assignmentName.equals("") || assignmentName.equals(" "))) {
            AssignmentData assignmentInfo = new AssignmentData(assignmentName);

            //optional information
            EditText editTextAssignmentDescription = requireActivity().findViewById(R.id.editTextAssignmentDescription);
            String assignmentDescription = editTextAssignmentDescription.getText().toString();
            if(!(assignmentDescription.equals(""))){
                assignmentInfo.setDescription(assignmentDescription);
            }
            if(dueDate!=null){
                assignmentInfo.setDueDate(dueDate);
            }
            assignmentInfo.setPriority(priorityNum);

            appModel.insertAssignment(assignmentInfo);
            Navigation.findNavController(view).navigateUp();
        }
    }

    protected void closeKeyboard(View view) {
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) requireActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, c.get(Calendar.YEAR),
                c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar c = Calendar.getInstance();
        c.set(i, i1, i2);
        dueDate = c.getTime();
        String text = dateFormat.format(dueDate);
        buttonDueDate.setText(text);
    }
}