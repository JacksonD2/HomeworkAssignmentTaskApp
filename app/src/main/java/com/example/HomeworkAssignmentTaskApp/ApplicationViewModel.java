package com.example.HomeworkAssignmentTaskApp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.data.AppRepository;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.ListHelper;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming.AssignmentListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationViewModel extends AndroidViewModel {
    private final AppRepository mRepository;
    private final LiveData<List<ClassData>> classList;
    private final LiveData<List<AssignmentData>> assignmentList, incompleteAssignmentList,
            completeAssignmentList;
    public final MutableLiveData<List<AssignmentData>>
            sIncompleteAssignmentList = new MutableLiveData<>(new ArrayList<>()),
            sCompleteAssignmentList = new MutableLiveData<>(new ArrayList<>());
    //data for observers
    private final MutableLiveData<Integer> selectedClassId = new MutableLiveData<>(-1),
            selectedPriority = new MutableLiveData<>(), deletedClassId = new MutableLiveData<>();
    private final MutableLiveData<Long> deletedAssignmentId = new MutableLiveData<>();
    private final MutableLiveData<Boolean> assignmentInserted = new MutableLiveData<>(false),
            assignmentListLoaded = new MutableLiveData<>(false),
            assignmentDeleted =  new MutableLiveData<>(false);
    public final MutableLiveData<AssignmentData> newAssignment = new MutableLiveData<>();
    public final MutableLiveData<AssignmentListAdapter> assignmentListModified = new MutableLiveData<>();

    public ApplicationViewModel(@NonNull Application application){
        super(application);
        mRepository = new AppRepository(application, assignmentListLoaded);
        classList = mRepository.getAllClasses();
        assignmentList = mRepository.getAllAssignments();
        incompleteAssignmentList = mRepository.getIncompleteAssignments();
        completeAssignmentList = mRepository.getCompleteAssignments();
    }

    /* ---------------------------------------------------------------------------
     * --------------------------- Class Methods ---------------------------------
     * ---------------------------------------------------------------------------
     */

    public LiveData<List<ClassData>> getClassList(){
        return classList;
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

    public int getClassId(int index) {
        List<ClassData> temp = getClassList().getValue();

        if (temp != null) return temp.get(index).getClassId();

        return 0;
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

    /* ---------------------- Class Fragment Communication ---------------------------- */

    public LiveData<Integer> getSelectedClassId() {
        return selectedClassId;
    }

    public void setSelectedClassId(int classId) {
        selectedClassId.setValue(classId);
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

    public LiveData<Boolean> getAssignmentListLoaded(){
        return assignmentListLoaded;
    }

    public void setAssignmentListLoaded(Boolean b){
        assignmentListLoaded.setValue(b);
    }

    public boolean isAssignmentListLoaded(){
        if(assignmentListLoaded.getValue()!=null){
            return assignmentListLoaded.getValue();
        }
        return false;
    }

    public LiveData<Boolean> getAssignmentDeleted(){
        return assignmentDeleted;
    }

    public void setAssignmentDeleted(Boolean b){
        assignmentDeleted.setValue(b);
    }

    public boolean isAssignmentDeleted(){
        if(assignmentDeleted.getValue()!=null){
            return assignmentDeleted.getValue();
        }
        return false;
    }

    /* ---------------------------------------------------------------------------------
     * --------------------------- Assignment Methods ----------------------------------
     * ---------------------------------------------------------------------------------
     */

    public LiveData<List<AssignmentData>> getAssignmentList(){
        return assignmentList;
    }

    public void insertAssignment(AssignmentData assignmentData) {
        mRepository.insertAssignment(assignmentData, this);
        //setAssignmentListModified(true);
        //newAssignment.setValue(assignmentData);
    }

    public void updateAssignment(AssignmentData assignmentData) {
        mRepository.updateAssignment(assignmentData);

        List<AssignmentData> temp = sIncompleteAssignmentList.getValue();
        for(int i=0; i<temp.size(); i++){
            if(temp.get(i).getAssignmentId() == assignmentData.getAssignmentId()){
                temp.set(i,assignmentData);
                sIncompleteAssignmentList.setValue(temp);
                return;
            }
        }

        temp = sCompleteAssignmentList.getValue();
        for(int i=0; i<temp.size(); i++){
            if(temp.get(i).getAssignmentId() == assignmentData.getAssignmentId()){
                temp.set(i,assignmentData);
                sCompleteAssignmentList.setValue(temp);
            }
        }
    }

    public void deleteAssignment(AssignmentData assignmentData) {
        mRepository.deleteAssignment(assignmentData);
        //assignmentListModified.setValue(true);
    }

    public String deleteAssignment(long assignmentId){
        for(int i = 0; i < sCompleteAssignmentList.getValue().size(); i++){
            if(sCompleteAssignmentList.getValue().get(i).getAssignmentId()==assignmentId){
                AssignmentData assignmentData = ListHelper.removeItem(i, sCompleteAssignmentList);
                //notifyItemRemoved(i+incompleteAssignmentList.size()+2);
                return assignmentData.getAssignmentName();
            }
        }
        for(int i = 0; i < sIncompleteAssignmentList.getValue().size(); i++){
            if(sIncompleteAssignmentList.getValue().get(i).getAssignmentId()==assignmentId){
                AssignmentData assignmentData = ListHelper.removeItem(i, sIncompleteAssignmentList);
                //notifyItemRemoved(i+1);
                return assignmentData.getAssignmentName();
            }
        }

        return "";
    }

    public LiveData<List<AssignmentData>> getIncompleteAssignmentList() {
        return incompleteAssignmentList;
    }

    public LiveData<List<AssignmentData>> getCompleteAssignmentList() {
        return completeAssignmentList;
    }

    public AssignmentData getAssignmentData(long id){
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

    public boolean isAssignment(long assignmentId){
        return assignmentList.getValue() != null &&
                assignmentList.getValue().stream().anyMatch(o -> o.getAssignmentId()==assignmentId);
        //return getAssignmentData(assignmentId)!=null;
    }

    public void refreshSCompleteAssignmentList(){
        if(completeAssignmentList.getValue()!=null) {
            List<AssignmentData> temp = completeAssignmentList.getValue();
            Collections.sort(temp);
            sCompleteAssignmentList.setValue(temp);
        }
    }

    public void refreshSIncompleteAssignmentList(){
        if(incompleteAssignmentList.getValue()!=null) {
            List<AssignmentData> temp = incompleteAssignmentList.getValue();
            Collections.sort(temp);
            sIncompleteAssignmentList.setValue(temp);
        }
    }

    /*public boolean isAssignment(long assignmentId){
        return mRepository.isAssignment(assignmentId);
    }*/

    /* ----------------------- Assignment Fragment Communication ----------------------------- */


    public LiveData<Integer> getSelectedPriority() {
        return selectedPriority;
    }

    public void setSelectedPriority(int priority){
        selectedPriority.setValue(priority);
    }

    public void setDeletedAssignmentId(long assignmentId){
        deletedAssignmentId.setValue(assignmentId);
        assignmentInserted.setValue(true);
    }

    public LiveData<Long> getDeletedAssignmentId(){
        return deletedAssignmentId;
    }

    public void deleteAssignmentById(){
        /*if(getDeletedClassId()!=null) {
            deleteClass(getClassData(getDeletedClassId().getValue()));
        }*/
        if(deletedAssignmentId.getValue()!=null) {
            mRepository.deleteAssignment(deletedAssignmentId.getValue());
        }
    }

    public void setAssignmentInserted(boolean b){
        assignmentInserted.setValue(b);
    }

    public LiveData<Boolean> getAssignmentInserted(){
        return assignmentInserted;
    }

    public boolean isAssignmentInserted(){
        if(assignmentInserted.getValue()!=null) {
            return assignmentInserted.getValue();
        }
        return false;
    }
}
