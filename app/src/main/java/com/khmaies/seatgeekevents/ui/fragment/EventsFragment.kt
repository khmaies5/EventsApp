package com.khmaies.seatgeekevents.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.khmaies.seatgeekevents.common.EventsApp
import com.khmaies.seatgeekevents.databinding.FragmentEventsBinding
import com.khmaies.seatgeekevents.model.Event
import com.khmaies.seatgeekevents.ui.EndlessScrollListener
import com.khmaies.seatgeekevents.ui.adapter.EventsAdapter
import com.khmaies.seatgeekevents.utils.State
import com.khmaies.seatgeekevents.viewmodel.EventsViewModel


class EventsFragment : Fragment() {
    private var _binding: FragmentEventsBinding? = null
    private lateinit var viewModel: EventsViewModel

    private lateinit var eventsAdapter: EventsAdapter
    var events: MutableList<Event> = mutableListOf()
    var searchQuery = ""

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
        // Inflate the layout for this fragment
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        initRecyclerView()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchData()
        observeCall()

        binding.inputFindEvent.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchQuery = binding.inputFindEvent.text.toString()
                viewModel.fetchData(searchQuery)
                true // Return true to indicate that the event has been consumed
            } else {
                false // Return false if the event hasn't been consumed
            }
        }
    }

    /**
     * prepare recyclerView's layout and actions
     */
    private fun initRecyclerView() {
        eventsAdapter =
            EventsAdapter(EventsAdapter.OnClickListener { event ->
                val action =
                    EventsFragmentDirections.actionEventsFragmentToEventDetailsFragment(event)
                findNavController().navigate(action)
            })

        val mLayoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.eventsRecyclerview.apply {
            layoutManager = mLayoutManager
            adapter = eventsAdapter
        }

        binding.eventsRecyclerview.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore(page: Int) {
                viewModel.addMoreData(query = searchQuery, pageIndex = page)
            }
        })
    }

    private fun observeCall() {
        viewModel.eventsListLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                }

                is State.Success -> {

                    if (viewModel.currentPage == 1) {
                        eventsAdapter.setData(state.data.events)
                    } else {
                        eventsAdapter.setNextData(state.data.events)
                    }
                }

                is State.Error -> {
                    Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}