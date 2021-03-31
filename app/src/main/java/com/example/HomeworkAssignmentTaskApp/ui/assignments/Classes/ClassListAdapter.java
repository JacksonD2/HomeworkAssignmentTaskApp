package com.example.HomeworkAssignmentTaskApp.ui.assignments.Classes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.deprecated.ClassObject;

import java.util.ArrayList;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ClassListViewHolder>{

    //private ArrayList<ClassObject> classList;
    private Context context;
    private MutableLiveData<ArrayList<ClassObject>> mClassList;
    //AssignmentsViewModel viewModel;
    ApplicationViewModel viewModel2;

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
        viewModel2 = model;
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
        holder.class_name_txt.setText(viewModel2.getClassList().getValue().get(position).getClassName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putInt("tab", 1);
                Navigation.findNavController(view).navigate(R.id.action_get_class_info, bundle);
                //Navigation.findNavController(view).navigate(R.id.action_get_class_info);
            }
        });
    }

    @Override
    public int getItemCount() {
        //return viewModel.getClassList().getValue().size();
        return viewModel2.getClassList().getValue().size();
    }

    public class ClassListViewHolder extends RecyclerView.ViewHolder{
        private final TextView class_name_txt;

        public ClassListViewHolder(@NonNull View itemView) {
            super(itemView);
            class_name_txt = itemView.findViewById(R.id.class_name_txt);
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
