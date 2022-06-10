package com.example.droidfleetid.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.droidfleetid.R
import com.example.droidfleetid.data.Datum
import com.example.droidfleetid.domain.entity.DeviceEntity
import com.example.droidfleetid.presentation.DroidFleetViewModel
import com.example.droidfleetid.presentation.LiveDataDto
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*


class MapsFragment : Fragment(), OnMapReadyCallback {
    private val viewModel: DroidFleetViewModel by activityViewModels()
    private lateinit var mMap: GoogleMap



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true

        val coords = mutableListOf<DeviceEntity>()
        viewModel.deviceEntityListLD.observe(viewLifecycleOwner) { list ->
            list.map { coords.add(it) }
        }


        Log.d("Coords11", coords.toString())

        val builder = LatLngBounds.Builder()

            coords.map {
                val sydney = it
                val latlng = mapperDeviseEntityToLatLng(sydney)
                if (latlng!= null){
                    mMap.addMarker(MarkerOptions().position(latlng).title("adsad"))
                    builder.include(latlng)
                }

            }
            val bounds = builder.build()
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 120))

        viewModel.selectedDevice.observe(viewLifecycleOwner){ dto ->
            Log.d("SELECTEDLIVEDATA", "MapFragment $dto")
                when(dto){
                    is LiveDataDto.Device -> {
                        Log.d("SELECTEDLIVEDATA", "MapFragment ${dto.data.data.toString()}")
                        lateinit var latLng: LatLng
                        if(dto.data.data.isEmpty()){
                            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 120))
                        }else {
                            dto.data.data.map {
                                latLng = LatLng((it.coords.lat), (it.coords.lon))
                            }
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                        }
                    }
                    is LiveDataDto.Reset ->  mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 120))
                }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.resetSelectedDevice()
    }

    private fun mapperDeviseEntityToLatLng(device: DeviceEntity): LatLng? {
        if(device.data.isNotEmpty()){
            val lat = device.data.last().coords.lat
            val lon = device.data.last().coords.lon
            return LatLng(lat,lon)
        } else {
         return  null
        }
    }
}