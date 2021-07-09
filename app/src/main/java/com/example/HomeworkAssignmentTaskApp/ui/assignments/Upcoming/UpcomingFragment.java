package com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.SwipeCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UpcomingFragment extends Fragment {
    public AssignmentListAdapter listAdapter;
    private ApplicationViewModel appModel;
    private RecyclerView recyclerView;
    public static final String classAdded = "classAdded";

    public UpcomingFragment() {
        super(R.layout.fragment_upcoming);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upcoming, container, false);

        //ViewModel
        appModel = new ViewModelProvider(requireActivity()).get(ApplicationViewModel.class);

        //ProgressBar progressBar = root.findViewById(R.id.progressBar);
        //sets up task list
        recyclerView = root.findViewById(R.id.taskList);
        //listAdapter = new AssignmentListAdapter(getContext(), appModel, appModel.getIncompleteAssignmentList().getValue(), appModel.getCompleteAssignmentList().getValue());
        listAdapter = new AssignmentListAdapter(getContext(), appModel);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new SwipeCallback(0, ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT,
                        listAdapter, requireContext(), recyclerView);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(listAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fabRefresh = root.findViewById(R.id.fabRefresh);
        fabRefresh.setOnClickListener(v -> listAdapter.superNotifyDataSetChanged());

        appModel.getAssignmentListLoaded().observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean) {
                List<AssignmentData> list = appModel.getAssignmentList().getValue();

                if(list!=null) {
                    int numAssignments = list.size();

                    appModel.refreshSIncompleteAssignmentList();
                    appModel.refreshSCompleteAssignmentList();

                    if (numAssignments != 0)
                        listAdapter.notifyItemRangeInserted(0, numAssignments + 2);
                    //progressBar.setVisibility(View.GONE);
                    appModel.setAssignmentListLoaded(false);
                }
            }
        });
        appModel.newAssignment.observe(getViewLifecycleOwner(), v -> {
            /*if(appModel.isAssignmentInserted() && v!=null){
                listAdapter.notifyNewAssignment(v);
                appModel.setAssignmentInserted(false);
                appModel.newAssignment.setValue(null);
            }*/
            //listAdapter.notifyDataSetChanged();
        });
        appModel.getDeletedAssignmentId().observe(getViewLifecycleOwner(), v -> {
            //listAdapter.superNotifyDataSetChanged();
            //listAdapter.notifyAssignmentRemoved(v);
            //appModel.setDeletedAssignmentId(-1);
        });
        appModel.assignmentListModified.observe(getViewLifecycleOwner(), o -> {
            if(o!=listAdapter){
                Log.d(this.getTag(), "Main List adapter updated");
                listAdapter.notifyDataSetChanged();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

/*tasks = getResources().getStringArray(R.array.tasks);
descriptions = getResources().getStringArray(R.array.description);
listAdapter = new AssignmentListAdapter(getContext(), tasks, descriptions, images);*/

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
                /*Toast.makeText(requireContext(),
                        requireContext().getString(message, holder.assignment_name_txt.getText()),
                        Toast.LENGTH_SHORT).show();*/

                /*Snackbar snack = Snackbar.make(recyclerView, requireContext().getString(message,
                        holder.assignment_name_txt.getText()), Snackbar.LENGTH_SHORT);
                //.setAction(R.string.undo, v -> {})

                View view = snack.getView();
                CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams)view.getLayoutParams();
                params.gravity =  Gravity.CENTER_HORIZONTAL | Gravity.TOP;

                TypedValue tv = new TypedValue();
                int actionBarHeight=0;
                if (requireActivity().getTheme().resolveAttribute(R.attr.actionBarSize, tv, true))
                {
                    actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
                }

                params.setMargins(0, actionBarHeight+10, 0, 0);
                view.setLayoutParams(params);
                snack.show();*/
                /*TextView mainText = snack.getView().findViewById(R.id.snackbar_text);
                mainText.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));*/

                    /*List assignments = appModel.getIncompleteAssignmentList().getValue();
                    int incompSize = 0;
                    if(assignments!=null) {
                        incompSize = assignments.size();
                        listAdapter.notifyItemRangeInserted(1, assignments.size());
                    }
                    assignments = appModel.getCompleteAssignmentList().getValue();
                    if(assignments!=null) {
                        listAdapter.notifyItemRangeInserted(2+incompSize, assignments.size());
                    }
                    //appModel.getAssignmentListLoaded().removeObserver(this);*/
                    /*Toast.makeText(requireContext(),
                            "Loaded!",
                            Toast.LENGTH_SHORT).show();*/
//listAdapter.superNotifyDataSetChanged();

            /*if(appModel.isAssignmentDeleted()) {
                //String assignmentName = listAdapter.deleteAssignment(v);
                //String assignmentName = appModel.deleteAssignment(v);
                Toast.makeText(requireContext(),
                        requireContext().getString(R.string.assignment_successfully_deleted,
                        assignmentName), Toast.LENGTH_SHORT).show();
                listAdapter.superNotifyDataSetChanged();
                appModel.setAssignmentDeleted(false);
            }*/