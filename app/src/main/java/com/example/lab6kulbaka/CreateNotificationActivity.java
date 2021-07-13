package com.example.lab6kulbaka;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNotificationActivity extends AppCompatActivity {

    TextView mTimeTv;
    TextView mDateTv;
    TextInputEditText mTheme;
    TextInputEditText mText;
    Calendar mDateAndTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification);
        mTimeTv = (TextView) findViewById(R.id.time);
        mDateTv = (TextView) findViewById(R.id.date);
        mTheme = (TextInputEditText) findViewById(R.id.theme);
        mText = (TextInputEditText) findViewById(R.id.text);
        setInitialDateTime();
    }

    public void onTimeClick(View view) {
        if (mDateAndTime == null) mDateAndTime = Calendar.getInstance();
        new TimePickerDialog(CreateNotificationActivity.this, t,
                             mDateAndTime.get(Calendar.HOUR_OF_DAY),
                             mDateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    private void setInitialDateTime() {
        mDateTv.setText(DateUtils.formatDateTime(this,
                                                 mDateAndTime.getTimeInMillis(),
                                                 DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        mTimeTv.setText(DateUtils.formatDateTime(this,
                                                 mDateAndTime.getTimeInMillis(),
                                                 DateUtils.FORMAT_SHOW_TIME));
    }

    TimePickerDialog.OnTimeSetListener t = (view, hourOfDay, minute) -> {
        mDateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mDateAndTime.set(Calendar.MINUTE, minute);
        setInitialDateTime();
    };

    DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
        mDateAndTime.set(Calendar.YEAR, year);
        mDateAndTime.set(Calendar.MONTH, monthOfYear);
        mDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDateTime();
    };

    public void onDateClick(View view) {
        if (mDateAndTime == null) mDateAndTime = Calendar.getInstance();
        new DatePickerDialog(CreateNotificationActivity.this, d,
                             mDateAndTime.get(Calendar.YEAR),
                             mDateAndTime.get(Calendar.MONTH),
                             mDateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void onSaveClick(View view) {
        if (mTheme.getText() == null || mTheme.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.no_theme), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mText.getText() == null || mText.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.no_text), Toast.LENGTH_SHORT).show();
            return;
        }
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("theme", mTheme.getText().toString());
        values.put("text", mText.getText().toString());
        values.put("date", mDateAndTime.getTimeInMillis());
        db.insert("notifications", "", values);
        Toast.makeText(this, getString(R.string.success_added), Toast.LENGTH_SHORT).show();
        finish();
    }
}