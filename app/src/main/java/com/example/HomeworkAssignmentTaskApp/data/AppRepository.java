package com.example.HomeworkAssignmentTaskApp.data;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class AppRepository {

    private ClassDao mClassDao;
    private AssignmentDao mAssignmentDao;
    private LiveData<List<AssignmentData>> mAllAssignments, mIncompleteAssignments, mCompleteAssignments;
    private LiveData<List<ClassData>> mAllClasses;
    private MutableLiveData<Boolean> assignmentListLoaded;
    private AppDatabase db;
    //private LiveData<int[]> assignmentIds;
    //private final MutableLiveData<Boolean> assignmentListModified;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public AppRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        mClassDao = db.classDao();
        mAssignmentDao = db.assignmentDao();
        mAllClasses = mClassDao.getAllClasses();
        mAllAssignments = mAssignmentDao.getAllAssignments();
        mIncompleteAssignments = mAssignmentDao.getIncompleteAssignments();
        mCompleteAssignments = mAssignmentDao.getCompleteAssignments();
    }

    public AppRepository (Application application, MutableLiveData<Boolean> assignmentListLoaded) {
        db = AppDatabase.getInstance(application);
        mClassDao = db.classDao();
        mAssignmentDao = db.assignmentDao();
        mAllClasses = mClassDao.getAllClasses();
        mAllAssignments = mAssignmentDao.getAllAssignments();
        mIncompleteAssignments = mAssignmentDao.getIncompleteAssignments();
        mCompleteAssignments = mAssignmentDao.getCompleteAssignments();
        this.assignmentListLoaded = assignmentListLoaded;
        //assignmentIds = mAssignmentDao.getAssignmentIds();
        //this.assignmentListModified = assignmentListModified;
    }

    public LiveData<List<ClassData>> getAllClasses() {
        return mAllClasses;
    }

    public LiveData<List<AssignmentData>> getAllAssignments() {
        return mAllAssignments;
    }

    public LiveData<List<AssignmentData>> getIncompleteAssignments() {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        executor.execute(() -> {
            if (mIncompleteAssignments.getValue() == null){
                //noinspection StatementWithEmptyBody
                while (mIncompleteAssignments.getValue() == null || mAllClasses.getValue() == null);
                //waits until database is instantiated
                mainThreadHandler.post(() -> assignmentListLoaded.setValue(true));
            }
        } );
        return mIncompleteAssignments;
    }

    public LiveData<List<AssignmentData>> getCompleteAssignments() {
        return mCompleteAssignments;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertClass(ClassData classData) {
        AppDatabase.databaseWriteExecutor.execute(() -> mClassDao.insertClass(classData));
    }

    public void updateClass(ClassData classData) {
        AppDatabase.databaseWriteExecutor.execute(() -> mClassDao.updateClass(classData));
    }

    public void deleteClass(ClassData classData) {
        AppDatabase.databaseWriteExecutor.execute(() -> mClassDao.deleteClass(classData));
    }

    public void deleteClass(int classId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            ClassDao.ClassId temp = new ClassDao.ClassId();
            temp.classId = classId;
            mClassDao.deleteClass(temp);
        });
    }

    public void insertAssignment(AssignmentData assignmentData, ApplicationViewModel viewModel) {
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            long rowId = mAssignmentDao.insertAssignment(assignmentData);
            //AssignmentData assignmentInfo = getAssignment(rowId);

            mainThreadHandler.post(() -> {
                Log.d(null, "rowId is: " + rowId);
                viewModel.setAssignmentInserted(true);
                //viewModel.newAssignment.setValue(assignmentInfo);
                assignmentData.setAssignmentId(rowId);
                viewModel.newAssignment.setValue(assignmentData);
            });
        });
    }

    public void updateAssignment(AssignmentData assignmentData) {
        AppDatabase.databaseWriteExecutor.execute(() -> mAssignmentDao.updateAssignment(assignmentData));
    }

    public void deleteAssignment(AssignmentData assignmentData) {
        AppDatabase.databaseWriteExecutor.execute(() -> mAssignmentDao.deleteAssignment(assignmentData));
    }

    public void deleteAssignment(int assignmentId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            AssignmentDao.AssignmentId temp = new AssignmentDao.AssignmentId();
            temp.assignmentId = assignmentId;
            mAssignmentDao.deleteAssignment(temp);
        });
    }

    public void deleteAssignment(long assignmentId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            AssignmentDao.AssignmentId temp = new AssignmentDao.AssignmentId();
            temp.assignmentId = assignmentId;
            mAssignmentDao.deleteAssignment(temp);
        });
    }

    //public LiveData<int[]> getAssignmentIds(){return assignmentIds;}

    /*public boolean isAssignment(long assignmentId){
        /*AtomicBoolean isAssignment = new AtomicBoolean(false);
        AppDatabase.databaseWriteExecutor.execute(() -> {
            isAssignment.set(mAssignmentDao.isAssignment(assignmentId));
        });

        return isAssignment.get();
        return mAssignmentDao.isAssignment(assignmentId);
    }*/

    public AssignmentData getAssignment(long assignmentId) {
        return mAssignmentDao.getAssignmentById(assignmentId);
    }
}
