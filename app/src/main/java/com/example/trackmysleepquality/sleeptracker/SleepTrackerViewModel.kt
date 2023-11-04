package com.example.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.constraintlayout.widget.ConstraintSet.Transform
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.trackmysleepquality.formatNights
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.xml.transform.Transformer

class SleepTrackerViewModel(
    val database: SleepDatabaseDao, application: Application
) : AndroidViewModel(application) {

    //Make it possible to start or stop coroutine. It give to manage a state of coroutine`s processing
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        //when this ViewModel is destroy then Job stop all coroutine here in ViewModel
        viewModelJob.cancel()
    }

    //Create Scope where specify in which thread will coroutine work. And also specify a Job
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /*private var tonight = MutableLiveData<SleepNight?>(*/
    private var tonight = MutableStateFlow<SleepNight?>(null)

    private var nights = database.getAllNights()

    val nightsString = nights.map { nights ->
        formatNights(nights, application.resources)
    }






/*
    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }
*/


    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        //make a coroutine that will not block main thread. He know which thread cuz I defined it in CoroutineScope  CoroutineScope(Dispatchers.Main + viewModelJob)
        //So, Main thread will not blocked
        uiScope.launch {
            tonight.value = getTonigntFromDatabase()
        }
    }

    private suspend fun getTonigntFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = database.getTonight()
            if (night?.endTimeMilli != night?.startTimeMilli) night = null

            night
        }

    }

    fun onStartTracking() {
        uiScope.launch {
            val newNight = SleepNight()

            insert(newNight)

            tonight.value = getTonigntFromDatabase()

        }
    }

    private suspend fun insert(night: SleepNight) {
        return withContext(Dispatchers.IO) {
            database.insert(night)
        }
    }

    fun onStopTracking() {
        uiScope.launch {
            // In Kotlin, the return@label syntax is used for specifying which function among
            // several nested ones this statement returns from.
            // In this case, we are specifying to return from launch(),
            // not the lambda.
            val oldNight = tonight.value ?: return@launch

            // Update the night in the database to add the end time.
            oldNight.endTimeMilli = System.currentTimeMillis()

            update(oldNight)
        }
    }

    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(night)
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            tonight.value = null
        }
    }

    private suspend fun clear() {
        database.clear()
    }


}




