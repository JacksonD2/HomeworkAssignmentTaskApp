package com.example.HomeworkAssignmentTaskApp.ui.assignments.Calendar;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.R;
//import com.kizitonwose.calendarview.CalendarView;
import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.SwipeCallback;
//import com.kizitonwose.calendarview.ui.ViewContainer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {
    private FilteredAssignmentListAdapter listAdapter;
    private CalendarView calendarView;
    private RecyclerView calendarAssignmentList;
    private ApplicationViewModel appModel;
    private long selectedDay;
    private TextView current_date_txt;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d", Locale.US);
    //CalendarView calendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_calendar, container, false);

        appModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);

        //recyclerview
        calendarAssignmentList = root.findViewById(R.id.calendarAssignmentList);

        List<AssignmentData> incompleteAssignmentList = appModel.getIncompleteAssignmentList().getValue(),
                completeAssignmentList = appModel.getCompleteAssignmentList().getValue();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date(System.currentTimeMillis()));

        listAdapter = new FilteredAssignmentListAdapter(getContext(), appModel,
                addAssignmentsOnSameDay(incompleteAssignmentList, c1),
                addAssignmentsOnSameDay(completeAssignmentList, c1));
        //new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        calendarAssignmentList.setAdapter(listAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new SwipeCallback(0, ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT,
                        listAdapter, requireContext(), calendarAssignmentList);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(calendarAssignmentList);
        calendarAssignmentList.setLayoutManager(new LinearLayoutManager(getContext()));

        calendarView = root.findViewById(R.id.calendarView);
        selectedDay = calendarView.getDate();
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) ->
                updateAssignmentListDate(year, month, dayOfMonth));

        current_date_txt = root.findViewById(R.id.current_date_txt);
        current_date_txt.setPaintFlags(current_date_txt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        current_date_txt.setText(dateFormat.format(new Date(selectedDay)));

        //observer
        appModel.getAssignmentListLoaded().observe(getViewLifecycleOwner(), aBoolean -> {
            System.out.println(1007);
            if(aBoolean) {
                /*Toast.makeText(requireContext(),
                        "Loaded!",
                        Toast.LENGTH_SHORT).show();*/
                refreshAssignmentList();
                //listAdapter.superNotifyDataSetChanged();
                appModel.setAssignmentListLoaded(false);
            }
        });
        appModel.newAssignment.observe(getViewLifecycleOwner(), v -> refreshAssignmentList());
        appModel.assignmentListModified.observe(getViewLifecycleOwner(), o -> {
            //refreshAssignmentList();
            if(o!=listAdapter){
                refreshAssignmentList();
            }
        });
        appModel.getDeletedAssignmentId().observe(getViewLifecycleOwner(), o -> refreshAssignmentList());

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        calendarView.setDate(selectedDay, true, true);
    }

    private void refreshAssignmentList(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(selectedDay));
        //calendar.setTime(new Date(calendarView.getDate()));
        updateAssignmentListDate(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void updateAssignmentListDate(int year, int month, int dayOfMonth){
        Calendar c1 = Calendar.getInstance();
        c1.set(year, month, dayOfMonth);
        selectedDay = c1.getTime().getTime();
        current_date_txt.setText(dateFormat.format(new Date(selectedDay)));

        listAdapter.superNotifyDataSetChanged(
                addAssignmentsOnSameDay(appModel.sIncompleteAssignmentList.getValue(), c1),
                addAssignmentsOnSameDay(appModel.sCompleteAssignmentList.getValue(), c1));
    }

    private List<AssignmentData> addAssignmentsOnSameDay(List<AssignmentData> list, Calendar c1){
        Calendar c2 = Calendar.getInstance();
        List<AssignmentData> newList = new ArrayList<>();

        if (list != null) {
            for(AssignmentData assignmentData: list){
                Date dueDate = assignmentData.getDueDate();

                if(dueDate!=null) {
                    c2.setTime(dueDate);

                    if(c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                            && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                            && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)){
                        newList.add(assignmentData);
                    }
                }
            }
        }

        return newList;
    }

}

    /*static class DayViewContainer extends ViewContainer {
        TextView textView = getView().findViewById(R.id.calendarDayText);

        public DayViewContainer(@NotNull android.view.View view) {
            super(view);
        }
    }*/

    /* calendarView.setDayBinder(new DayBinder<DayViewContainer>() {
            @NotNull
            @Override
            public DayViewContainer create(@NotNull View view) {
                return new DayViewContainer(view);
            }

            @Override
            public void bind(@NotNull DayViewContainer viewContainer, @NotNull CalendarDay calendarDay) {
                viewContainer.textView.setText(calendarDay.getDate().getDayOfMonth());
            }
        });*/

//Toast.makeText(requireContext(), dateFormat.format(new Date(calendarView.getDate())), Toast.LENGTH_SHORT).show();


        /*List<AssignmentData> incompleteAssignmentList = appModel.sIncompleteAssignmentList.getValue(),
                completeAssignmentList = appModel.sCompleteAssignmentList.getValue();

        incompleteAssignmentList = addAssignmentsOnSameDay(incompleteAssignmentList, c1);
        completeAssignmentList = addAssignmentsOnSameDay(completeAssignmentList, c1);

        listAdapter.superNotifyDataSetChanged(incompleteAssignmentList, completeAssignmentList);*/