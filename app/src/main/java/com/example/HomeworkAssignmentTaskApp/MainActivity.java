package com.example.HomeworkAssignmentTaskApp;

import android.os.Bundle;
import android.view.Menu;

import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.data.ClassData;
import com.example.HomeworkAssignmentTaskApp.deprecated.ClassObject;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    ArrayList<ClassObject> classList;
    ArrayList<AssignmentData> assignmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //ViewModel
        ApplicationViewModel appViewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        appViewModel.getClassList().observe(this, new Observer<List<ClassData>>() {
            @Override
            public void onChanged(List<ClassData> classData) {
                //appViewModel.appDatabase.classDao().getAllClasses();
            }
        });
        appViewModel.getAssignmentList().observe(this, new Observer<List<AssignmentData>>() {
            @Override
            public void onChanged(List<AssignmentData> assignmentData) {
                if(assignmentData == null){

                }
            }
        });

        //Action/tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //App drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        //Navigation Component
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_assignments, R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {

                //viewModel and files
        AssignmentsViewModel viewModel = new ViewModelProvider(this).get(AssignmentsViewModel.class);
        viewModel.setClassList(new ArrayList<ClassObject>());
        viewModel.getClassList().observe(this, new Observer<ArrayList<ClassObject>>() {
            @Override
            public void onChanged(ArrayList<ClassObject> classData) {
                System.out.println("ChanGed!!!!!!!!!!!!!!!\n\n\n\n\n\n");
                viewModel.setClassList(classData);
            }
        });
        FileManager.initializeAppFiles(getApplicationContext());
        FileManager.readStoredData(savedInstanceState, getApplicationContext(), viewModel);

        //database
        ApplicationViewModel appViewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        appViewModel.getClassList().observe(this, new Observer<List<ClassData>>() {
            @Override
            public void onChanged(List<ClassData> classData) {
                //appViewModel.appDatabase.classDao().getAllClasses();
                appViewModel.updateClassList();
            }
        });

        //database with rxjava
        ApplicationViewModel2 appViewModel = new ViewModelProvider(this).get(ApplicationViewModel2.class);
        final Disposable subscribe = appViewModel.getClassList().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(classList ->
                {
                    //consume modelClasses here which is a list of ModelClass
                    System.out.println("RoomWithRx: " + classList.size());

                }, e -> System.out.println("RoomWithRx: " + e.getMessage()));
        appViewModel.getClassList().observe(this, new Observer<List<ClassData>>() {
            @Override
            public void onChanged(List<ClassData> classData) {
                //appViewModel.appDatabase.classDao().getAllClasses();
            }
        });

    }*/