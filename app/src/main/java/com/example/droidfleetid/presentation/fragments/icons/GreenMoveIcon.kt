package com.example.droidfleetid.presentation.fragments.icons

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class GreenMoveIcon(val widthDefault: Int, val heightDefault: Int) {
    private var radius = widthDefault / 2f
    private var margin5 = widthDefault*0.05f
    private var margin20 = widthDefault*0.25f



    private val paintWhite = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
    }
    private val paintGreen = Paint().apply {
        isAntiAlias = true
        color = Color.GREEN
    }

    fun getIcon(): BitmapDescriptor {
        val bitmap = Bitmap.createBitmap(widthDefault, heightDefault, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawCircle(radius, radius, radius, paintWhite)
        canvas.drawCircle(radius, radius, radius - margin5, paintGreen)
        canvas.drawRect(margin20,   margin20, widthDefault - margin20, heightDefault - margin20, paintWhite)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }





}