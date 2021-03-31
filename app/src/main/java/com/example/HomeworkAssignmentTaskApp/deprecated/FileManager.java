package com.example.HomeworkAssignmentTaskApp.deprecated;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.example.HomeworkAssignmentTaskApp.R;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileManager {

    //static String string = context.getResources().getString(R.string.class_list_filename);

    public static void checkStoredData(){

    }

    public static void copyFromAssets(Context context){
        try {
            String fileName = context.getResources().getString(R.string.class_list_filename);
            File dir = new File(context.getFilesDir(), "user_data");
            File courses = new File(dir, fileName);
            if(!courses.exists()){
                courses.createNewFile();
            }

            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter pw = new PrintWriter(courses);

            String line = reader.readLine();
            while(line!=null) {
                pw.println(line);
                line = reader.readLine();
            }

            pw.close();
            inputStream.close();
            reader.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            checkStoredData();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void copyToAssets(Context context){
        try {
            String fileName = context.getResources().getString(R.string.class_list_filename);
            File dir = new File(context.getFilesDir(), "user_data");
            File courses = new File(dir, fileName);
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter pw = new PrintWriter(courses);

            String line = reader.readLine();
            while(line!=null) {
                pw.println(line);
                line = reader.readLine();
            }

            pw.close();
            inputStream.close();
            reader.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            checkStoredData();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void readStoredData(Bundle bundle, Context context, @NotNull AssignmentsViewModel model){
        MutableLiveData<ArrayList<ClassObject>> mutableList = model.getClassList();
        ArrayList<ClassObject> classList = mutableList.getValue();
        String line;
        int numCourses = 15;

        try {
            String fileName = context.getResources().getString(R.string.class_list_filename);
            File dir = new File(context.getFilesDir(), "user_data");
            File courses = new File(dir, fileName);
            FileInputStream inputStream = new FileInputStream(courses);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            for (int i = 0; i < numCourses; i++) {
                line = reader.readLine();
                if(line==null){
                    break;
                }
                classList.add(new ClassObject(line));
            }

            mutableList.setValue(classList);

            inputStream.close();
            reader.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
            checkStoredData();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //note: add part that copies files from assets to internal storage
    public static boolean initializeAppFiles(Context context){
        File dir = new File(context.getFilesDir(), "user_data");

        if(!dir.exists()){
            dir.mkdir();
        }

        copyFromAssets(context);
        /*try{
            copyFromAssets(context);
            //File courses = new File(dir, "Course_list.txt");
            if(!courses.createNewFile()){
                //do something here
                checkStoredData();
            }
            //else {
                AssetManager assetManager = context.getAssets();
                InputStream inputStream = assetManager.open("Classes_list.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                int numCourses = 15;

                try {
                    PrintWriter pw = new PrintWriter(courses);
                    //pw.print(reader.read());

                    line = reader.readLine();

                    while(line!=null){
                        pw.println(line);
                        line = reader.readLine();
                    }
                    pw.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return false;
                }


                /*PrintWriter pw;
                try {
                    pw = new PrintWriter(courses);
                    pw.print(0);
                    pw.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                inputStream.close();
                reader.close();
            //}
        }catch (FileNotFoundException e){
            e.printStackTrace();
            checkStoredData();
            return false;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }*/

        return true;
    }

    public static boolean addNewClass(String className, @NotNull View view, @NotNull AssignmentsViewModel model){
        MutableLiveData<ArrayList<ClassObject>> mutableList = model.getClassList();
        ArrayList<ClassObject> classList = mutableList.getValue();

        if(className==null || className.length()==0){
            return false;
        }

        try{
            String fileName = view.getContext().getResources().getString(R.string.class_list_filename);
            File dir = new File(view.getContext().getFilesDir(), "user_data");
            File courses = new File(dir, fileName);
            boolean wasAdded = false;

            for(int i = 0; i<classList.size(); i++){
                int comparison = classList.get(i).getCourseName().compareToIgnoreCase(className);

                if(comparison==0){
                    return false;
                }
                else if(comparison>0){
                    //System.out.println(className+"!!!!!!!!!!!!!!!!!!!");
                    wasAdded = true;
                    classList.add(i, new ClassObject(className));
                    break;
                }
            }

            if(!wasAdded){
                classList.add(new ClassObject(className));
            }
            mutableList.setValue(classList);

            FileWriter writer = new FileWriter(courses, false);
            for(int i = 0; i<classList.size(); i++){
                writer.append(classList.get(i).getCourseName()+"\n");
            }

            writer.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
            checkStoredData();
            return false;
        } catch(IOException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}

    /*public static void writeFileOnInternalStorage(Context mcoContext, String fileName, String body){
        File dir = new File(mcoContext.getFilesDir(), "mydir");

        if(!dir.exists()){
            dir.mkdir();
        }

        try {
            File gpxfile = new File(dir, fileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(body);
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }*/

/*public static boolean addNewClass(String className, @NotNull View view, @NotNull AssignmentsViewModel model){
        AssetManager assetManager = view.getContext().getAssets();
        String line;
        ArrayList<ClassData> classList = model.getClassList().getValue();

        if(className==null || className.length()==0){
            return false;
        }

        try{
            InputStream inputStream = assetManager.open("Classes_list.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            Boolean wasAdded= false;
            for(int i = 0; i<classList.size(); i++){
                int comparison = classList.get(i).getCourseName().compareToIgnoreCase(className);

                if(comparison==0){
                    return false;
                }
                if(comparison>0){
                    System.out.println(className+"!!!!!!!!!!!!!!!!!!!");
                    wasAdded = true;
                    classList.add(i, new ClassData(className));
                    break;
                }
                line = reader.readLine();
            }

            if(!wasAdded){
                classList.add(new ClassData(className));
            }

            inputStream.close();
            reader.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
            checkStoredData();
            return false;
        } catch(IOException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }*/

    /*public static void readStoredData(Bundle bundle, Context context,
                                      ArrayList<ClassData> classList, ArrayList<AssignmentData> assignmentList){
        //AssetManager assetManager = context.getAssets();
        String line;
        int numCourses = 15;

        try {
            File dir = new File(context.getFilesDir(), "user_data");
            File courses = new File(dir, "Course_list.txt");
            FileInputStream inputStream = new FileInputStream(courses);
            //InputStream inputStream = assetManager.open("Classes_list.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            for (int i = 0; i < numCourses; i++) {
                line = reader.readLine();
                if(line==null){
                    break;
                }
                classList.add(new ClassData(line));
            }

            inputStream.close();
            reader.close();

            //bundle.putParcelableArrayList("classList", classList);
        } catch (FileNotFoundException e){
            e.printStackTrace();
            checkStoredData();
        } catch(IOException e){
            e.printStackTrace();
        }
    }*/

    /*public static boolean addNewClass(String className, @NotNull View view, @NotNull AssignmentsViewModel model){
        //AssetManager assetManager = view.getContext().getAssets();
        //String line;
        MutableLiveData<ArrayList<ClassData>> mutableList = model.getClassList();
        ArrayList<ClassData> classList = mutableList.getValue();

        if(className==null || className.length()==0){
            return false;
        }

        try{
            File dir = new File(view.getContext().getFilesDir(), "user_data");
            File courses = new File(dir, "Course_list.txt");
            // FileInputStream inputStream = new FileInputStream(courses);
            //BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            boolean wasAdded = false;

            for(int i = 0; i<classList.size(); i++){
                int comparison = classList.get(i).getCourseName().compareToIgnoreCase(className);

                if(comparison==0){
                    return false;
                }
                else if(comparison>0){
                    System.out.println(className+"!!!!!!!!!!!!!!!!!!!");
                    wasAdded = true;
                    classList.add(i, new ClassData(className));
                    break;
                }
                //line = reader.readLine();
            }

            mutableList.setValue(classList);

            if(!wasAdded){
                classList.add(new ClassData(className));
            }

            //FileOutputStream outputStream = new FileOutputStream(courses, false);
            FileWriter writer = new FileWriter(courses, false);
            for(int i = 0; i<classList.size(); i++){
                writer.append(classList.get(i).getCourseName()+"\n");
            }

            writer.close();
            //inputStream.close();
            //reader.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
            checkStoredData();
            return false;
        } catch(IOException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }*/