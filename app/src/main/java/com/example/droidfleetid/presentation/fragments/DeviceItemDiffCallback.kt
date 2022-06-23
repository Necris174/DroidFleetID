package com.example.droidfleetid.presentation.fragments

import androidx.recyclerview.widget.DiffUtil
import com.example.droidfleetid.domain.entity.DeviceEntity

class DeviceItemDiffCallback: DiffUtil.ItemCallback<DeviceEntity>() {

    override fun areItemsTheSame(oldItem: DeviceEntity, newItem: DeviceEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DeviceEntity, newItem: DeviceEntity): Boolean {
        return oldItem == newItem
    }
}