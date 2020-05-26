package com.example.myapplication_tema2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    public void AddUser(User user);

    @Delete
    public void DeleteUser(User user);

    @Query("select * from user")
    public List<User> getUsers();

    @Query("delete from user")
    public void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);
}

