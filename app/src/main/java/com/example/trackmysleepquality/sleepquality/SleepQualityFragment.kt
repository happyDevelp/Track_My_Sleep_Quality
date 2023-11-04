package com.example.android.trackmysleepquality.sleepquality
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.trackmysleepquality.R
import com.example.trackmysleepquality.databinding.FragmentSleepQualityBinding


class SleepQualityFragment : Fragment() {
    lateinit var binding: FragmentSleepQualityBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {

        // Get a reference to the binding object and inflate the fragment views.
        val binding = FragmentSleepQualityBinding.inflate(
                inflater, container, false)

        val application = requireNotNull(this.activity).application

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }
}
