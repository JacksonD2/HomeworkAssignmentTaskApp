package com.example.HomeworkAssignmentTaskApp.ui.assignments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming.AssignmentListAdapter;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

public class SwipeCallback extends ItemTouchHelper.SimpleCallback {
    private AssignmentListAdapter listAdapter;
    private Context context;
    private View view;

    public SwipeCallback(int dragDirs, int swipeDirs, AssignmentListAdapter listAdapter,
                         Context context, View view) {
        super(dragDirs, swipeDirs);
        this.listAdapter = listAdapter;
        this.context = context;
        this.view = view;
    }

    @Override
    public boolean onMove(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder, @NotNull RecyclerView.ViewHolder target) {
        // Perform drag
        return false;
    }

    @Override
    public void onSwiped(@NotNull RecyclerView.ViewHolder viewHolder, int direction) {
        // Remove item
        if (viewHolder instanceof AssignmentListAdapter.AssignmentItemViewHolder) {
            AssignmentListAdapter.AssignmentItemViewHolder holder = (AssignmentListAdapter.AssignmentItemViewHolder) viewHolder;

            boolean isComplete = listAdapter.itemSwiped(holder);
            int message = isComplete ? R.string.assignment_marked_complete : R.string.assignment_marked_incomplete;

            @SuppressLint("ShowToast") Snackbar snack = Snackbar.make(view, context.getString(message,
                    holder.assignment_name_txt.getText()), Snackbar.LENGTH_SHORT)
                    .setAnchorView(view);
            if(isComplete) {
                snack.setBackgroundTint(ContextCompat.getColor(context, R.color.green));
                snack.setTextColor(ContextCompat.getColor(context, R.color.black));
            }
            snack.show();
        }
    }

    @Override
    public int getSwipeDirs(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof AssignmentListAdapter.AssignmentItemViewHolder) {
            return super.getSwipeDirs(recyclerView, viewHolder);
        } else {
            return 0;
        }
    }
}
