package com.example.trackmysleepquality.sleepquality
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.trackmysleepquality.R
import com.example.trackmysleepquality.databinding.FragmentSleepQualityBinding


class SleepQualityFragment : Fragment() {
    private lateinit var binding: FragmentSleepQualityBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {

        // Get a reference to the binding object and inflate the fragment views.

        val application = requireNotNull(this.activity).application
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_quality, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val application = requireNotNull(this.activity).application
        val arguments = SleepQualityFragmentArgs.fromBundle(requireArguments())

        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepQualityViewModelFactory(arguments.sleepNightKey, dataSource)

        val sleepQualityViewModel = ViewModelProvider(this, viewModelFactory).get(SleepQualityViewModel::class.java)

        binding.sleepQualityViewModel = sleepQualityViewModel

        sleepQualityViewModel.navigateToSleepTracker.observe(viewLifecycleOwner){
            if (it == true){
                this.findNavController().navigate(SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment2())
                sleepQualityViewModel.doneNavigation()
            }
        }

        binding.qualityZeroImage.setOnClickListener{
            sleepQualityViewModel.onSetSleepQuality(0)
        }

        binding.qualityOneImage.setOnClickListener{
            sleepQualityViewModel.onSetSleepQuality(1)
        }

        binding.qualityTwoImage.setOnClickListener{
            sleepQualityViewModel.onSetSleepQuality(2)
        }


        binding.qualityThreeImage.setOnClickListener{
            sleepQualityViewModel.onSetSleepQuality(3)
        }

        binding.qualityFourImage.setOnClickListener{
            sleepQualityViewModel.onSetSleepQuality(4)
        }

        binding.qualityFiveImage.setOnClickListener{
            sleepQualityViewModel.onSetSleepQuality(5)
        }
    }
}
