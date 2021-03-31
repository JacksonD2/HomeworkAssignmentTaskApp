package com.example.HomeworkAssignmentTaskApp.data;

import androidx.annotation.NonNull;
import androidx.room.*;

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
    private String className;

    @ColumnInfo(name = "instructorName")
    private String instructorName;

    @ColumnInfo(name = "startDate")
    private Date startDate;

    @ColumnInfo(name = "endDate")
    private Date endDate;

    @ColumnInfo(name = "classColor")
    private String classColor;


    public ClassData(){}

    @Ignore
    public ClassData(String name){
        //classId = id;
        className = name;
    }

    @Ignore
    public ClassData(String name, Date start, Date end){
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

    @NonNull
    public void setClassName(String name){
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

    public void setClassColor(String color){
        classColor = color;
    }
    public String getClassColor(){
        return classColor;
    }
}
