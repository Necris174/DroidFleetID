package com.example.droidfleetid.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ListAdapter
import com.example.droidfleetid.R
import com.example.droidfleetid.domain.entity.DeviceEntity
import com.example.droidfleetid.presentation.fragments.DeviceItemDiffCallback
import com.example.droidfleetid.presentation.fragments.DeviceListViewHolder

class DeviceListAdapter : ListAdapter<DeviceEntity, DeviceListViewHolder>(DeviceItemDiffCallback())
{


    var onDeviceItemClickListener: ((DeviceEntity) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_device_info, parent, false)
        return DeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        val deviceItem = getItem(position)

        holder.deviceModel.text = deviceItem.model
        holder.deviceNumber.text = deviceItem.number

        if(deviceItem.data.isEmpty()){
            holder.deviceSpeed.text = "S"
        }else{
            holder.deviceSpeed.text = deviceItem.data.first().coords.speed.toString()
        }
        holder.deviceSpeed.animate()
            .setDuration(3000)
            .scaleX(2F)
            .scaleY(2F)
            .setInterpolator(BounceInterpolator())
            .start()

        holder.itemView.setOnClickListener {
            onDeviceItemClickListener?.invoke(deviceItem)
        }
    }



}