package com.example.myapplication;

import java.util.Date;

import androidx.room.TypeConverter;

class DateConvertor {
    @TypeConverter
    public static Date fromTimeStamp(Long timeStamp) {
        return timeStamp != null
                ? new Date(timeStamp)
                : null;
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date != null
                ? date.getTime() : null;
    }
}