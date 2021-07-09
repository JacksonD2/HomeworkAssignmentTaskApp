package com.example.HomeworkAssignmentTaskApp.ui.assignments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.HomeworkAssignmentTaskApp.R;
import com.google.android.material.color.MaterialColors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

// Used for formatting text, views and string identifiers
public class FormattingHelper {
    public static final DateFormat setDateFormat = new SimpleDateFormat("M/d/yyyy", Locale.US),
            showDateFormat = new SimpleDateFormat("EEE, MMM d", Locale.US),
            timeFormat = new SimpleDateFormat("h:mm a", Locale.US),
            dateTimeFormat = new SimpleDateFormat("h:mm a\nEEE, MMM d", Locale.US);
    public static final String ASSIGNMENT_ID = "assignment_id", CLASS_ID = "class_id";

    public static void setPriority(TextView p, int priority){
        String[] priorities = p.getResources().getStringArray(R.array.priorities);
        if(priority>=0 && priority<priorities.length){
            p.setText(priorities[priority]);
            setPriorityColor(p, priority);
        }
        else {
            p.setText(p.getResources().getString(R.string.data_error));
        }
    }

    public static void setPriority(TextView p, int priority, ColorStateList colorStateList){
        String[] priorities = p.getResources().getStringArray(R.array.priorities);
        if(priority>=0 && priority<priorities.length){
            p.setText(priorities[priority]);
            setPriorityColor(p, priority, colorStateList);
        }
        else {
            p.setText(p.getResources().getString(R.string.data_error));
        }
    }

    public static void setPriorityColor(TextView p, int priority){
        if(priority!=0) {
            setPriorityColor(p, priority, null);
        }
    }

    public static void setPriorityColor(TextView p, int priority, ColorStateList colorStateList){
        switch (priority){
            case -1:
                p.setTextColor(ContextCompat.getColor(p.getContext(), R.color.max));
                break;
            case 0: //does not change
                //p.setTextColor(MaterialColors.getColor(p, colorAttr, Color.BLACK));
                //p.setTextColor(colorAttr);
                p.setTextColor(colorStateList);
                break;
            case 1:
                p.setTextColor(ContextCompat.getColor(p.getContext(), R.color.medium));
                break;
            case 2:
                p.setTextColor(ContextCompat.getColor(p.getContext(), R.color.high));
                break;
            default:
                p.setTextColor(ContextCompat.getColor(p.getContext(), R.color.very_high));
                break;
        }
    }

}
