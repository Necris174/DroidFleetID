package com.example.droidfleetid

import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.DeviceEntity
import com.example.droidfleetid.databinding.ItemSelectDeviceBinding

class SelectedDeviceListViewHolder(val binding: ItemSelectDeviceBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(device: DeviceEntity) {
        binding.selectName.text = device.model
        binding.selectNumber.text = device.number
    }
}


