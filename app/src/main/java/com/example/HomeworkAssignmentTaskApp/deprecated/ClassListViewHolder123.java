package com.example.HomeworkAssignmentTaskApp.deprecated;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HomeworkAssignmentTaskApp.R;

public class ClassListViewHolder123 extends RecyclerView.ViewHolder{
    private final TextView class_name_txt;

    public ClassListViewHolder123(@NonNull View itemView) {
        super(itemView);
        class_name_txt = itemView.findViewById(R.id.class_name_txt);
    }

    public void bind(String text) {
        class_name_txt.setText(text);
    }

    static ClassListViewHolder123 create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_row, parent, false);
        return new ClassListViewHolder123(view);
    }
}
