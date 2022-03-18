package com.example.droidfleetid.presentation.fragments

import androidx.recyclerview.widget.DiffUtil
import com.example.droidfleetid.domain.entity.Device

class DeviceItemDiffCallback: DiffUtil.ItemCallback<Device>() {

    override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
        return oldItem == newItem
    }
}