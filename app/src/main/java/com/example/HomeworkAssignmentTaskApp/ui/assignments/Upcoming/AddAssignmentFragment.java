package com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.FormattingHelper;
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.Classes.ClassListAdapter;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddAssignmentFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    protected ApplicationViewModel appModel;
    //protected Spinner spinnerClasses, spinnerPriority;
    protected Button buttonPriority, buttonClassName, buttonDueDate, buttonDueTime, buttonDeleteAssignment;
    protected Date dueDate;
    protected int priorityNum, classId, dueHourOfDay, dueMinute, colorAttr;
    protected boolean isTimeSet;
    protected ColorStateList originalPriorityColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = setUpViews(inflater, container);

        dueHourOfDay = 23;
        dueMinute = 59;
        isTimeSet = false;

        buttonDeleteAssignment = root.findViewById(R.id.buttonDeleteAssignment);
        buttonDeleteAssignment.setVisibility(View.GONE);

        //buttonClassName.setText(R.string.add_assignment_class_name);

        //fab
        FloatingActionButton fab = root.findViewById(R.id.fabAddAssignment);
        fab.setOnClickListener(view -> {
            AssignmentData assignmentData = finishAddingAssignment(view, null);
            if(assignmentData!=null){
                appModel.insertAssignment(assignmentData);
                Navigation.findNavController(view).navigateUp();
            }
        });

        return root;
    }

    protected View setUpViews(LayoutInflater inflater, ViewGroup container){
        View root = inflater.inflate(R.layout.fragment_add_assignment, container, false);
        priorityNum = 0;
        classId = -1;

        //ViewModel
        appModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);
        appModel.getSelectedClassId().observe(getViewLifecycleOwner(), integer -> {
            if(integer!=-1) {
                classId = integer;
                ClassData currentClass = appModel.getClassData(classId);
                if (currentClass != null) {
                    buttonClassName.setText(currentClass.getClassName());
                    appModel.setSelectedClassId(-1);
                }
            }
        });
        appModel.getSelectedPriority().observe(getViewLifecycleOwner(), integer -> {
            priorityNum = integer;
            FormattingHelper.setPriority(buttonPriority, priorityNum, originalPriorityColor);
        });

        //Classes
        buttonClassName = root.findViewById(R.id.buttonClassName);
        buttonClassName.setOnClickListener(view -> {
            DialogFragment newFragment = new ClassDialogFragment();
            newFragment.show(requireActivity().getSupportFragmentManager(), "class_picker");
        });

        //Priority
        buttonPriority = root.findViewById(R.id.buttonPriority);
        FormattingHelper.setPriority(buttonPriority, priorityNum);
        //colorAttr = buttonPriority.
        originalPriorityColor = buttonPriority.getTextColors();
        buttonPriority.setOnClickListener(view -> {
            DialogFragment newFragment = new PriorityDialogFragment();
            newFragment.show(requireActivity().getSupportFragmentManager(), "priority_picker");
        });

        //Due Date
        buttonDueDate = root.findViewById(R.id.buttonDueDate);
        buttonDueDate.setOnClickListener(v -> showDatePickerDialog());

        //Due Time
        buttonDueTime = root.findViewById(R.id.buttonDueTime);
        buttonDueTime.setOnClickListener(v -> showTimePickerDialog());

        return root;
    }

    protected AssignmentData finishAddingAssignment(View view, AssignmentData assignmentInfo){
        closeKeyboard(view);
        EditText editTextAssignmentName = requireActivity().findViewById(R.id.editTextAssignmentName);
        String assignmentName = editTextAssignmentName.getText().toString();

        if(assignmentName.equals("") || assignmentName.equals(" ")){
            Toast.makeText(requireContext(), R.string.add_assignment_name_error,
                    Toast.LENGTH_LONG).show();
        }
        else if(dueDate==null){
            Toast.makeText(requireContext(), R.string.add_assignment_due_date_error,
                    Toast.LENGTH_LONG).show();
        }
        else{
            //required info
            if(assignmentInfo==null) assignmentInfo = new AssignmentData(assignmentName);
            else assignmentInfo.setAssignmentName(assignmentName);
            Calendar c = Calendar.getInstance();
            c.setTime(dueDate);
            c.set(Calendar.HOUR_OF_DAY, dueHourOfDay);
            c.set(Calendar.MINUTE, dueMinute);
            assignmentInfo.setDueDate(c.getTime());

            //optional info
            EditText editTextAssignmentDescription = requireActivity().findViewById(R.id.editTextAssignmentDescription);
            String assignmentDescription = editTextAssignmentDescription.getText().toString();
            if(!(assignmentDescription.equals(""))){
                assignmentInfo.setDescription(assignmentDescription);
            }
            assignmentInfo.setPriority(priorityNum);
            assignmentInfo.setClassId(classId);

            return assignmentInfo;
        }

        return null;
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
        String text = FormattingHelper.setDateFormat.format(dueDate);
        buttonDueDate.setText(text);
    }

    protected void showTimePickerDialog(){
        TimePickerDialog timePickerDialog;
        if(isTimeSet) {
            timePickerDialog = new TimePickerDialog(getContext(), this,
                    dueHourOfDay, dueMinute, false);
        }
        else {
            timePickerDialog = new TimePickerDialog(getContext(), this,
                    23, 59, false);
        }
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        dueHourOfDay = hourOfDay;
        dueMinute = minute;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        buttonDueTime.setText(FormattingHelper.timeFormat.format(c.getTime()));
        isTimeSet = true;
    }

    public static class PriorityDialogFragment extends DialogFragment {
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
            builder.setAdapter(new AddAssignmentFragment.ClassListArrayAdapter(requireContext(),
                    R.layout.class_row, R.id.class_name_txt, viewModel.getClassList().getValue()),
                    (dialogInterface, i) -> viewModel.setSelectedClassId(viewModel.getClassId(i)));
            return builder.create();
        }
    }

    public static class ClassListArrayAdapter extends ArrayAdapter<ClassData> {
        Context context;
        int layoutResourceId;
        List<ClassData> data;

        public ClassListArrayAdapter (Context context, int layoutResourceId, int textViewResourceId,
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

            ClassData currentClass = data.get(position);
            holder.formatClass(currentClass);
            holder.class_date_txt.setVisibility(View.GONE);

            return convertView;
        }

    }
}
