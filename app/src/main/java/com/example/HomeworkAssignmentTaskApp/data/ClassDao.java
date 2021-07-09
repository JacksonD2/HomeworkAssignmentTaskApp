package com.example.HomeworkAssignmentTaskApp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClassDao {
    @Query("Select * from classes")
    LiveData<List<ClassData>> getAllClasses();

    /*@Query("Select * from classes")
    LiveData<List<ClassData>> getLiveData();*/

    @Query("DELETE FROM classes")
    void deleteAll();

    /*@Query("")
    List<ClassData> loadAllByIds(int[] userIds);*/

    @Insert
    void insertAll(ClassData... users);

    @Insert
    long insertClass(ClassData classData);

    @Update
    void updateClass(ClassData classData);

    @Delete
    void deleteClass(ClassData classData);

    @Delete(entity = ClassData.class)
    void deleteClass(ClassId... classId);

    class ClassId {
        public int classId;
    }
}
