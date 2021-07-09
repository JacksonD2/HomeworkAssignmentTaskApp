package com.example.HomeworkAssignmentTaskApp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.HomeworkAssignmentTaskApp.MainActivity;
import com.example.HomeworkAssignmentTaskApp.R;
import com.example.HomeworkAssignmentTaskApp.data.AppDatabase;
import com.example.HomeworkAssignmentTaskApp.data.AppRepository;
import com.example.HomeworkAssignmentTaskApp.data.AssignmentData;
import com.example.HomeworkAssignmentTaskApp.ui.assignments.FormattingHelper;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.HomeworkAssignmentTaskApp.MainActivity.CHANNEL_ID;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //String assignmentName = intent.getStringExtra(FormattingHelper.ASSIGNMENT_ID);
        long assignmentId = intent.getLongExtra(FormattingHelper.ASSIGNMENT_ID, 0);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            AppDatabase appDatabase = AppDatabase.getInstance(context);
            //List<AssignmentData> assignments = appDatabase.assignmentDao().getAssignmentById(assignmentId);
            AssignmentData assignmentData = appDatabase.assignmentDao().getAssignmentById(assignmentId);

            if(assignmentData!=null && !assignmentData.isComplete()) {
                String assignmentName = assignmentData.getAssignmentName();
                //String assignmentName = assignments.size()>=1 ? assignments.get(0).getAssignmentName() : "Error!!";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_edit_24)
                        .setContentTitle(assignmentName + " Due Soon")
                        //.setContentText(assignmentName)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                        //.setStyle(new NotificationCompat.BigTextStyle().bigText("Much longer text that cannot fit one line..."))
                        //.addAction(R.drawable.ic_baseline_check_24, context.getString(R.string.mark_complete), intent)


                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                notificationManager.notify(200, builder.build());
            }
            else Log.d(this.toString(), "Assignment not found");
        });
    }
}
