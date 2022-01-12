package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RidesReservedDAO {

    @Insert
    public void insert(RideReserved rideReserved);

    @Delete
    public void delete(RideReserved rideReserved);

    @Query("Select * from RidesReserved where user_id=:id")
    public List<RideReserved> getRidesReserved(long id);

    @Query("Select * from RidesReserved where ride_id=:id")
    public List<RideReserved> getRidesReserved2(long id);

    @Query("select * from RidesReserved where ride_id=:id1 and user_id=:id2")
    public RideReserved getRideReserved(long id1, long id2);

    @Query("delete from RidesReserved where ride_id=:id1 and user_id=:id2")
    public void deleteWhere(long id1, long id2);

    @Query("Select user_id from RidesReserved where ride_id=:id")
    public int getPassager(long id);


}
