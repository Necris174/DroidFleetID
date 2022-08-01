package com.example.droidfleetid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.entity.DeviceEntity
import com.example.droidfleetid.databinding.ItemSelectDeviceBinding
import com.example.droidfleetid.presentation.fragments.DeviceItemDiffCallback
import com.example.droidfleetid.presentation.fragments.DeviceListViewHolder

class SelectedDeviceAdapter : ListAdapter<DeviceEntity, SelectedDeviceListViewHolder>(
    DeviceItemDiffCallback()
){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectedDeviceListViewHolder {
        val view = ItemSelectDeviceBinding.inflate(LayoutInflater.from(parent.context))
        return SelectedDeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectedDeviceListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
