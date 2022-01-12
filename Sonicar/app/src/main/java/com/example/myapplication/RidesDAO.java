package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface RidesDAO {

    @Insert
    public void insert(Ride ride);

    @Insert
    public void insert(List<Ride> ride);

    @Query("Select * from Rides")
    public List<Ride> getAll();

    @Query("Delete from Rides where id = :id1")
    public void deleteWhere(long id1);

    @Delete
    public void delete(Ride ride);

    @Query("Select * from Rides where id=:id1")
    public Ride get(long id1);

    @Query("Select driver_id from Rides where id=:id1")
    public int getDriver_id(long id1);

    @Query("Update Rides set nr_passagers=nr_passagers-:nr where id=:id1")
    public void decreaseNr_passagers(long nr, long id1);

    @Query("Select * from Rides where driver_id=:id1")
    public List<Ride> getDriverRides(long id1);

    @Query("Update Rides set nr_passagers=nr_passagers+:nr where id=:id")
    public void increaseNr_passagers(long nr, long id);
}
