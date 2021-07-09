package com.example.HomeworkAssignmentTaskApp.ui.assignments.Classes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.FormattingHelper;
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;

import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ClassListViewHolder>{

    //private ArrayList<ClassObject> classList;
    private Context context;
    //private MutableLiveData<ArrayList<ClassObject>> mClassList;
    //AssignmentsViewModel viewModel;
    private ApplicationViewModel appModel;

    /*public ClassListAdapter(Context ct, ArrayList<ClassObject> list){
        context = ct;
        classList = list;
    }

    public ClassListAdapter(Context ct, MutableLiveData<ArrayList<ClassObject>> list) {
        context = ct;
        mClassList = list;
    }

    public ClassListAdapter(Context ct, AssignmentsViewModel model) {
        context = ct;
        viewModel = model;
    }*/

    public ClassListAdapter(Context ct, ApplicationViewModel model) {
        context = ct;
        appModel = model;
    }

    @NonNull
    @Override
    public ClassListAdapter.ClassListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.class_row, parent, false);
        return new ClassListAdapter.ClassListViewHolder(view);
    }

    /*@Override
    public void onBindViewHolder(@NonNull ClassListAdapter.ClassListViewHolder holder, int position) {
        holder.class_name_txt.setText(classList.get(position).getCourseName());
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }*/

    @Override
    public void onBindViewHolder(@NonNull ClassListAdapter.ClassListViewHolder holder, int position) {
        //holder.class_name_txt.setText(viewModel.getClassList().getValue().get(position).getCourseName());
        //holder.class_name_txt.setText(viewModel2.getClassList()..get(position).getCourseName());
        ClassData currentClass = Objects.requireNonNull(appModel.getClassList().getValue()).get(position);
        holder.formatClass(currentClass);
        formatDate(holder, currentClass);

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(FormattingHelper.CLASS_ID, currentClass.getClassId());
            //bundle.putInt("tab", 1);
            Navigation.findNavController(view).navigate(R.id.action_get_class_info, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(appModel.getClassList().getValue()).size();
    }

    private void formatDate(ClassListAdapter.ClassListViewHolder holder,  ClassData currentClass){
        Date startDate = currentClass.getStartDate(), endDate = currentClass.getEndDate(),
                currentDate = new Date(System.currentTimeMillis());

        DateFormat dateFormat = FormattingHelper.setDateFormat;
        if(startDate==null){
            if(endDate==null){
                holder.class_date_txt.setText(R.string.blank);
            }
            else if (currentDate.compareTo(endDate) > 0){
                holder.class_date_txt.setText(context.getString(R.string.date_ended_on, dateFormat.format(endDate)));
            }
            else {
                holder.class_date_txt.setText(context.getString(R.string.date_ends_on, dateFormat.format(endDate)));
            }
        }
        else if(endDate==null){
            if (currentDate.compareTo(startDate) > 0) {
                holder.class_date_txt.setText(context.getString(R.string.date_started_on, dateFormat.format(startDate)));
            }
            else {
                holder.class_date_txt.setText(context.getString(R.string.date_starts_on, dateFormat.format(startDate)));
            }
        }
        else if (currentDate.compareTo(startDate) > 0) {
            if (currentDate.compareTo(endDate) > 0){
                holder.class_date_txt.setText(context.getString(R.string.date_ended_on, dateFormat.format(endDate)));
            }
            else {
                holder.class_date_txt.setText(context.getString(R.string.date_ends_on, dateFormat.format(endDate)));
            }
        }
        else {
            holder.class_date_txt.setText(context.getString(R.string.date_starts_on, dateFormat.format(startDate)));
        }
    }


    public static class ClassListViewHolder extends RecyclerView.ViewHolder{
        public final TextView class_name_txt, class_date_txt, class_instructor_txt;
        public final ImageView class_color;

        public ClassListViewHolder(@NonNull View itemView) {
            super(itemView);
            class_name_txt = itemView.findViewById(R.id.class_name_txt);
            class_date_txt = itemView.findViewById(R.id.class_date_txt);
            class_color = itemView.findViewById(R.id.class_color);
            class_instructor_txt = itemView.findViewById(R.id.class_instructor_txt);
        }

        public void formatClass(ClassData classData){
            class_name_txt.setText(classData.getClassName());
            if(classData.getInstructorName()!=null) {
                class_instructor_txt.setText(classData.getInstructorName());
            }
            if(classData.getClassColor()!=0){
                class_color.setBackgroundColor(classData.getClassColor());
            }
        }

        /*public void bind(String text) {
            class_name_txt.setText(text);
        }

        ClassListViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.class_row, parent, false);
            return new ClassListViewHolder(view);
        }*/
    }
}

        /*if((startDate==null || currentDate.compareTo(startDate) > 0 ) && endDate!=null){
            holder.class_date_txt.setText(dateFormat.format(endDate));
        }
        else if (startDate!=null){
            holder.class_date_txt.setText(dateFormat.format(startDate));
        }
        else {
            holder.class_date_txt.setText("");
        }*/
        /*if(startDate==null){
            if(endDate==null){
                holder.class_date_txt.setText("");
            }
            else if (currentDate.compareTo(endDate) > 0){
                holder.class_date_txt.setText(context.getString(R.string.date_ended_on, dateFormat.format(endDate)));
            }
            else {
                holder.class_date_txt.setText(context.getString(R.string.date_ends_on, dateFormat.format(endDate)));
            }
        }
        else if (currentDate.compareTo(startDate) > 0){*/

        /*if(endDate==null) {
            if(startDate==null) {
                holder.class_date_txt.setText("");
            }
            else if (currentDate.compareTo(startDate) > 0) {
                holder.class_date_txt.setText(context.getString(R.string.date_started_on, dateFormat.format(startDate)));
            }
            else {
                holder.class_date_txt.setText(dateFormat.format(startDate));
            }
        }
        else if (currentDate.compareTo(endDate) < 0){
            holder.class_date_txt.setText(context.getString(R.string.date_ends_on, dateFormat.format(endDate)));
        }
        else {
            holder.class_date_txt.setText(context.getString(R.string.date_ended_on, dateFormat.format(endDate)));
        }
            /*
        }
        else {
            holder.class_date_txt.setText(dateFormat.format(startDate));
        }*/