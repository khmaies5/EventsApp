package com.khmaies.seatgeekevents.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.khmaies.seatgeekevents.common.EventsApp
import com.khmaies.seatgeekevents.databinding.FragmentEventDetailsBinding
import com.khmaies.seatgeekevents.utils.AppUtils.convertUtcToLocalDateTime
import com.khmaies.seatgeekevents.utils.AppUtils.formatPrice
import com.khmaies.seatgeekevents.viewmodel.EventsViewModel

class EventDetailsFragment : Fragment() {
    private var _binding: FragmentEventDetailsBinding? = null
    private lateinit var viewModel: EventsViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (requireActivity().application as EventsApp).appContainer
        val viewModelFactory = appContainer.viewModelFactory
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[EventsViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        arguments?.let {
            val data = EventDetailsFragmentArgs.fromBundle(it).event
            binding.eventName.text = data.eventName
            binding.eventAddress.text = data.eventVenue.address
            binding.eventDateTime.text = convertUtcToLocalDateTime(data.eventDate)
            binding.eventPrice.text = formatPrice(data.eventStats.eventPrice)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}