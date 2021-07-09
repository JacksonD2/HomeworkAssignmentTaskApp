package com.example.HomeworkAssignmentTaskApp.ui.assignments;

import androidx.lifecycle.MutableLiveData;

import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;

import java.util.Collections;
import java.util.List;

//Methods used for organizing lists
public class ListHelper{
    public static <T extends Comparable<T>> int sortedInsert(List<T> list, T key){
        int i = Collections.binarySearch(list, key);

        if (i < 0) {
            i = -i - 1;
        }
        list.add(i, key);

        return i;
    }

    public static <T extends Comparable<T>> int sortedInsert(T key, MutableLiveData<List<T>> list){
        List<T> temp = list.getValue();
        int i = sortedInsert(temp, key);
        list.setValue(temp);
        return i;
    }

    public static <T extends Comparable<T>> T removeItem(int pos, MutableLiveData<List<T>> list){
        List<T> temp = list.getValue();
        assert temp != null;
        T removed = temp.remove(pos);
        list.setValue(temp);
        return removed;
    }

    public static void removeAssignment(AssignmentData assignmentData, MutableLiveData<List<AssignmentData>> list){
        List<AssignmentData> temp = list.getValue();

        if(temp!=null) {
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).getAssignmentId() == assignmentData.getAssignmentId()) {
                    temp.remove(i);
                    break;
                }
            }

            list.setValue(temp);
        }
    }
}
