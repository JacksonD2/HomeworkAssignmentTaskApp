package com.example.HomeworkAssignmentTaskApp.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ClassData.class, AssignmentData.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "app_db";
    private static AppDatabase instance;

    //threads, might be optional https://developer.android.com/codelabs/android-room-with-a-view#7
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized AppDatabase getInstance(Context context){
        if(instance==null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DB_NAME).addCallback(sRoomDatabaseCallback).build();
            //instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                System.out.println("database begin loading!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                AssignmentDao assignmentDao = instance.assignmentDao();
                assignmentDao.deleteAll();

                AssignmentData temp = new AssignmentData("Project 3");
                Date date = new Date(System.currentTimeMillis());
                temp.setDueDate(date);
                //temp.setComplete(true);
                assignmentDao.insertAssignment(temp);
                temp = new AssignmentData("Book Essay");
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, 3); //add days
                date = c.getTime();
                temp.setDueDate(date);
                //temp.setComplete(true);
                assignmentDao.insertAssignment(temp);
                temp = new AssignmentData("Exam");
                c.add(Calendar.DATE, 2);
                temp.setDueDate(c.getTime());
                assignmentDao.insertAssignment(temp);
                temp = new AssignmentData("Worksheet");
                c.add(Calendar.DATE, 1);
                temp.setDueDate(c.getTime());
                assignmentDao.insertAssignment(temp);

                ClassDao classDao = instance.classDao();
                classDao.deleteAll();
                classDao.insertClass(new ClassData("English"));
                classDao.insertClass(new ClassData("Art"));

                mainThreadHandler.post(() -> {
                    //assignmentListLoaded.setValue(true);
                    System.out.println(9999);
                });
            });
        }
    };

    //public abstract ClassDao classDao();
    public abstract ClassDao classDao();
    public abstract AssignmentDao assignmentDao();
}