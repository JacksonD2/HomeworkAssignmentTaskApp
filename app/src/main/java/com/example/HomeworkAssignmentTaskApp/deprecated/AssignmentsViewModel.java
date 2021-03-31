package com.example.HomeworkAssignmentTaskApp.deprecated;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class AssignmentsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ClassObject>> classList;

    public AssignmentsViewModel(){
        super();
    }

    /*public AssignmentsViewModel(@NonNull Application application){
        super(application);
    }*/

    public void setClassList(ArrayList<ClassObject> list){
        classList = new MutableLiveData<ArrayList<ClassObject>>();
        classList.setValue(list);
    }

    public MutableLiveData<ArrayList<ClassObject>> getClassList(){
        return classList;
    }

}
