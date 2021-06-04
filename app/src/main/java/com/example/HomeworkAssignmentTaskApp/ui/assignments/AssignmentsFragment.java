package com.example.HomeworkAssignmentTaskApp.ui.assignments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.deprecated.ClassObject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class AssignmentsFragment extends Fragment {

    private ViewPager2 pager;
    private TabLayout mTabLayout;
    public static final String TAB_NUMBER = "tab";
    //ArrayList<ClassObject> classList;
   // ArrayList<AssignmentData> assignmentList;

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
            Bundle bundle = new Bundle();
            switch(pager.getCurrentItem()){
                case 0:
                    bundle.putInt(TAB_NUMBER, 0);
                    Navigation.findNavController(view).navigate(R.id.action_add_new_assignment, bundle);
                    break;
                case 1:
                    bundle.putInt(TAB_NUMBER, 1);
                    Navigation.findNavController(view).navigate(R.id.action_add_new_class, bundle);
                    break;
            }
        });
        //fab.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.addClassFragment, null));

        //Tabs and swiping
        pager = root.findViewById(R.id.viewPager);
        pager.setUserInputEnabled(false);
        mTabLayout = root.findViewById(R.id.tabLayout);
        pager.setAdapter(new AssignmentsFragmentAdapter(this));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
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
        mTabLayout.setScrollPosition(pager.getCurrentItem(),0f,true);
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