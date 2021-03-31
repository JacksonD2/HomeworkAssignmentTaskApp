package com.example.HomeworkAssignmentTaskApp.deprecated;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.example.HomeworkAssignmentTaskApp.data.ClassData;

import java.util.List;

@Dao
public interface OldClassDao {
    @Query("Select * from classes")
    LiveData<List<ClassData>> getAllClasses();

    /*@Query("")
    List<ClassData> loadAllByIds(int[] userIds);*/

    @Insert
    void insertAll(ClassData... users);

    @Insert
    void insertClass(ClassData classData);

    @Update
    void updateClass(ClassData classData);

    @Delete
    void deleteClass(ClassData classData);
}
