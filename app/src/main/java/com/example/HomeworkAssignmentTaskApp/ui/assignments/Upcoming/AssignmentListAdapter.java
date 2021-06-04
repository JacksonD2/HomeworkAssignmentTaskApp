package com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AssignmentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //String data1[], data2[];
    //int images[];
    private List<AssignmentData> incompleteAssignmentList, completeAssignmentList;
    private ApplicationViewModel appModel;
    private Context context;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public AssignmentListAdapter(Context ct, ApplicationViewModel model) {
        context = ct;
        appModel = model;

        //assignment lists
        int i = 0;
        while((incompleteAssignmentList = appModel.getIncompleteAssignmentList().getValue()) == null){
            if(i==100){
                incompleteAssignmentList = new ArrayList<>();
                break;
            }
            i++;
        }
        Collections.sort(incompleteAssignmentList);
        i = 0;
        while((completeAssignmentList = appModel.getCompleteAssignmentList().getValue()) == null){
            if(i==100){
                completeAssignmentList = new ArrayList<>();
                break;
            }
            i++;
        }
        Collections.sort(completeAssignmentList);

    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==TYPE_HEADER) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.assignment_category, parent, false);
            return new AssignmentCategoryViewHolder(view);
        }
        else /*if(viewType==TYPE_ITEM)*/{
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.assignment_row, parent, false);
            return new AssignmentListViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof AssignmentCategoryViewHolder) {
            AssignmentCategoryViewHolder viewHolder = (AssignmentCategoryViewHolder) holder;

            if (position == 0) {
                viewHolder.assignment_category_txt.setText(context.getResources().getString(R.string.category_upcoming));
            }
            else if (position == incompleteAssignmentList.size()+1){
                viewHolder.assignment_category_txt.setText(context.getResources().getString(R.string.category_complete));
            }
        }
        else if (holder instanceof AssignmentListViewHolder){
            AssignmentListViewHolder viewHolder = (AssignmentListViewHolder) holder;
            AssignmentData currentAssignment;
            int incompleteSize = incompleteAssignmentList.size();

            position -= 1;
            if (position < incompleteSize) {
                currentAssignment = incompleteAssignmentList.get(position);
            }
            else {
                currentAssignment = completeAssignmentList.get(position - incompleteSize - 1);
            }
            viewHolder.assignmentId = currentAssignment.getAssignmentId();
            //values
            viewHolder.assignment_name_txt.setText(currentAssignment.getAssignmentName());
            Date date = currentAssignment.getDueDate();
            if (date != null) {
                viewHolder.assignment_due_date_txt.setText(ApplicationViewModel.setDateFormat.format(date));
            }
            ClassData classData;
            if(currentAssignment.getClassId()!=-1 &&
                    (classData = appModel.getClassData(currentAssignment.getClassId()))!=null) {
                    viewHolder.assignment_class_name_txt.setText(classData.getClassName());
            }
            else {
                viewHolder.assignment_class_name_txt.setText("");
            }

            //colors and formatting
            if(currentAssignment.isComplete()){
                viewHolder.assignment_name_txt.setPaintFlags(viewHolder.assignment_name_txt.getPaintFlags() |
                        Paint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.assignment_name_txt.setTextColor(Color.GREEN);
                viewHolder.assignment_due_date_txt.setPaintFlags(viewHolder.assignment_due_date_txt.getPaintFlags() |
                        Paint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.assignment_due_date_txt.setTextColor(Color.GREEN);
            }

            holder.itemView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putInt(UpcomingFragment.ASSIGNMENT_ID, currentAssignment.getAssignmentId());
                bundle.putInt("tab", 0);
                Navigation.findNavController(view).navigate(R.id.action_get_assignment_info, bundle);
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == (incompleteAssignmentList.size()+1)){
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return incompleteAssignmentList.size()+completeAssignmentList.size()+2;
    }

    public void itemSwiped(AssignmentListViewHolder viewHolder){
        AssignmentData assignmentData = appModel.getAssignmentData(viewHolder.assignmentId);
        int pos, initialPos;

        //assignmentData.setComplete(!assignmentData.isComplete());
        if(assignmentData.isComplete()){
            assignmentData.setComplete(false);
            //initialPos = viewHolder.getAdapterPosition()-2-incompleteAssignmentList.size();
            initialPos = viewHolder.getBindingAdapterPosition()-2-incompleteAssignmentList.size();
            completeAssignmentList.remove(initialPos);
            pos = sortedInsert(incompleteAssignmentList, assignmentData)+1;
        }
        else {
            assignmentData.setComplete(true);
            //initialPos = viewHolder.getAdapterPosition()-1;
            initialPos = viewHolder.getBindingAdapterPosition()-1;
            incompleteAssignmentList.remove(initialPos);
            pos = sortedInsert(completeAssignmentList, assignmentData)+2+incompleteAssignmentList.size();
        }
        appModel.updateAssignment(assignmentData);
        //System.out.println("Initial position: "+viewHolder.getAdapterPosition()+", New position: "+pos);
        //notifyItemMoved(viewHolder.getAdapterPosition(), pos);
        //notifyDataSetChanged();
        notifyItemRemoved(viewHolder.getLayoutPosition());
        notifyItemInserted(pos);
    }

    public int sortedInsert(List<AssignmentData> list, AssignmentData assignmentData){
        /*int i, size = list.size();

        for(i = 0; i < size; i++){
            int comparison = list.get(i).compareTo(assignmentData);
            if(comparison<0) {
                continue;
            }
            else if(comparison==0){
                break;
            }
        }

        list.add(i, assignmentData);*/

        int i = Collections.binarySearch(list, assignmentData);

        if (i < 0) {
            i = -i - 1;
        }
        list.add(i, assignmentData);

        return i;
    }

    public class AssignmentListViewHolder extends RecyclerView.ViewHolder {
        public TextView assignment_name_txt, assignment_class_name_txt, assignment_due_date_txt;
        public int assignmentId;

        public AssignmentListViewHolder(@NonNull View itemView) {
            super(itemView);
            assignment_name_txt = itemView.findViewById(R.id.assignment_name_txt);
            assignment_class_name_txt = itemView.findViewById((R.id.assignment_class_name_txt));
            assignment_due_date_txt = itemView.findViewById((R.id.assignment_due_date_txt));
            assignmentId = -1;
        }
    }

    public class AssignmentCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView assignment_category_txt;

        public AssignmentCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            assignment_category_txt = itemView.findViewById(R.id.assignment_category_txt);
        }
    }
}

