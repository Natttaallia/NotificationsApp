package com.example.lab6kulbaka;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS notifications (" +
                           "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                           "theme TEXT, " +
                           "text TEXT, " +
                           "date DATETIME DEFAULT CURRENT_TIMESTAMP)");
        Cursor query = db.rawQuery("SELECT * FROM notifications;", null);
        int delay = 0;
        while (query.moveToNext()) {
            final Notification notification = new Notification(
                    query.getInt(0),
                    query.getString(1),
                    query.getString(2),
                    query.getLong(3));
            if (notification.Date.before(Calendar.getInstance())) {
                scheduleNotification(getNotification(notification), delay);
                delay += 5000;
            }
        }
        query.close();
    }

    private void scheduleNotification(android.app.Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                                                                 notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private android.app.Notification getNotification(Notification notification) {
        android.app.Notification.Builder builder = new android.app.Notification.Builder(this);
        builder.setContentTitle(notification.Theme);
        builder.setContentText(notification.Text);
        builder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24);
        Intent resultIntent = new Intent(this, NotificationActivity.class);
        resultIntent.putExtra("ID", notification.Id);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                                                                      PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        return builder.build();
    }

    public void onCreateClick(View view) {
        final Intent intent = new Intent(this, CreateNotificationActivity.class);
        startActivity(intent);
    }

    public void onShowClick(View view) {
        final Intent intent = new Intent(this, NotificationsActivity.class);
        startActivity(intent);
    }
}