package com.example.droidfleetid.presentation

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.entity.DeviceEntity
import com.example.droidfleetid.R
import com.example.droidfleetid.presentation.fragments.DeviceItemDiffCallback
import com.example.droidfleetid.presentation.fragments.DeviceListViewHolder
import kotlin.random.Random

class DeviceListAdapter : ListAdapter<DeviceEntity, DeviceListViewHolder>(DeviceItemDiffCallback())
{


    var onDeviceItemClickListener: ((DeviceEntity) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_device_info, parent, false)
        return DeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        val deviceItem = getItem(position)
        if (deviceItem.data.isEmpty()){
            holder.address.text = "В черном списке"
        } else {
            holder.itemView.setOnClickListener {
                onDeviceItemClickListener?.invoke(deviceItem)
            }
            holder.address.text = deviceItem.address
        }


        holder.deviceModel.text = deviceItem.model
        holder.deviceNumber.text = deviceItem.number
        if(deviceItem.data.isNotEmpty()) {

            val time = deviceItem.data.first().time
            if (time != null) {
                when (time) {
                    in 0..60 -> {
                        holder.lastDate.text = "$time c"
                        val gradientDrawable =
                            (holder.lastDate.background as GradientDrawable).mutate()
                        (gradientDrawable as GradientDrawable).setColor(Color.GREEN)
                    }
                    in 60..300 -> {
                        holder.lastDate.text = "${time / 60} м"
                        val gradientDrawable =
                            (holder.lastDate.background as GradientDrawable).mutate()
                        (gradientDrawable as GradientDrawable).setColor(Color.GREEN)
                    }
                    in 300..3600 -> {
                        holder.lastDate.text = "${time / 60} м"
                        val gradientDrawable =
                            (holder.lastDate.background as GradientDrawable).mutate()
                        (gradientDrawable as GradientDrawable).setColor(Color.YELLOW)
                    }
                    else -> {
                        holder.lastDate.text = "> 1ч"
                        val gradientDrawable =
                            (holder.lastDate.background as GradientDrawable).mutate()
                        (gradientDrawable as GradientDrawable).setColor(Color.RED)
                    }
                }
            }
        } else {
            holder.lastDate.text = "> 1ч"
            val gradientDrawable =
                (holder.lastDate.background as GradientDrawable).mutate()
            (gradientDrawable as GradientDrawable).setColor(Color.RED)
        }


        if(deviceItem.data.isEmpty()){
            holder.deviceSpeed.text = "S"
        }else{
            holder.deviceSpeed.text = deviceItem.data.first().coords.speed.toString()
        }


    }



}