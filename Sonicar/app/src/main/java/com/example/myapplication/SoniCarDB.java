package com.example.myapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Ride.class, User.class, RideReserved.class}, version = 3, exportSchema = false)
public abstract class SoniCarDB extends RoomDatabase {

    private final static String DB_NAME = "SoniCar.db";
    private static SoniCarDB instanta;

    public static SoniCarDB getInstance(Context context) {
        if (instanta == null) {
            instanta = Room.databaseBuilder(context, SoniCarDB.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instanta;
    }

    public abstract UsersDAO getUsersDAO();
    public abstract RidesDAO getRidesDAO();
    public abstract RidesReservedDAO getRidesReservedDAO();
}
