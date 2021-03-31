package com.example.HomeworkAssignmentTaskApp.deprecated;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.HomeworkAssignmentTaskApp.data.AppDatabase;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;

import java.util.List;

public class OldApplicationViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private LiveData<List<ClassData>> classList;

    /*public ApplicationViewModel(){
        super();
    }*/

    public OldApplicationViewModel(@NonNull Application application){
        super(application);
        //appDatabase = AppDatabase.getInstance(application);
        //classList = appDatabase.classDao().getAllClasses();
    }

    /*public void setClassList(ArrayList<ClassData> list){
        classList = new LiveData<List<ClassData>>();
        classList.setValue(list);
    }*/

    /*public LiveData<List<ClassData>> getClassList(){
        return classList;
    }

    public void updateClassList(){
        classList = appDatabase.classDao().getAllClasses();
    }

    public void insertNewClass(ClassData classData){
        appDatabase.classDao().insertClass(classData);
    }*/

}
