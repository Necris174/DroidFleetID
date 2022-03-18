package com.example.droidfleetid.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.droidfleetid.databinding.FragmentDeviceListBinding
import com.example.droidfleetid.domain.entity.Device
import com.example.droidfleetid.presentation.DeviceListAdapter

class DeviceListFragment : Fragment() {

    private lateinit var adapter: DeviceListAdapter

    private var _binding: FragmentDeviceListBinding? = null
    private val binding: FragmentDeviceListBinding
        get() = _binding ?: throw RuntimeException("LoginFragment == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeviceListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        adapter.submitList(listOf(Device("12321","123444","23434",
            "34535333",true,true,true,true,"qeqweq",
            "123133","23123","21323","3123123"),Device("12321","123444","23434",
            "34535333",true,true,true,true,"qeqweq",
            "123133","23123","21323","3123123"),Device("12321","123444","23434",
            "34535333",true,true,true,true,"qeqweq",
            "123133","23123","21323","3123123"),Device("12321","123444","23434",
            "34535333",true,true,true,true,"qeqweq",
            "123133","23123","21323","3123123")))
    }

    private fun setupRecyclerView(){
            adapter = DeviceListAdapter()
            binding.rvDeviceList.adapter = adapter
            adapter.onDeviceItemClickListener = {
                  Toast.makeText(activity,"Нажатие на ${it.imei}",Toast.LENGTH_LONG).show()
                }
            }
        }
