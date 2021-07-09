package com.example.HomeworkAssignmentTaskApp.deprecated;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming.UpcomingFragment;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.Calendar.CalendarFragment;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.Classes.ClassesFragment;

public class AssignmentsFragmentAdapter extends FragmentStateAdapter {

    //ArrayList<ClassObject> classList;
    //ArrayList<AssignmentData> assignmentList;

    /*public AssignmentsFragmentAdapter(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }*/

    public AssignmentsFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    /*public AssignmentsFragmentAdapter(@NonNull Fragment fragment,
                                      ArrayList<ClassObject> classes, ArrayList<AssignmentData> assignments) {
        super(fragment);
    }*/

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //Fragment fragment;
        switch (position){
            case 1:
                return new ClassesFragment();
            case 2:
                return new CalendarFragment();
            default:
                return new UpcomingFragment();
        }
        //fragment.setArguments(new Bundle());
        //return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
