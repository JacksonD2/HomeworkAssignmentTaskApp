package com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.R;

import org.jetbrains.annotations.NotNull;

public class UpcomingFragment extends Fragment {
    private RecyclerView recyclerView;
    private AssignmentListAdapter listAdapter;
    public static final String ASSIGNMENT_ID = "assignmentId";

    public UpcomingFragment() {
        super(R.layout.fragment_upcoming);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upcoming, container, false);

        //ViewModel
        ApplicationViewModel appModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);
        /*appModel.getIncompleteAssignmentList().observe(getViewLifecycleOwner(), new Observer<List<AssignmentData>>() {
            @Override
            public void onChanged(List<AssignmentData> assignmentData) {
                listAdapter.notifyDataSetChanged();
            }
        });
        appModel.getCompleteAssignmentList().observe(getViewLifecycleOwner(), new Observer<List<AssignmentData>>() {
            @Override
            public void onChanged(List<AssignmentData> assignmentData) {
                listAdapter.notifyDataSetChanged();
            }
        });
        appModel.getAssignmentList().observe(getViewLifecycleOwner(), new Observer<List<AssignmentData>>() {
            @Override
            public void onChanged(List<AssignmentData> assignmentData) {
                listAdapter.notifyDataSetChanged();
            }
        });*/

        //sets up task list
        recyclerView = root.findViewById(R.id.taskList);
        listAdapter = new AssignmentListAdapter(getContext(), appModel);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(listAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager); //don't forget to check null when using

        return root;
    }

    /*ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
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
            };*/

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new SwipeCallback(0, ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT);
    class SwipeCallback extends ItemTouchHelper.SimpleCallback {

        public SwipeCallback(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder, @NotNull RecyclerView.ViewHolder target) {
            // Perform drag
            return false;
        }

        @Override
        public void onSwiped(@NotNull RecyclerView.ViewHolder viewHolder, int direction) {
            // Remove item
            if (viewHolder instanceof AssignmentListAdapter.AssignmentListViewHolder) {
                AssignmentListAdapter.AssignmentListViewHolder holder = (AssignmentListAdapter.AssignmentListViewHolder) viewHolder;
                listAdapter.itemSwiped(holder);
            }
        }

        @Override
        public int getSwipeDirs(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder) {
            if (viewHolder instanceof AssignmentListAdapter.AssignmentListViewHolder) {
                return super.getSwipeDirs(recyclerView, viewHolder);
            } else {
                return 0;
            }
        }
    }
}

/*tasks = getResources().getStringArray(R.array.tasks);
descriptions = getResources().getStringArray(R.array.description);
listAdapter = new AssignmentListAdapter(getContext(), tasks, descriptions, images);*/