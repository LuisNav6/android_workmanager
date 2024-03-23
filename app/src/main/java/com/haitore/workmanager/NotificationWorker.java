package com.haitore.workmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class NotificationWorker extends Worker {
    public NotificationWorker(Context context, WorkerParameters workerParameters){
        super(context, workerParameters);
    }

    @NonNull
    @Override
    public Result doWork() {
        showNotification();
        OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInitialDelay(10, TimeUnit.SECONDS)
                .build();

        WorkManager.getInstance(getApplicationContext()).enqueue(notificationWork);
        return Result.success();
    }

    private void showNotification(){
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("channel_id", "Notificacion de Alerta", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "channel_id")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Notificacion de Workmanager")
                .setContentText("Esta notificacion es generada por Workmanager")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, builder.build());
    }
}
