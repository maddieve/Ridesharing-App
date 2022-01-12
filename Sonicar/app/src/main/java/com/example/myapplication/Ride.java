package com.example.myapplication;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "Rides",foreignKeys = @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "driver_id"), indices = @Index("driver_id"))
@TypeConverters({DateConvertor.class})
public class Ride implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int driver_id;
    private String departure;
    private String destination;
    private String date;
    private int nr_passagers;


    @Override
    public String toString() {
        return "Ride{" +
                "id=" + id +
                ", driver_id=" + driver_id +
                ", departure='" + departure + '\'' +
                ", destination='" + destination + '\'' +
                ", date=" + date +
                ", nr_passagers=" + nr_passagers +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }

    public Ride(){}

    public Ride(String departure, String destination, String date, int nr_passagers) {
        this.departure = departure;
        this.destination = destination;
        this.date = date;
        this.nr_passagers = nr_passagers;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNr_passagers() {
        return nr_passagers;
    }

    public void setNr_passagers(int nr_passagers) {
        this.nr_passagers = nr_passagers;
    }
}
