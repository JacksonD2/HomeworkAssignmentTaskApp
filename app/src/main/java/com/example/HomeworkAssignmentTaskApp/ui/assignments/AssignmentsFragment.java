package com.example.HomeworkAssignmentTaskApp.ui.assignments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.deprecated.ClassObject;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.Calendar.CalendarFragment;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.Classes.ClassesFragment;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming.AssignmentListAdapter;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming.UpcomingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AssignmentsFragment extends Fragment {

    private ViewPager2 pager;
    private TabLayout mTabLayout;
    public static final String TAB_NUMBER = "tab";

    public AssignmentsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_assignments, container, false);

        //Floating action button on bottom right
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            //Bundle bundle = new Bundle();
            if(pager.getCurrentItem() == 1) //bundle.putInt(TAB_NUMBER, 1);
                Navigation.findNavController(view).navigate(R.id.action_add_new_class);
            else
                Navigation.findNavController(view).navigate(R.id.action_add_new_assignment);
        });
        //fab.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.addClassFragment, null));

        //Tabs and swiping
        pager = root.findViewById(R.id.viewPager);
        pager.setUserInputEnabled(false);
        mTabLayout = root.findViewById(R.id.tabLayout);
        mTabLayout.getTabAt(0);
        mTabLayout.setHorizontalScrollBarEnabled(false);
        pager.setAdapter(new AssignmentsFragmentAdapter(this));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition(), false);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }
        });

        return root;
    }

    @Override
    public void onResume (){
        super.onResume();
        //mTabLayout.setScrollPosition(pager.getCurrentItem(),0,false, false);
        TabLayout.Tab tab = mTabLayout.getTabAt(pager.getCurrentItem());
        if(tab!=null) tab.select();
    }

    private static class AssignmentsFragmentAdapter extends FragmentStateAdapter {

        public AssignmentsFragmentAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

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
}

/*
                if(getArguments()!=null) {
                    System.out.println("\n\n\n\nadssssssasdddddddddddddddddddddddqweqwewqe \n\n\nweq qwwqe qwe ");
                    int tabIndex = getArguments().getInt("tab");
                    pager.setCurrentItem(tabIndex, false);
                    mTabLayout.setScrollPosition(tabIndex,0f,true);
                }
 */



        /*if(savedInstanceState!=null) {
            System.out.println("\n\n\n\nadssssssasdddddddddddddddddddddddqweqwewqe \n\n\nweq qwwqe qwe ");
            int tabIndex = savedInstanceState.getInt("tab");
            pager.setCurrentItem(tabIndex, false);
            mTabLayout.setScrollPosition(tabIndex,0f,true);
        }*/

//mTabLayout.setScrollPosition(pager.getCurrentItem(),0f,true);

//custom back button
        /*OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                if(getArguments()!=null) {
                    System.out.println("\n\n\n\nadssssssasdddddddddddddddddddddddqweqwewqe \n\n\nweq qwwqe qwe ");
                    int tabIndex = getArguments().getInt("tab");
                    pager.setCurrentItem(tabIndex, false);
                    mTabLayout.setScrollPosition(tabIndex,0f,true);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this.getViewLifecycleOwner(), callback);*/



    /*@Override
    public void onViewCreated (@NotNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        if(savedInstanceState!=null) {
            System.out.println("\n\n\n\nadssssssasdddddddddddddddddddddddqweqwewqe \n\n\nweq qwwqe qwe ");
            int tabIndex = savedInstanceState.getInt("tab");
            pager.setCurrentItem(tabIndex, false);
            mTabLayout.setScrollPosition(tabIndex,0f,true);
        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState (Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("tab", mTabLayout.getSelectedTabPosition());
    }*/

                    /*appModel.getAssignmentList().observe(getViewLifecycleOwner(), assignmentData -> {
                        Fragment fragment = ((AssignmentsFragmentAdapter)
                                Objects.requireNonNull(pager.getAdapter())).currentFragment;

                        if(fragment instanceof UpcomingFragment) {
                            AssignmentListAdapter listAdapter = ((UpcomingFragment)fragment).listAdapter;
                            if(listAdapter!=null){
                                listAdapter.notifyDataSetChanged();
                            }
                        }
                    });*/

/*        BottomNavigationView bottom_nav_view;
        FragmentContainerView fragmentContainerView;

        fragmentContainerView = root.findViewById(R.id.fragmentContainerView);
        bottom_nav_view = root.findViewById(R.id.bottom_nav_view);
        bottom_nav_view.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if(id == R.id.navigation_upcoming){

            }
            else if(id == R.id.navigation_classes){

            }
            else if(id == R.id.navigation_completed){

            }
            else {

            }

            return true;
        });*/