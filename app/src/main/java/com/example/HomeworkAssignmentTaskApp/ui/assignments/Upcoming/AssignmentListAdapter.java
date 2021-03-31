package com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming;

import android.content.Context;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AssignmentListAdapter extends RecyclerView.Adapter<AssignmentListAdapter.AssignmentListViewHolder> {

    //String data1[], data2[];
    //int images[];
    ArrayList<AssignmentData> assignmentList;
    ApplicationViewModel appModel;
    Context context;

    public AssignmentListAdapter(Context ct, String[] s1, String[] s2, int[] img){
        context = ct;
        //data1 = s1;
       // data2 = s2;
        //images = img;
    }
    public AssignmentListAdapter(Context ct, ApplicationViewModel model) {
        context = ct;
        appModel = model;
        if(appModel.getAssignmentList().getValue()==null){
            appModel.getAssignmentList();
        }
    }

    @NonNull
    @Override
    public AssignmentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.assignment_row, parent, false);
        return new AssignmentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentListViewHolder holder, int position) {
        //holder.task_txt.setText(data1[position]);
        //holder.description_txt.setText(data2[position]);
        //holder.myImage.setImageResource(images[position]);
        holder.assignment_name_txt.setText(appModel.getAssignmentList().getValue().get(position).getAssignmentName());
        //holder.assignment_class_name_txt.setText(appModel.getAssignmentList().getValue().get(position).get);
        Date date = appModel.getAssignmentList().getValue().get(position).getDueDate();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        if(date!=null) {
            holder.assignment_due_date_txt.setText(dateFormat.format(date));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putInt("tab", 0);
                Navigation.findNavController(view).navigate(R.id.action_get_assignment_info, bundle);
                //Navigation.findNavController(view).navigate(R.id.action_get_class_info);
            }
        });
    }

    @Override
    public int getItemCount() {
        //return data1.length;
        return appModel.getAssignmentList().getValue().size();
        //return 2;
    }

    public class AssignmentListViewHolder extends RecyclerView.ViewHolder{
        TextView assignment_name_txt, assignment_class_name_txt, assignment_due_date_txt;
        //ImageView myImage;

        public AssignmentListViewHolder(@NonNull View itemView) {
            super(itemView);
            assignment_name_txt = itemView.findViewById(R.id.assignment_name_txt);
            assignment_class_name_txt = itemView.findViewById((R.id.assignment_class_name_txt));
            assignment_due_date_txt = itemView.findViewById((R.id.assignment_due_date_txt));
            //myImage = itemView.findViewById(R.id.imageView);
        }
    }
}

