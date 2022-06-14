package com.example.droidfleetid.presentation.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.droidfleetid.R
import com.example.droidfleetid.domain.entity.DeviceEntity
import com.example.droidfleetid.presentation.DroidFleetViewModel
import com.example.droidfleetid.presentation.LiveDataDto
import com.example.droidfleetid.presentation.fragments.icons.GreenMoveIcon
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


class MapsFragment : Fragment(), OnMapReadyCallback {
    private val viewModel: DroidFleetViewModel by activityViewModels()
    private lateinit var mMap: GoogleMap
    private var markers: MutableList<Marker?> = mutableListOf()
    private var widthDefault =  0
    private var heightDefault = 0
    private var radius = 0f


    private val paintWhite = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
    }
    private val paintGreen = Paint().apply {
        isAntiAlias = true
        color = Color.GREEN
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        widthDefault = 50 * context.resources.displayMetrics.density.toInt()
        heightDefault = 50 * context.resources.displayMetrics.density.toInt()
        radius = widthDefault / 2f
    }

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
        mMap.uiSettings.isCompassEnabled = true

        val bitmap = Bitmap.createBitmap(widthDefault, heightDefault, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawCircle(radius, radius, radius, paintWhite)
        canvas.drawCircle(radius, radius, radius - 2f, paintGreen)

        val icon = BitmapDescriptorFactory.fromBitmap(bitmap)

        val builder = LatLngBounds.Builder()
        viewModel.deviceEntityListLD.observe(viewLifecycleOwner) { list ->
            mMap.clear()
            Log.d("GOOGLE_MAP", "MapFragment Updates markers")
            list.map {
                val sydney = it
                val latlng = mapperDeviseEntityToLatLng(sydney)
                if (latlng != null) {
                    val myMarker = mMap.addMarker(
                        MarkerOptions().position(latlng).icon(icon)
                            .title("${sydney.model} ${sydney.number} ${sydney.data.first().coords.speed}")
                    )
                    markers.add(myMarker)
                    builder.include(latlng)
                }
            }
        }

            val bounds = builder.build()
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 120))

        viewModel.selectedDevice.observe(viewLifecycleOwner){ dto ->
                when(dto){
                    is LiveDataDto.Device -> {
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
        return if(device.data.isNotEmpty()){
            val lat = device.data.last().coords.lat
            val lon = device.data.last().coords.lon
            LatLng(lat,lon)
        } else {
            null
        }
    }
}