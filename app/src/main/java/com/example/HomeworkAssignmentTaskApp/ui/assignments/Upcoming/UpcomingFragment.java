package com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;

import java.util.List;

public class UpcomingFragment extends Fragment {

    String tasks[], descriptions[];
    int images[] = {R.drawable.arthurchaseemote, R.drawable.arthurchaseemote};
    RecyclerView recyclerView;
    AssignmentListAdapter listAdapter;

    public UpcomingFragment() {
        super(R.layout.fragment_upcoming);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upcoming, container, false);

        //ViewModel
        ApplicationViewModel appModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);
        appModel.getAssignmentList().observe(getViewLifecycleOwner(), new Observer<List<AssignmentData>>() {
            @Override
            public void onChanged(List<AssignmentData> assignmentData) {
                listAdapter.notifyDataSetChanged();
            }
        });

        //sets up task list
        recyclerView = root.findViewById(R.id.taskList);
        listAdapter = new AssignmentListAdapter(getContext(), appModel);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //don't forget to check null when using

        return root;
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    //remove item from list ie: mNotes.remove(viewHolder.getAdapterPosition());
                    //listAdapter.notifyDataSetChanged();
                    //instructions from https://www.youtube.com/watch?v=M1XEqqo6Ktg
                }
            };
}

/*tasks = getResources().getStringArray(R.array.tasks);
descriptions = getResources().getStringArray(R.array.description);
listAdapter = new AssignmentListAdapter(getContext(), tasks, descriptions, images);*/