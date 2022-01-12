package com.example.myapplication;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "RidesReserved",foreignKeys ={ @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "user_id"),
        @ForeignKey(entity = Ride.class,parentColumns = "id",childColumns = "ride_id")}, indices ={@Index("user_id"),@Index("ride_id")})
public class RideReserved {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int user_id;
    private int ride_id;
    private int nr_passagers_reserved;

    public RideReserved(){}

    public RideReserved(int user_id, int ride_id, int nr_passagers_reserved) {
        this.user_id = user_id;
        this.ride_id = ride_id;
        this.nr_passagers_reserved=nr_passagers_reserved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNr_passagers_reserved() {
        return nr_passagers_reserved;
    }

    public void setNr_passagers_reserved(int nr_passagers_reserved) {
        this.nr_passagers_reserved = nr_passagers_reserved;
    }

    @Override
    public String toString() {
        return "RideReserved{" +
                "id=" + id +
                "user_id=" + user_id +
                ", ride_id=" + ride_id +
                ", nr_passagers_reserved=" + nr_passagers_reserved +
                '}';
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRide_id() {
        return ride_id;
    }

    public void setRide_id(int ride_id) {
        this.ride_id = ride_id;
    }
}
