package com.example.HomeworkAssignmentTaskApp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ClassRepository {

    private ClassDao mClassDao;
    private AssignmentDao mAssignmentDao;
    private LiveData<List<AssignmentData>> mAllAssignments;
    private LiveData<List<ClassData>> mAllClasses;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public ClassRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mClassDao = db.classDao();
        mAssignmentDao = db.assignmentDao();
        mAllClasses = mClassDao.getAllClasses();
        mAllAssignments = mAssignmentDao.getAllAssignments();
    }

    public LiveData<List<ClassData>> getAllClasses() {
        return mAllClasses;
    }

    public LiveData<List<AssignmentData>> getAllAssignments() {
        return mAllAssignments;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertClass(ClassData classData) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mClassDao.insertClass(classData);
        });
    }

    public void updateClass(ClassData classData) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mClassDao.updateClass(classData);
        });
    }

    public void insertAssignment(AssignmentData assignmentData) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mAssignmentDao.insertAssignment(assignmentData);
        });
    }

    public void updateAssignment(AssignmentData assignmentData) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mAssignmentDao.updateAssignment(assignmentData);
        });
    }
}
