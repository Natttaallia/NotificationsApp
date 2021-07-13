package com.example.lab6kulbaka;

import java.util.Calendar;

/**
 * @author Kulbaka Nataly
 * @date 07.05.2021
 */
public class Notification {

    public int Id;
    public String Theme;
    public String Text;
    public Calendar Date;

    public Notification(int id, String theme, String text, long date) {
        Id = id;
        Theme = theme;
        Text = text;
        Date = Calendar.getInstance();
        Date.setTimeInMillis(date);
    }
}
