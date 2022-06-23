package com.example.droidfleetid.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.droidfleetid.R
import com.example.droidfleetid.databinding.FragmentDeviceListBinding
import com.example.droidfleetid.presentation.DeviceListAdapter
import com.example.droidfleetid.presentation.DroidFleetViewModel
import com.example.droidfleetid.presentation.LiveDataDto
import com.google.android.material.bottomnavigation.BottomNavigationView

class DeviceListFragment : Fragment() {

    private lateinit var adapter: DeviceListAdapter

    private var _binding: FragmentDeviceListBinding? = null
    private val binding: FragmentDeviceListBinding
        get() = _binding ?: throw RuntimeException("LoginFragment == null")

    private val viewModel: DroidFleetViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeviceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.deviceEntityListLD.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        adapter = DeviceListAdapter()
        binding.rvDeviceList.adapter = adapter
        adapter.onDeviceItemClickListener = {

            viewModel.selectDevice(it)
        }
    }


}
