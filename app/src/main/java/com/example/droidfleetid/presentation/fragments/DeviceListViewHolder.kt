package com.example.droidfleetid.presentation.fragments

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.droidfleetid.R

class DeviceListViewHolder(view: View): RecyclerView.ViewHolder(view){
    val deviceModel = view.findViewById<TextView>(R.id.device_model)
    val deviceNumer = view.findViewById<TextView>(R.id.device_number)
    val deviceMode  = view.findViewById<ImageView>(R.id.icon_card)
}