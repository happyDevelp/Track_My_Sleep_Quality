package com.example.android.trackmysleepquality.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trackmysleepquality.DAILY_SLEEP_QUALITY_TABLE

@Entity(tableName = DAILY_SLEEP_QUALITY_TABLE)
data class SleepNight(
    @PrimaryKey(autoGenerate = true)
    val nightId: Long = 0L,

    @ColumnInfo(name = "start_time_milli")
    val startTimeMilli:Long = System.currentTimeMillis(),

    @ColumnInfo(name = "end_time_mill")
    var endTimeMilli: Long = startTimeMilli,

    @ColumnInfo(name = "sleep_quality")
    val sleepQuality: Int = -1
)