package com.example.HomeworkAssignmentTaskApp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.data.ClassRepository;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;

import java.util.List;

public class ApplicationViewModel extends AndroidViewModel {
    private ClassRepository mRepository;
    private final LiveData<List<ClassData>> classList;
    private final LiveData<List<AssignmentData>> assignmentList;

    public ApplicationViewModel(@NonNull Application application){
        super(application);
        mRepository = new ClassRepository(application);
        classList = mRepository.getAllClasses();
        assignmentList = mRepository.getAllAssignments();
    }

    public LiveData<List<ClassData>> getClassList(){
        return classList;
    }

    public LiveData<List<AssignmentData>> getAssignmentList(){
        return assignmentList;
    }

    public void insertClass(ClassData classData){
        mRepository.insertClass(classData);
    }

    public void updateClass(ClassData classData){
        mRepository.updateClass(classData);
    }

    public void insertAssignment(AssignmentData assignmentData) {
        mRepository.insertAssignment(assignmentData);
    }

    public void updateAssignment(AssignmentData assignmentData) {
        mRepository.updateAssignment(assignmentData);
    }

}
