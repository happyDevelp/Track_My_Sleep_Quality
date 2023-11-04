package com.example.android.trackmysleepquality.sleepquality

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.database.SleepDatabaseDao

class SleepQualityViewModel(val database: SleepDatabaseDao, application: Application): AndroidViewModel(application){



}
