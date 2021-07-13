package com.example.lab6kulbaka;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationsActivity extends AppCompatActivity {

    NotificationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ListView listView = findViewById(R.id.listView);
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM notifications;", null);

        final ArrayList<Notification> data = new ArrayList<>();
        while (query.moveToNext()) {
            data.add(new Notification(
                    query.getInt(0),
                    query.getString(1),
                    query.getString(2),
                    query.getLong(3)));
        }
        query.close();

        mAdapter = new NotificationAdapter(this, R.layout.list_item, data,
                                           (notification) -> {
                                               db.delete("notifications", "ID = ?",
                                                         new String[]{String.valueOf(notification.Id)});
                                               Toast.makeText(this, getString(R.string.success_delete),
                                                              Toast.LENGTH_SHORT).show();
                                               mAdapter.remove(notification);
                                           },
                                           (id) -> {
                                               final Intent intent = new Intent(this,
                                                                                NotificationActivity.class);
                                               intent.putExtra("ID", id);
                                               startActivity(intent);
                                           });
        listView.setAdapter(mAdapter);
    }
}