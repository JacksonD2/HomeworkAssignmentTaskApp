package com.example.HomeworkAssignmentTaskApp.ui.assignments.Classes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;
import com.example.HomeworkAssignmentTaskApp.deprecated.ClassObject;

import java.util.ArrayList;
import java.util.List;


public class ClassesFragment extends Fragment {

    ClassListAdapter listAdapter;
    RecyclerView recyclerView;
    private ArrayList<ClassObject> classList;

    public ClassesFragment() {
        super(R.layout.fragment_classes);
    }

    public ClassesFragment(ArrayList<ClassObject> classes) {
        super(R.layout.fragment_classes);
        classList = classes;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_classes, container, false);

        ApplicationViewModel appModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);
        appModel.getClassList().observe(getViewLifecycleOwner(), new Observer<List<ClassData>>() {
            @Override
            public void onChanged(List<ClassData> classData) {
                //appViewModel.appDatabase.classDao().getAllClasses();
                listAdapter.notifyDataSetChanged();
            }
        });

        recyclerView = root.findViewById(R.id.class_list);
        listAdapter = new ClassListAdapter(getContext(), appModel);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    /*@Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            onResume();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint())
        {
            return;
        }

        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do what you want
            }
        });
    }*/
}