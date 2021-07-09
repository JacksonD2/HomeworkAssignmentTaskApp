package com.example.HomeworkAssignmentTaskApp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kotlinx.coroutines.flow.Flow;

@Dao
public interface AssignmentDao {
    @Query("Select * from assignments")
    LiveData<List<AssignmentData>> getAllAssignments();

    @Query("Select * from assignments Where isComplete = 0")
    LiveData<List<AssignmentData>> getIncompleteAssignments();

    @Query("Select * from assignments Where isComplete = 1")
    LiveData<List<AssignmentData>> getCompleteAssignments();

    @Query("Select * from assignments Where assignmentId = :id Limit 1")
    AssignmentData getAssignmentById(long id);

    /*@Query("Select assignmentId from assignments")
    LiveData<int[]> getAssignmentIds();*/

    /*@Query("SELECT EXISTS(SELECT 1 FROM assignments WHERE assignmentId = :id)")
    LiveData<Boolean> isAssignment(long id);*/

    @Query("DELETE FROM assignments")
    void deleteAll();

    @Insert
    void insertAll(AssignmentData... assignments);

    @Insert
    long insertAssignment(AssignmentData assignmentData);

    @Update
    void updateAssignment(AssignmentData assignmentData);

    @Delete
    void deleteAssignment(AssignmentData assignmentData);

    @Delete(entity = AssignmentData.class)
    void deleteAssignment(AssignmentId... assignmentIds);

    /*// RX Java
    @Query("Select * from assignments")
    Flowable<List<AssignmentData>> getItemList();*/

    class AssignmentId {
        public long assignmentId;
    }
}
