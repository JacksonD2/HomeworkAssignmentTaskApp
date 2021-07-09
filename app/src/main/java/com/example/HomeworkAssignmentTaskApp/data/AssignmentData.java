package com.example.HomeworkAssignmentTaskApp.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Entity(tableName = "assignments")
public class AssignmentData implements Comparable<AssignmentData> {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "assignmentId")
    private long assignmentId;

    @NonNull
    @ColumnInfo(name = "assignmentName")
    private String assignmentName = "";

    @ColumnInfo(name = "priority")
    private int priority;

    @ColumnInfo(name = "dueDate")
    private Date dueDate;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "classId")
    private int classId;

    @ColumnInfo(name = "isComplete")
    private boolean isComplete;

    public AssignmentData(){}

    @Ignore
    public AssignmentData(@NotNull String name){
        assignmentName = name;
        classId = -1;
        isComplete = false;
        priority = 0;
    }

    @Ignore
    public AssignmentData(@NotNull String name, int id){
        assignmentName = name;
        classId = id;
        isComplete = false;
        priority = 0;
    }

    //getters and setters

    public long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(long assignmentId) {
        this.assignmentId = assignmentId;
    }

    @NotNull
    public String getAssignmentName(){
        return assignmentName;
    }

    public void setAssignmentName(@NonNull String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    @Override
    public int compareTo(AssignmentData assignmentData) {
        if (getDueDate() == null || assignmentData.getDueDate() == null)
            return 0;
        return getDueDate().compareTo(assignmentData.getDueDate());
    }
}
