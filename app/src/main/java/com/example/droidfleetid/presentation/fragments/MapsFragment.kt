package com.example.droidfleetid.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MapsFragment : Fragment(), OnMapReadyCallback  {
    private val viewModel: DroidFleetViewModel by activityViewModels()
    private lateinit var mMap: GoogleMap
    private var widthDefault =  0
    private var heightDefault = 0
    private lateinit var mBottomSheetLayout: LinearLayout
    private lateinit var informationLayout: LinearLayout
    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>
    private var deviceEntityList = mutableListOf<DeviceEntity>()



    override fun onAttach(context: Context) {
        super.onAttach(context)
        widthDefault = 40 * context.resources.displayMetrics.density.toInt()
        heightDefault = 40 * context.resources.displayMetrics.density.toInt()
        Log.d("density", widthDefault.toString())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val  root = inflater.inflate(R.layout.fragment_maps, container, false)
        mBottomSheetLayout = root.findViewById(R.id.persistent_bottom_sheet)
        sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout)
        informationLayout = root.findViewById(R.id.informationLayout)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMarkerClickListener { marker ->
            informationLayout.removeAllViews()
            val clickCount = marker.tag as? DeviceEntity
            clickCount?.sensors?.map {
                val view = LayoutInflater.from(context).inflate(R.layout.item_sensor,informationLayout,false)
                val textView = view.findViewById<TextView>(R.id.sensor_name)
                val textView1 = view.findViewById<TextView>(R.id.sensor_value)
                textView.text = it.name
                textView1.text = it.value
                Log.d("SELECTEDLIVEDATA", "MapFragment ${it.name} ${it.value}")
                informationLayout.addView(view)
            }
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            false
        }
        mMap.uiSettings.isZoomControlsEnabled = true
        val builder = LatLngBounds.Builder()

        viewModel.deviceEntityListLD.observe(viewLifecycleOwner) { list ->
            deviceEntityList.addAll(list)
            mMap.clear()
            Log.d("GOOGLE_MAP", "MapFragment Updates markers")
            list.map { entity ->

                val latLng = mapperDeviseEntityToLatLng(entity)
                if (latLng != null) {
                    mMap.addMarker(
                        MarkerOptions().position(latLng).icon(GreenMoveIcon(widthDefault,heightDefault).getIcon()).anchor(0.5f,0.5f)
                            .title("${entity.model} ${entity.number}")
                    )?.tag = entity
                    builder.include(latLng)
                }
            }
        }

            val bounds = builder.build()
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 120))

        viewModel.selectedDevice.observe(viewLifecycleOwner){ dto ->
            Log.d("SELECTEDLIVEDATA", "MapFragment $dto")
                when(dto){
                    is LiveDataDto.Device -> {
                        Log.d("SELECTEDLIVEDATA", "MapFragment ${dto.data.data}")
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