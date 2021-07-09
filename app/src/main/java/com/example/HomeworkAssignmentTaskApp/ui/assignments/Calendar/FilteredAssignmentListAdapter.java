package com.example.HomeworkAssignmentTaskApp.ui.assignments.Calendar;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;
import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.ListHelper;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.Upcoming.AssignmentListAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FilteredAssignmentListAdapter extends AssignmentListAdapter {
    private List<AssignmentData> incompleteAssignmentList, completeAssignmentList;

    public FilteredAssignmentListAdapter(Context ct, ApplicationViewModel model,
                                         List<AssignmentData> incompleteList, List<AssignmentData> completeList) {
        super(ct, model);
        incompleteAssignmentList = incompleteList;
        completeAssignmentList = completeList;
    }

    @Override
    protected List<AssignmentData> getIncompleteAssignmentList() {
        return incompleteAssignmentList;
    }

    @Override
    protected List<AssignmentData> getCompleteAssignmentList() {
        return completeAssignmentList;
    }

    @Override
    protected void removeAssignment(AssignmentData assignmentData, @NotNull MutableLiveData<List<AssignmentData>> list) {
        super.removeAssignment(assignmentData, list);
        List<AssignmentData> temp;

        if(assignmentData.isComplete()) temp = incompleteAssignmentList;
        else temp = completeAssignmentList;

        for (int i = 0; i<temp.size(); i++){
            if(temp.get(i).getAssignmentId()==assignmentData.getAssignmentId()){
                temp.remove(i);
                break;
            }
        }
    }

    @Override
    protected int sortedInsert(AssignmentData assignmentData, MutableLiveData<List<AssignmentData>> list) {
        super.sortedInsert(assignmentData, list);
        int pos;

        if(list==appModel.sCompleteAssignmentList)
            pos = ListHelper.sortedInsert(completeAssignmentList, assignmentData);
        else
            pos = ListHelper.sortedInsert(incompleteAssignmentList, assignmentData);

        return pos;
    }

    public void superNotifyDataSetChanged(List<AssignmentData> incompleteList,
                                          List<AssignmentData> completeList) {
        incompleteAssignmentList = incompleteList;
        completeAssignmentList = completeList;
        notifyDataSetChanged();
    }
}
    /*@Override
    public boolean itemSwiped(AssignmentListViewHolder viewHolder) {
        boolean b = super.itemSwiped(viewHolder);
        //refreshAssignmentList();
        //notifyDataSetChanged();

        return b;
    }*/

    /*@Override
    protected void insertAssignment(AssignmentData assignmentData) {
        super.insertAssignment(assignmentData);
        /*int pos;

        if (assignmentData.isComplete()){
            ListHelper.sortedInsert(assignmentData, sCompleteAssignmentList);
            pos = 2 + getIncompleteAssignmentList().size() +
                    ListHelper.sortedInsert(completeAssignmentList, assignmentData);
        }
        else {
            ListHelper.sortedInsert(assignmentData, sIncompleteAssignmentList);
            pos = 1 + ListHelper.sortedInsert(incompleteAssignmentList, assignmentData);
        }

        notifyItemInserted(pos);
    }*/

    /*@Override
    public void insertAssignment(AssignmentData assignmentData) {
        //super.insertAssignment(assignmentData);
        int pos;

        if (assignmentData.isComplete()){
            pos = 2 + getIncompleteAssignmentList().size() +
                    sortedInsert(assignmentData, completeAssignmentList);
            sortedInsert(assignmentData, sCompleteAssignmentList);
        }
        else {
            pos = 1 + sortedInsert(assignmentData, incompleteAssignmentList);
            sortedInsert(assignmentData, sIncompleteAssignmentList);
        }

        notifyItemInserted(pos);
        refreshAssignmentList();
    }

    @Override
    protected AssignmentData removeAssignment(int pos, MutableLiveData<List<AssignmentData>> list) {
        AssignmentData assignmentData = super.removeAssignment(pos, list);

        if(list==sCompleteAssignmentList) completeAssignmentList.remove(assignmentData);
        else if(list == sIncompleteAssignmentList) incompleteAssignmentList.remove(assignmentData);

        return assignmentData;
    }

    @Override
    public String deleteAssignment(long assignmentId) {
        String s = super.deleteAssignment(assignmentId);
        refreshAssignmentList();
        return s;
    }*/
