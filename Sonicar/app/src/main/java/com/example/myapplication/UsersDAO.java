package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsersDAO {

    @Insert
    public void insert(User user);

    @Insert
    public void insert(List<User> user);

    @Query("Select * from Users")
    public List<User> getAll();

    @Query("Delete from Users where id = :id1")
    public void deleteWhere(long id1);//use long not int

    @Delete
    public void delete(User user);

    @Query("Select MAX(id) from Users")
    public int maxUserId();

    @Query("Select * from Users where id=:id1")
    public User get(long id1);

    //updates

    @Query("Update Users set lastname=:ln where id=:id")
    public void updateLN(String ln, long id);

    @Query("Update Users set firstname=:ln where id=:id")
    public void updateFN(String ln, long id);

    @Query("Update Users set username=:ln where id=:id")
    public void updateUN(String ln, long id);

    @Query("Update Users set phone=:ln where id=:id")
    public void updatePH(String ln, long id);

    @Query("Update Users set birthdate=:ln where id=:id")
    public void updateBD(String ln, long id);

    @Query("Update Users set email=:ln where id=:id")
    public void updateEM(String ln, long id);


}
