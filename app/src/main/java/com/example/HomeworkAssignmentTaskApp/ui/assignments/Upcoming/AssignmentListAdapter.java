package com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming;

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
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.FormattingHelper;
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.ListHelper;
import com.google.android.material.color.MaterialColors;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AssignmentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    
    /*public MutableLiveData<List<AssignmentData>>
            sIncompleteAssignmentList,
            sCompleteAssignmentList;*/
    protected final ApplicationViewModel appModel;
    private final Context context;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    public AssignmentListAdapter(Context ct, ApplicationViewModel model){
        context = ct;
        appModel = model;
        //sIncompleteAssignmentList = model.sIncompleteAssignmentList;
        //sCompleteAssignmentList = model.sCompleteAssignmentList;
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
            return new AssignmentItemViewHolder(view);
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
            else if (position == getIncompleteAssignmentList().size()+1){
                viewHolder.assignment_category_txt.setText(context.getResources().getString(R.string.category_complete));
            }
        }
        //Assignments
        else if (holder instanceof AssignmentItemViewHolder){
            AssignmentItemViewHolder viewHolder = (AssignmentItemViewHolder) holder;
            AssignmentData currentAssignment;
            int incompleteSize = getIncompleteAssignmentList().size();

            position -= 1;
            if (position < incompleteSize)
                currentAssignment = getIncompleteAssignmentList().get(position);
            else
                currentAssignment = getCompleteAssignmentList().get(position - incompleteSize - 1);

            //values
            viewHolder.assignmentId = currentAssignment.getAssignmentId();
            viewHolder.assignment_name_txt.setText(currentAssignment.getAssignmentName());

            //Priority and date
            setPriorityByDate(currentAssignment, viewHolder);

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
            if(currentAssignment.isComplete()) viewHolder.setCompleteView();
            else viewHolder.setIncompleteView();

            //OnClickListener
            holder.itemView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                if(appModel.isAssignment(currentAssignment.getAssignmentId())) {
                    bundle.putLong(FormattingHelper.ASSIGNMENT_ID, currentAssignment.getAssignmentId());
                    Navigation.findNavController(view).navigate(R.id.nav_assignment_info, bundle);
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
        if (position == 0 || position == (getIncompleteAssignmentList().size()+1)){
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        if(getIncompleteAssignmentList().size()==0 && getCompleteAssignmentList().size()==0){
            return 0;
        }
        return getIncompleteAssignmentList().size()+getCompleteAssignmentList().size()+2;
    }

    /*
     * ---------------------------- New Methods -----------------------------------
     */
    
    protected List<AssignmentData> getIncompleteAssignmentList(){
        return appModel.sIncompleteAssignmentList.getValue();
    }

    protected List<AssignmentData> getCompleteAssignmentList(){
        return appModel.sCompleteAssignmentList.getValue();
    }

    public boolean itemSwiped(AssignmentItemViewHolder viewHolder){
        AssignmentData assignmentData = appModel.getAssignmentData(viewHolder.assignmentId);

        if(assignmentData!=null) {
            if (assignmentData.isComplete()) {
                assignmentData.setComplete(false);
                removeAssignment(assignmentData, appModel.sCompleteAssignmentList);
            } else {
                assignmentData.setComplete(true);
                removeAssignment(assignmentData, appModel.sIncompleteAssignmentList);
            }

            appModel.updateAssignment(assignmentData);
            notifyItemRemoved(viewHolder.getLayoutPosition());
            insertAssignment(assignmentData);
            appModel.assignmentListModified.setValue(this);

            return assignmentData.isComplete();
        }
        else {
            superNotifyDataSetChanged();
            return false;
        }
    }

    //meant to be overridden
    protected void removeAssignment(AssignmentData assignmentData,
                                    @NotNull MutableLiveData<List<AssignmentData>> list){
        ListHelper.removeAssignment(assignmentData, list);
    }

    private void insertAssignment(AssignmentData assignmentData) {
        int pos;

        if (assignmentData.isComplete())
            pos = 2 + getIncompleteAssignmentList().size() + sortedInsert(assignmentData,
                    appModel.sCompleteAssignmentList);
        else
            pos = 1 + sortedInsert(assignmentData, appModel.sIncompleteAssignmentList);

        notifyItemInserted(pos);
    }

    public void notifyNewAssignment(AssignmentData assignmentData) {
        int pos, temp;

        if (assignmentData.isComplete()) {
            pos = 2 + getIncompleteAssignmentList().size();
            temp = findAssignmentPosition(getCompleteAssignmentList(), assignmentData.getAssignmentId());
        }
        else {
            pos = 1;
            temp = findAssignmentPosition(getIncompleteAssignmentList(), assignmentData.getAssignmentId());
        }

        if(temp!=-1)
            notifyItemInserted(pos+temp);
    }

    public void notifyAssignmentRemoved(long assignmentId){
        int pos = findAssignmentPosition(getCompleteAssignmentList(), assignmentId);
        if(pos!=-1)
            notifyItemRemoved(pos + 2 + getIncompleteAssignmentList().size());
        else {
            pos = findAssignmentPosition(getIncompleteAssignmentList(), assignmentId);
            if(pos!=-1)
                notifyItemRemoved(pos + 1);
        }
    }

    private int findAssignmentPosition(List<AssignmentData> list, long assignmentId){
        int pos = -1;

        for(int i = 0; i<list.size(); i++){
            if(list.get(i).getAssignmentId()==assignmentId)
                pos = i;
        }

        return pos;
    }

    //meant to be overridden
    protected int sortedInsert(AssignmentData assignmentData, MutableLiveData<List<AssignmentData>> list){
        return ListHelper.sortedInsert(assignmentData, list);
    }

    public void superNotifyDataSetChanged(){
        appModel.refreshSCompleteAssignmentList();
        appModel.refreshSIncompleteAssignmentList();
        //incompleteAssignmentList = appModel.getIncompleteAssignmentList();
        //completeAssignmentList = appModel.getCompleteAssignmentList();
        //Collections.sort(incompleteAssignmentList);
        //Collections.sort(completeAssignmentList);
        notifyDataSetChanged();
    }

    private void setPriorityByDate(AssignmentData assignmentData, AssignmentItemViewHolder viewHolder){
        Date date = assignmentData.getDueDate();
        viewHolder.priority = assignmentData.getPriority();
        //Date and priority
        if (date != null) {
            viewHolder.assignment_due_date_txt.setText(FormattingHelper.dateTimeFormat.format(date));

            Calendar c1 = Calendar.getInstance(), c2 = Calendar.getInstance();
            c1.setTime(date);
            c2.setTime(new Date(System.currentTimeMillis()));

            long diff = c1.getTimeInMillis() - c2.getTimeInMillis();
            float daysApart = ((float)diff)/ (24 * 60 * 60 * 1000);

            if(daysApart<0) viewHolder.priority = -1;
            else {
                switch ((int)daysApart){
                    case 0:
                        viewHolder.priority+=2;
                    case 1:
                        viewHolder.priority+=1;
                }
            }
        }
        else viewHolder.assignment_due_date_txt.setText("");
    }

    /*
     * ---------------------------- ViewHolders -----------------------------------
     */

    public static class AssignmentItemViewHolder extends RecyclerView.ViewHolder {
        public TextView assignment_name_txt, assignment_class_name_txt, assignment_due_date_txt;
        private final ImageView assignment_class_color;
        private final ColorStateList assignment_name_color, assignment_due_date_color;
        private long assignmentId;
        private int priority;

        public AssignmentItemViewHolder(@NonNull View itemView) {
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

    /*protected int sortedInsert(AssignmentData assignmentData, List<AssignmentData> list){
        int i = Collections.binarySearch(list, assignmentData);

        if (i < 0) {
            i = -i - 1;
        }
        list.add(i, assignmentData);

        return i;
    }*/

    /*public AssignmentListAdapter(Context ct, ApplicationViewModel model,
                                 MutableLiveData<List<AssignmentData>> incompleteList,
                                 MutableLiveData<List<AssignmentData>> completeList) {
        context = ct;
        appModel = model;
        sIncompleteAssignmentList = incompleteList;
        sCompleteAssignmentList = completeList;
    }*/

    /*public String deleteAssignment(long assignmentId){
        for(int i = 0; i < getCompleteAssignmentList().size(); i++){
            if(getCompleteAssignmentList().get(i).getAssignmentId()==assignmentId){
                AssignmentData assignmentData = FormattingHelper.removeAssignment(i, sCompleteAssignmentList);
                //notifyItemRemoved(i+incompleteAssignmentList.size()+2);
                return assignmentData.getAssignmentName();
            }
        }
        for(int i = 0; i < getIncompleteAssignmentList().size(); i++){
            if(getIncompleteAssignmentList().get(i).getAssignmentId()==assignmentId){
                AssignmentData assignmentData = FormattingHelper.removeAssignment(i, sIncompleteAssignmentList);
                //notifyItemRemoved(i+1);
                return assignmentData.getAssignmentName();
            }
        }

        return "No assignment found";
    }*/

/*
public boolean itemSwiped(AssignmentListViewHolder viewHolder){
        AssignmentData assignmentData = appModel.getAssignmentData(viewHolder.assignmentId);
        //int initialPos;

        if(assignmentData.isComplete()){
            assignmentData.setComplete(false);
            //initialPos = viewHolder.getBindingAdapterPosition()-2-getIncompleteAssignmentList().size();
            //removeAssignment(initialPos, sCompleteAssignmentList);
            ListHelper.removeAssignment(assignmentData, sCompleteAssignmentList);
            //completeAssignmentList.remove(initialPos);
            //appModel.sCompleteAssignmentList.setValue(completeAssignmentList);
            //pos = sortedInsert(assignmentData)+1;
        }
        else {
            assignmentData.setComplete(true);
            //initialPos = viewHolder.getBindingAdapterPosition()-1;
            //removeAssignment(initialPos, sIncompleteAssignmentList);
            ListHelper.removeAssignment(assignmentData, sIncompleteAssignmentList);
            //appModel.sIncompleteAssignmentList.setValue(incompleteAssignmentList);
            //pos = sortedInsert(assignmentData)+2+incompleteAssignmentList.size();
        }
        appModel.updateAssignment(assignmentData);
        notifyItemRemoved(viewHolder.getLayoutPosition());
        insertAssignment(assignmentData);
        appModel.assignmentListModified.setValue(this);

        return assignmentData.isComplete();
    }
 */
