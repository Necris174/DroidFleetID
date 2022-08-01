package com.example.droidfleetid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.droidfleetid.databinding.FragmentReportDeviceListBinding
import com.example.droidfleetid.presentation.DroidFleetViewModel

class FragmentReportDeviceList : Fragment() {

    private var _binding: FragmentReportDeviceListBinding? = null
    private val binding: FragmentReportDeviceListBinding
        get() = _binding ?: throw RuntimeException("FragmentReportDeviceListBinding == null")

    private lateinit var adapter: SelectedDeviceAdapter

    private val viewModel: DroidFleetViewModel by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportDeviceListBinding.inflate(inflater, container, false)
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
        adapter = SelectedDeviceAdapter()
        binding.selectDeviceRecycler.adapter = adapter

    }

}