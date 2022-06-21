package com.example.droidfleetid.presentation.fragments

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.droidfleetid.R

class DeviceListViewHolder(view: View): RecyclerView.ViewHolder(view){
    val deviceModel: TextView = view.findViewById(R.id.device_model)
    val deviceNumber: TextView = view.findViewById(R.id.device_number)
    val deviceSpeed: TextView = view.findViewById(R.id.text_speed)
}