package com.example.HomeworkAssignmentTaskApp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {

    private ClassDao mClassDao;
    private AssignmentDao mAssignmentDao;
    private LiveData<List<AssignmentData>> mAllAssignments, mIncompleteAssignments, mCompleteAssignments;
    private LiveData<List<ClassData>> mAllClasses;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mClassDao = db.classDao();
        mAssignmentDao = db.assignmentDao();
        mAllClasses = mClassDao.getAllClasses();
        mAllAssignments = mAssignmentDao.getAllAssignments();
        mIncompleteAssignments = mAssignmentDao.getIncompleteAssignments();
        mCompleteAssignments = mAssignmentDao.getCompleteAssignments();
    }

    public LiveData<List<ClassData>> getAllClasses() {
        return mAllClasses;
    }

    public LiveData<List<AssignmentData>> getAllAssignments() {
        return mAllAssignments;
    }

    public LiveData<List<AssignmentData>> getIncompleteAssignments() {
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

    public void insertAssignment(AssignmentData assignmentData) {
        AppDatabase.databaseWriteExecutor.execute(() -> mAssignmentDao.insertAssignment(assignmentData));
    }

    public void updateAssignment(AssignmentData assignmentData) {
        AppDatabase.databaseWriteExecutor.execute(() -> mAssignmentDao.updateAssignment(assignmentData));
    }
}
