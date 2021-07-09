package com.example.HomeworkAssignmentTaskApp.data;

import androidx.annotation.NonNull;
import androidx.room.*;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Entity(tableName = "classes")
public class ClassData {
    /*@PrimaryKey(autoGenerate = true)
    public int classId;
    @ColumnInfo(name = "className")
    public String className;*/

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "classId")
    private int classId;

    @NonNull
    @ColumnInfo(name = "className")
    private String className = "";

    @ColumnInfo(name = "instructorName")
    private String instructorName;

    @ColumnInfo(name = "startDate")
    private Date startDate;

    @ColumnInfo(name = "endDate")
    private Date endDate;

    @ColumnInfo(name = "classColor")
    private int classColor = 0;


    public ClassData(){}

    @Ignore
    public ClassData(@NotNull String name){
        //classId = id;
        className = name;
    }

    @Ignore
    public ClassData(@NotNull String name, Date start, Date end){
        className = name;
        startDate = start;
        endDate = end;
    }

    public void setClassId(int id){
        classId = id;
    }
    public int getClassId(){
        return classId;
    }

    public void setClassName(@NotNull String name){
        className = name;
    }
    @NonNull
    public String getClassName(){
        return className;
    }

    public void setInstructorName(String name){
        instructorName = name;
    }
    public String getInstructorName(){
        return instructorName;
    }

    public void setStartDate(Date date){
        startDate = date;
    }
    public Date getStartDate(){
        return startDate;
    }

    public void setEndDate(Date date){
        endDate = date;
    }
    public Date getEndDate(){
        return endDate;
    }

    public void setClassColor(int color){
        classColor = color;
    }
    public int getClassColor(){
        return classColor;
    }

    @NotNull
    @Override
    public java.lang.String toString() {
        return getClassName();
    }
}
