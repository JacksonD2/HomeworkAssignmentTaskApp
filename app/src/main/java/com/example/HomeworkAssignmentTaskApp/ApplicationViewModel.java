package com.example.HomeworkAssignmentTaskApp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.data.AppRepository;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ApplicationViewModel extends AndroidViewModel {
    private final AppRepository mRepository;
    private final LiveData<List<ClassData>> classList;
    private final LiveData<List<AssignmentData>> assignmentList, incompleteAssignmentList, completeAssignmentList;
    private final MutableLiveData<Integer> selectedClassId = new MutableLiveData<>(), selectedPriority = new MutableLiveData<>(), deletedClassId = new MutableLiveData<>();
    public static final DateFormat setDateFormat = new SimpleDateFormat("M/d/yyyy", Locale.US);

    public ApplicationViewModel(@NonNull Application application){
        super(application);
        mRepository = new AppRepository(application);
        classList = mRepository.getAllClasses();
        assignmentList = mRepository.getAllAssignments();
        incompleteAssignmentList = mRepository.getIncompleteAssignments();
        completeAssignmentList = mRepository.getCompleteAssignments();
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

    public void deleteClass(ClassData classData){
        mRepository.deleteClass(classData);
    }

    public void insertAssignment(AssignmentData assignmentData) {
        mRepository.insertAssignment(assignmentData);
    }

    public void updateAssignment(AssignmentData assignmentData) {
        mRepository.updateAssignment(assignmentData);
    }

    public LiveData<List<AssignmentData>> getIncompleteAssignmentList() {
        return incompleteAssignmentList;
        //return assignmentList;
    }

    public LiveData<List<AssignmentData>> getCompleteAssignmentList() {
        return completeAssignmentList;
    }

    public ClassData getClassData(int id){
        List<ClassData> temp = getClassList().getValue();

        if(temp!=null) {
            for (ClassData classData : temp) {
                if (classData.getClassId() == id) {
                    return classData;
                }
            }
        }

        return null;
    }

    public AssignmentData getAssignmentData(int id){
        List<AssignmentData> temp = getAssignmentList().getValue();

        if(temp!=null) {
            for (AssignmentData assignmentData : temp) {
                if (assignmentData.getAssignmentId() == id) {
                    return assignmentData;
                }
            }
        }

        return null;
    }

    public int getClassId(int index) {
        List<ClassData> temp = getClassList().getValue();

        if (temp != null) {
            return temp.get(index).getClassId();
        }

        return 0;
    }

    public LiveData<Integer> getSelectedClassId() {
        return selectedClassId;
    }

    public void setSelectedClassId(int classId) {
        selectedClassId.setValue(classId);
    }

    public LiveData<Integer> getSelectedPriority() {
        return selectedPriority;
    }

    public void setSelectedPriority(int priority){
        selectedPriority.setValue(priority);
    }

    public void setDeletedClassId(int classId){
        deletedClassId.setValue(classId);
    }

    public LiveData<Integer> getDeletedClassId(){
        return deletedClassId;
    }

    public void deleteClassById(){
        /*if(getDeletedClassId()!=null) {
            deleteClass(getClassData(getDeletedClassId().getValue()));
        }*/
        if(deletedClassId.getValue()!=null) {
            mRepository.deleteClass(deletedClassId.getValue());
        }
    }

    /*public void deleteClassById(int classId){
        ClassData classData = getClassData(classId);
        if(classData!=null) {
            deleteClass(classData);
        }
    }*/
}
