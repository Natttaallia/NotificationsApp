package com.example.lab6kulbaka;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.TextView;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            int id = arguments.getInt("ID");
            SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
            Cursor query = db.rawQuery("SELECT * FROM notifications WHERE ID ='" + id + "';", null);
            query.moveToNext();
            final TextView tvTitle = (TextView) findViewById(R.id.theme);
            final TextView tvText = (TextView) findViewById(R.id.text);
            final TextView tvDate = (TextView) findViewById(R.id.date);
            tvText.setText(query.getString(2));
            tvTitle.setText(query.getString(1));
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(query.getLong(3));
            tvDate.setText(DateUtils.formatDateTime(this,
                                                    c.getTimeInMillis(),
                                                    DateUtils.FORMAT_SHOW_DATE
                                                            | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));
            query.close();
            db.close();
        }
    }
}