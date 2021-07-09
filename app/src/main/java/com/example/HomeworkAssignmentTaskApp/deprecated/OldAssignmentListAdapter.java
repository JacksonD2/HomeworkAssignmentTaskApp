package com.example.HomeworkAssignmentTaskApp.deprecated;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.FormattingHelper;
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;
import com.google.android.material.color.MaterialColors;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class OldAssignmentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AssignmentData> incompleteAssignmentList, completeAssignmentList;
    private ApplicationViewModel appModel;
    private Context context;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public OldAssignmentListAdapter(){
        incompleteAssignmentList = new ArrayList<>();
        completeAssignmentList = new ArrayList<>();
    }

    public OldAssignmentListAdapter(Context ct, ApplicationViewModel model,
                                 List<AssignmentData> incompleteList, List<AssignmentData> completeList) {
        context = ct;
        appModel = model;

        if(incompleteList==null || completeList==null){
            incompleteAssignmentList = new ArrayList<>();
            completeAssignmentList = new ArrayList<>();
        }
        else{
            incompleteAssignmentList = incompleteList;
            Collections.sort(incompleteAssignmentList);
            completeAssignmentList = completeList;
            Collections.sort(completeAssignmentList);
        }
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
        //Headers
        if(holder instanceof AssignmentCategoryViewHolder) {
            AssignmentCategoryViewHolder viewHolder = (AssignmentCategoryViewHolder) holder;

            if (position == 0) {
                viewHolder.assignment_category_txt.setText(context.getResources().getString(R.string.category_upcoming));
            }
            else if (position == incompleteAssignmentList.size()+1){
                viewHolder.assignment_category_txt.setText(context.getResources().getString(R.string.category_complete));
            }
        }
        //Assignments
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

            //values
            viewHolder.assignmentId = currentAssignment.getAssignmentId();
            viewHolder.priority = currentAssignment.getPriority();
            viewHolder.assignment_name_txt.setText(currentAssignment.getAssignmentName());
            Date date = currentAssignment.getDueDate();
            //Date and priority
            if (date != null) {
                viewHolder.assignment_due_date_txt.setText(FormattingHelper.showDateFormat.format(date));

                Calendar c1 = Calendar.getInstance(), c2 = Calendar.getInstance();
                c1.setTime(date);
                c2.setTime(new Date(System.currentTimeMillis()));

                long diff = c1.getTimeInMillis() - c2.getTimeInMillis();
                int daysApart = (int) (diff / (24 * 60 * 60 * 1000));

                if(daysApart<0){
                    viewHolder.priority = -1;
                }
                else {
                    switch (daysApart){
                        case 0:
                            viewHolder.priority+=3;
                        case 1:
                            viewHolder.priority+=2;
                        case 2:
                            viewHolder.priority+=1;
                    }
                }
            }
            else {
                viewHolder.assignment_due_date_txt.setText("");
            }

            //Class Info
            ClassData classData;
            if(currentAssignment.getClassId()!=-1 &&
                    (classData = appModel.getClassData(currentAssignment.getClassId()))!=null) {
                viewHolder.assignment_class_name_txt.setText(classData.getClassName());
                if(classData.getClassColor()!=0){
                    viewHolder.assignment_class_color.setBackgroundColor(classData.getClassColor());
                }
                else {
                    viewHolder.assignment_class_color.setBackgroundColor(
                            MaterialColors.getColor(holder.itemView, R.attr.colorOnBackground));
                }
            }
            else {
                viewHolder.assignment_class_name_txt.setText("");
                viewHolder.assignment_class_color.setBackgroundColor(
                        MaterialColors.getColor(holder.itemView, R.attr.colorOnBackground));
            }
            //colors and formatting
            if(currentAssignment.isComplete()){
                viewHolder.setCompleteView();
            }
            else {
                viewHolder.setIncompleteView();
            }
            //OnClickListener
            holder.itemView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                if(appModel.isAssignment(currentAssignment.getAssignmentId())) {
                    bundle.putLong(FormattingHelper.ASSIGNMENT_ID, currentAssignment.getAssignmentId());
                    Navigation.findNavController(view).navigate(R.id.action_get_assignment_info, bundle);
                }
                else {
                    Toast.makeText(context, R.string.data_error,
                            Toast.LENGTH_SHORT).show();
                    superNotifyDataSetChanged();
                }
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
        if(incompleteAssignmentList.size()==0 && completeAssignmentList.size()==0){
            return 0;
        }
        return incompleteAssignmentList.size()+completeAssignmentList.size()+2;
    }

    /*
     * ---------------------------- New Methods -----------------------------------
     */

    public boolean itemSwiped(AssignmentListViewHolder viewHolder){
        AssignmentData assignmentData = appModel.getAssignmentData(viewHolder.assignmentId);
        int initialPos;

        if(assignmentData.isComplete()){
            assignmentData.setComplete(false);
            initialPos = viewHolder.getBindingAdapterPosition()-2-incompleteAssignmentList.size();
            completeAssignmentList.remove(initialPos);
            appModel.sCompleteAssignmentList.setValue(completeAssignmentList);
            //pos = sortedInsert(assignmentData)+1;
        }
        else {
            assignmentData.setComplete(true);
            initialPos = viewHolder.getBindingAdapterPosition()-1;
            incompleteAssignmentList.remove(initialPos);
            appModel.sIncompleteAssignmentList.setValue(incompleteAssignmentList);
            //pos = sortedInsert(assignmentData)+2+incompleteAssignmentList.size();
        }
        appModel.updateAssignment(assignmentData);
        notifyItemRemoved(viewHolder.getLayoutPosition());
        insertAssignment(assignmentData);

        return assignmentData.isComplete();
    }

    public void insertAssignment(AssignmentData assignmentData) {
        int pos;

        if (assignmentData.isComplete()){
            pos = 2 + incompleteAssignmentList.size() +
                    sortedInsert(assignmentData, completeAssignmentList);
            appModel.sCompleteAssignmentList.setValue(completeAssignmentList);
        }
        else {
            pos = 1 + sortedInsert(assignmentData, incompleteAssignmentList);
            appModel.sIncompleteAssignmentList.setValue(incompleteAssignmentList);
        }


        //pos += assignmentData.isComplete() ? 2 + incompleteAssignmentList.size() : 1;
        notifyItemInserted(pos);
    }

    private int sortedInsert(AssignmentData assignmentData, List<AssignmentData> list){
        //List<AssignmentData> list = assignmentData.isComplete() ? completeAssignmentList : incompleteAssignmentList;
        int i = Collections.binarySearch(list, assignmentData);

        if (i < 0) {
            i = -i - 1;
        }
        list.add(i, assignmentData);
        //System.out.println(assignmentData.getAssignmentId());

        return i;
    }

    public String deleteAssignment(long assignmentId){
        for(int i = 0; i < completeAssignmentList.size(); i++){
            if(completeAssignmentList.get(i).getAssignmentId()==assignmentId){
                AssignmentData assignmentData = completeAssignmentList.remove(i);
                //notifyItemRemoved(i+incompleteAssignmentList.size()+2);
                return assignmentData.getAssignmentName();
            }
        }
        for(int i = 0; i < incompleteAssignmentList.size(); i++){
            if(incompleteAssignmentList.get(i).getAssignmentId()==assignmentId){
                AssignmentData assignmentData = incompleteAssignmentList.remove(i);
                //notifyItemRemoved(i+1);
                return assignmentData.getAssignmentName();
            }
        }

        return "No assignment found";
    }

    public void superNotifyDataSetChanged(){
        appModel.refreshSCompleteAssignmentList();
        appModel.refreshSIncompleteAssignmentList();
        incompleteAssignmentList = appModel.sIncompleteAssignmentList.getValue();
        completeAssignmentList = appModel.sCompleteAssignmentList.getValue();
        Collections.sort(incompleteAssignmentList);
        Collections.sort(completeAssignmentList);
        notifyDataSetChanged();
    }

    public void superNotifyDataSetChanged(List<AssignmentData> incompleteAssignmentList,
                                          List<AssignmentData> completeAssignmentList){
        this.incompleteAssignmentList = incompleteAssignmentList;
        this.completeAssignmentList = completeAssignmentList;
        //Collections.sort(incompleteAssignmentList);
        //Collections.sort(completeAssignmentList);
        notifyDataSetChanged();
    }

    /*
     * ---------------------------- ViewHolders -----------------------------------
     */

    public static class AssignmentListViewHolder extends RecyclerView.ViewHolder {
        public TextView assignment_name_txt, assignment_class_name_txt, assignment_due_date_txt;
        private final ImageView assignment_class_color;
        private final ColorStateList assignment_name_color, assignment_due_date_color;
        private long assignmentId;
        private int priority;

        public AssignmentListViewHolder(@NonNull View itemView) {
            super(itemView);
            assignment_name_txt = itemView.findViewById(R.id.assignment_name_txt);
            assignment_class_name_txt = itemView.findViewById((R.id.assignment_class_name_txt));
            assignment_name_color = assignment_class_name_txt.getTextColors();
            assignment_due_date_txt = itemView.findViewById((R.id.assignment_due_date_txt));
            assignment_class_color = itemView.findViewById((R.id.assignment_class_color));
            assignment_due_date_color = assignment_due_date_txt.getTextColors();
            assignmentId = -1;
            priority = 0;
        }

        public void setIncompleteView(){
            assignment_name_txt.setPaintFlags(assignment_name_txt.getPaintFlags() &
                    (~Paint.STRIKE_THRU_TEXT_FLAG));
            assignment_name_txt.setTextColor(assignment_name_color);
            assignment_due_date_txt.setPaintFlags(assignment_name_txt.getPaintFlags() &
                    (~Paint.STRIKE_THRU_TEXT_FLAG));

            if(priority==0) {
                //assignment_name_txt.setTextColor(assignment_name_color);
                assignment_due_date_txt.setTextColor(assignment_due_date_color);
            }
            else {
                //FormattingHelper.setPriorityColor(assignment_name_txt, priority);
                FormattingHelper.setPriorityColor(assignment_due_date_txt, priority);
            }
        }

        public void setCompleteView(){
            assignment_name_txt.setPaintFlags(assignment_name_txt.getPaintFlags() |
                    Paint.STRIKE_THRU_TEXT_FLAG);
            assignment_name_txt.setTextColor(Color.GREEN);

            assignment_due_date_txt.setPaintFlags(assignment_due_date_txt.getPaintFlags() |
                    Paint.STRIKE_THRU_TEXT_FLAG);
            assignment_due_date_txt.setTextColor(Color.GREEN);
        }
    }

    public static class AssignmentCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView assignment_category_txt;

        public AssignmentCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            assignment_category_txt = itemView.findViewById(R.id.assignment_category_txt);
        }
    }
}

//notifyItemMoved(viewHolder.getAdapterPosition(), pos);
//notifyDataSetChanged()
//notifyItemInserted(pos);
//insertAssignment(assignmentData.isComplete(), assignmentData);

                /*else if(daysApart==0){
                    viewHolder.priority+=3;
                }
                else if(daysApart==1){
                    viewHolder.priority+=2;
                }
                else if(daysApart==2){
                    viewHolder.priority+=1;
                }*/
                /*if(c1.get(Calendar.ERA) == c2.get(Calendar.ERA) &&
                        c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)){
                    int daysApart = c1.get(Calendar.DAY_OF_YEAR)-c2.get(Calendar.DAY_OF_YEAR);

                    /*if(c1.get(Calendar.DAY_OF_YEAR)>c2.get(Calendar.DAY_OF_YEAR)){
                        viewHolder.priority = 5;
                    }
                    if(daysApart<=0){
                        viewHolder.priority = 5;
                    }
                    else if(daysApart==1){
                        viewHolder.priority+=2;
                    }
                    else if(daysApart==2){
                        viewHolder.priority+=1;
                    }
                }
                else {
                    viewHolder.priority = 5;
                }*/
//assignment lists
//int i = 0;
//boolean wasLoaded = true;
        /*while((incompleteAssignmentList = appModel.getIncompleteAssignmentList().getValue()) == null){
            if(i==100){
                wasLoaded = false;
                incompleteAssignmentList = new ArrayList<>();
                completeAssignmentList = new ArrayList<>();
                break;
            }
            i++;
        }*/
        /*if((incompleteAssignmentList = appModel.getIncompleteAssignmentList().getValue()) == null){
            incompleteAssignmentList = new ArrayList<>();
            completeAssignmentList = new ArrayList<>();
        }
        else{
            Collections.sort(incompleteAssignmentList);
            completeAssignmentList = appModel.getCompleteAssignmentList().getValue();
            assert completeAssignmentList != null;
            Collections.sort(completeAssignmentList);
        }*/