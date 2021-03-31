package com.example.HomeworkAssignmentTaskApp.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                System.out.println("database begin loading!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                AssignmentDao assignmentDao = instance.assignmentDao();
                assignmentDao.deleteAll();
                assignmentDao.insertAssignment(new AssignmentData("Project 3"));
                assignmentDao.insertAssignment(new AssignmentData("Book Essay"));

                ClassDao classDao = instance.classDao();
                classDao.deleteAll();
                classDao.insertClass(new ClassData("English"));
                classDao.insertClass(new ClassData("Art"));
            });
        }
    };

    //public abstract ClassDao classDao();
    public abstract ClassDao classDao();
    public abstract AssignmentDao assignmentDao();
}