package com.example.HomeworkAssignmentTaskApp.deprecated;

import android.content.Context;

import com.example.HomeworkAssignmentTaskApp.ApplicationViewModel;

import java.io.File;
import java.io.IOException;

public class DatabaseManager {

    public static String dbFileName = "user_db.db";

    public void initializeDatabase(Context context, ApplicationViewModel viewModel){
        File dir = new File(context.getFilesDir(), "user_data");
        if(!dir.exists()){
            dir.mkdir();
        }

        try {
            File dbFile = new File(dir, dbFileName);
            if (!dbFile.exists()) {
                dbFile.createNewFile();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
