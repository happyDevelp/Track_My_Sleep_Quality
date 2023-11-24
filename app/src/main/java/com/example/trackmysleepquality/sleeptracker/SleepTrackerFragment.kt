package com.example.trackmysleepquality.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.trackmysleepquality.R
import com.example.trackmysleepquality.databinding.FragmentSleepTrackerBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch

class SleepTrackerFragment : Fragment() {

    lateinit var binding: FragmentSleepTrackerBinding
    private lateinit var viewModel: SleepTrackerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Get a reference to the binding object and inflate the fragment views.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_tracker, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application = requireNotNull(this.activity).application

        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)

        val sleepTrackerViewModel = ViewModelProvider(this, viewModelFactory).get(SleepTrackerViewModel::class.java)

        binding.sleepTrackerViewModel = sleepTrackerViewModel

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, viewModelFactory) // passing second parameter I say to my ViewModelProvider to use this Fabric for create ScoreViewModel
            .get(SleepTrackerViewModel::class.java)


        sleepTrackerViewModel.navigateToQuality.observe(viewLifecycleOwner) {night ->
            night?.let {
                this.findNavController().navigate(SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQualityFragment(night.nightId))
                sleepTrackerViewModel.doneNavigation()
            }

            binding.sleepTrackerViewModel

        }


        //stateFlow
        /*viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.nightsString.collect() {
                    binding.textview.text = viewModel.nightsString.toString()
                }
            }
        }*/


        //LiveData
      /*  viewModel.nightsString.observe(viewLifecycleOwner) {
            binding.textview.text = viewModel.nightsString.toString()
        }

        binding.startButton.setOnClickListener {
            sleepTrackerViewModel.onStartTracking()
        }

        binding.stopButton.setOnClickListener {
            sleepTrackerViewModel.onStopTracking()
        }

        binding.clearButton.setOnClickListener {
            sleepTrackerViewModel.onClear()
        }*/



    }
}


