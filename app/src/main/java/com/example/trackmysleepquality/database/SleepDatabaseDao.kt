package com.example.android.trackmysleepquality.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.trackmysleepquality.DAILY_SLEEP_QUALITY_TABLE

@Dao
interface SleepDatabaseDao{
    @Insert()
    fun insert(night: SleepNight)

    @Update()
    fun update(night: SleepNight)

    @Query("SELECT * from $DAILY_SLEEP_QUALITY_TABLE WHERE nightId = :key")
    fun get(key: Long): SleepNight?

    @Query("DELETE FROM $DAILY_SLEEP_QUALITY_TABLE")
    fun clear()

    @Query("SELECT * FROM $DAILY_SLEEP_QUALITY_TABLE ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<SleepNight>>

    @Query("SELECT * FROM $DAILY_SLEEP_QUALITY_TABLE ORDER BY nightId DESC LIMIT 1")
    fun getTonight(): SleepNight?
}