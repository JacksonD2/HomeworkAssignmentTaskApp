package com.example.HomeworkAssignmentTaskApp;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.FormattingHelper;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.ListHelper;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "Main_Channel";
    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        //ViewModel
        ApplicationViewModel appViewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        appViewModel.getClassList().observe(this, classData -> { });
        appViewModel.getAssignmentList().observe(this, assignmentData -> { });
        appViewModel.getIncompleteAssignmentList().observe(this, assignmentData -> { });
        appViewModel.getCompleteAssignmentList().observe(this, assignmentData -> { });
        appViewModel.newAssignment.observe(this, assignmentData -> {
            if(assignmentData!=null) {
                if (assignmentData.isComplete())
                    ListHelper.sortedInsert(assignmentData, appViewModel.sCompleteAssignmentList);
                else
                    ListHelper.sortedInsert(assignmentData, appViewModel.sIncompleteAssignmentList);
                createReminder(assignmentData);
                Toast.makeText(this, this.getString(R.string.assignment_successfully_added,
                        assignmentData.getAssignmentName()), Toast.LENGTH_SHORT).show();
                appViewModel.assignmentListModified.setValue(null);
            }
        });
        appViewModel.getDeletedAssignmentId().observe(this, v -> {
            String assignmentName = appViewModel.deleteAssignment(v);
            if(!assignmentName.equals("")) {
                Toast.makeText(this, this.getString(R.string.assignment_successfully_deleted,
                        assignmentName), Toast.LENGTH_SHORT).show();
            }
            //appViewModel.assignmentListModified.setValue(null);
            /*if(appViewModel.isAssignmentDeleted()){
                String assignmentName = appViewModel.deleteAssignment(v);
                Toast.makeText(this, this.getString(R.string.assignment_successfully_deleted,
                        assignmentName), Toast.LENGTH_SHORT).show();
            }*/
        });
        appViewModel.assignmentListModified.observe(this, v -> { });

        //Action/tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //App drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        //Navigation Component
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_assignments)
                //.setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            // User chose the "Settings" item, show the app settings UI...
            navController.navigate(R.id.nav_settings);
            return true;
        }// If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createReminder(AssignmentData assignmentData){
        Intent intent = new Intent(this, ReminderBroadcast.class);
        intent.putExtra(FormattingHelper.ASSIGNMENT_ID, assignmentData.getAssignmentId());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar c = Calendar.getInstance();
        c.setTime(assignmentData.getDueDate());
        //c.set(Calendar.MINUTE, 0);
        //c.set(Calendar.HOUR_OF_DAY, 0);
        c.add(Calendar.DAY_OF_YEAR, -1); //sets reminder 24 hours before assignment is due
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTime().getTime(), pendingIntent);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, dueDate.getTime(), );
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