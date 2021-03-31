package com.example.HomeworkAssignmentTaskApp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssignmentDao {
    @Query("Select * from assignments")
    LiveData<List<AssignmentData>> getAllAssignments();

    @Query("DELETE FROM assignments")
    void deleteAll();

    @Insert
    void insertAll(AssignmentData... assignments);

    @Insert
    void insertAssignment(AssignmentData assignmentData);

    @Update
    void updateAssignment(AssignmentData assignmentData);

    @Delete
    void deleteAssignment(AssignmentData assignmentData);
}
