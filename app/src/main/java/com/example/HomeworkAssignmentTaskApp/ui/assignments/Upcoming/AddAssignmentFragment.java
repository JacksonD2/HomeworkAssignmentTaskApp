package com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming;

import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.Classes.ClassListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddAssignmentFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    protected ApplicationViewModel appModel;
    //protected Spinner spinnerClasses, spinnerPriority;
    protected Button buttonPriority, buttonClassName, buttonDueDate, buttonDeleteAssignment;
    protected Date dueDate;
    protected final DateFormat dateFormat = ApplicationViewModel.setDateFormat;
    protected final String errorAssignmentName = "Assignment name must not be blank!", errorDueDate = "Assignment must have due date!";
    protected int priorityNum, classId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = setUpViews(inflater, container);

        buttonDeleteAssignment = root.findViewById(R.id.buttonDeleteAssignment);
        buttonDeleteAssignment.setVisibility(View.GONE);

        //fab
        FloatingActionButton fab = root.findViewById(R.id.fabAddAssignment);
        fab.setOnClickListener(this::finishAddingAssignment);

        return root;
    }

    protected View setUpViews(LayoutInflater inflater, ViewGroup container){
        View root = inflater.inflate(R.layout.fragment_add_assignment, container, false);
        priorityNum = 0;
        classId = -1;

        //ViewModel
        appModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);
        appModel.getSelectedClassId().observe(getViewLifecycleOwner(), integer -> {
            classId = integer;
            ClassData currentClass = appModel.getClassData(classId);
            if(currentClass!=null){
                buttonClassName.setText(currentClass.getClassName());
            }
        });
        appModel.getSelectedPriority().observe(getViewLifecycleOwner(), integer -> {
            priorityNum = integer;
            AssignmentInfoFragment.setPriority(buttonPriority, priorityNum);
        });

        //Classes
        buttonClassName = root.findViewById(R.id.buttonClassName);
        buttonClassName.setOnClickListener(view -> {
            DialogFragment newFragment = new ClassDialogFragment();
            newFragment.show(requireActivity().getSupportFragmentManager(), "class_picker");            });

        //Priority
        buttonPriority = root.findViewById(R.id.buttonPriority);
        AssignmentInfoFragment.setPriority(buttonPriority, priorityNum);
        buttonPriority.setOnClickListener(view -> {
            DialogFragment newFragment = new PriorityDialogFragment();
            newFragment.show(requireActivity().getSupportFragmentManager(), "priority_picker");
        });

        //Due Date
        buttonDueDate = root.findViewById(R.id.buttonDueDate);
        buttonDueDate.setOnClickListener(v -> showDatePickerDialog());

        return root;
    }

    protected void finishAddingAssignment(View view){
        closeKeyboard(view);
        EditText editTextAssignmentName = requireActivity().findViewById(R.id.editTextAssignmentName);
        String assignmentName = editTextAssignmentName.getText().toString();

        if(assignmentName.equals("") || assignmentName.equals(" ")){
            Toast.makeText(requireContext(), errorAssignmentName, Toast.LENGTH_LONG).show();
        }
        else if(dueDate==null){
            Toast.makeText(requireContext(), errorDueDate, Toast.LENGTH_LONG).show();
        }
        else{
            //required info
            AssignmentData assignmentInfo = new AssignmentData(assignmentName);
            assignmentInfo.setDueDate(dueDate);

            //optional info
            EditText editTextAssignmentDescription = requireActivity().findViewById(R.id.editTextAssignmentDescription);
            String assignmentDescription = editTextAssignmentDescription.getText().toString();
            if(!(assignmentDescription.equals(""))){
                assignmentInfo.setDescription(assignmentDescription);
            }
            assignmentInfo.setPriority(priorityNum);
            assignmentInfo.setClassId(classId);

            appModel.insertAssignment(assignmentInfo);
            Navigation.findNavController(view).navigateUp();
        }
    }

    protected void closeKeyboard(View view){
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) requireActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /*
     --------------------------------- Inner Classes and Dialogs -----------------------------------
     */

    protected void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this,
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
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

    public static class PriorityDialogFragment extends DialogFragment {
        /*PriorityDialogListener listener;

        public interface PriorityDialogListener {
            public void onPriorityClick(int priority);
        }*/
        ApplicationViewModel viewModel;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            viewModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);
        }

        @NotNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.assignment_priority_level)
                    .setItems(R.array.priorities, (dialog, which) -> viewModel.setSelectedPriority(which));
            return builder.create();
        }
    }

    public static class ClassDialogFragment extends DialogFragment {
        ApplicationViewModel viewModel;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            viewModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);
        }

        @NotNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            //LayoutInflater inflater = requireActivity().getLayoutInflater();
            builder.setAdapter(new AddAssignmentFragment.CustomAdapter(requireContext(),
                    R.layout.class_row, R.id.class_name_txt, viewModel.getClassList().getValue()),
                    (dialogInterface, i) -> viewModel.setSelectedClassId(viewModel.getClassId(i)));
            return builder.create();
        }
    }

    public static class CustomAdapter extends ArrayAdapter<ClassData> {
        Context context;
        int layoutResourceId;
        List<ClassData> data;

        public CustomAdapter (Context context, int layoutResourceId, int textViewResourceId,
                              List<ClassData> data) {
            super(context, layoutResourceId, textViewResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ClassListAdapter.ClassListViewHolder holder;

            if(convertView == null) {
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                convertView = inflater.inflate(layoutResourceId, parent, false);

                holder = new ClassListAdapter.ClassListViewHolder(convertView);
                convertView.setTag(holder);
            }
            else {
                holder = (ClassListAdapter.ClassListViewHolder)convertView.getTag();
            }
            holder.class_name_txt.setText(data.get(position).getClassName());

            return convertView;
        }

    }
}
