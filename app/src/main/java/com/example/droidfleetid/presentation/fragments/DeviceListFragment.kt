package com.example.droidfleetid.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.droidfleetid.databinding.FragmentDeviceListBinding
import com.example.droidfleetid.presentation.DeviceListAdapter
import com.example.droidfleetid.presentation.DroidFleetViewModel

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
        binding.rvDeviceList.itemAnimator = null
        binding.rvDeviceList.adapter = adapter
        adapter.onDeviceItemClickListener = {
            viewModel.selectedDevice(it)
        }
    }


}
