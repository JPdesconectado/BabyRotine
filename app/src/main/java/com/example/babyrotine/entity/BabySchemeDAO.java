package com.example.babyrotine.entity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BabySchemeDAO {

    @Query("SELECT * FROM babyScheme ORDER BY hour DESC")
    List<BabyScheme> getAll();

    @Query("SELECT * FROM babyScheme WHERE activity = :activity ORDER BY hour DESC")
    List<BabyScheme> findByActivityBaby(String activity);

    @Insert
    void insertActivityBaby(BabyScheme babyScheme);

    @Insert
    void insertAll(List<BabyScheme> babySchemes);

    @Update
    void updateActivityBaby(BabyScheme babyScheme);

    @Delete
    void deleteActivityBaby(BabyScheme babyScheme);

    @Query("DELETE FROM babyScheme")
    void deleteAll();

}
