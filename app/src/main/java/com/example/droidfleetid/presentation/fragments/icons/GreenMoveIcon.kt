package com.example.droidfleetid.presentation.fragments.icons

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class GreenMoveIcon(context: Context) {
    private var widthDefault = 20 * context.resources.displayMetrics.density.toInt()
    private var heightDefault = 20 * context.resources.displayMetrics.density.toInt()
    private var radius = widthDefault / 2f


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
        canvas.drawCircle(radius, radius, radius - 2f, paintGreen)
        canvas.drawRect(0f, 0f, radius, radius, paintWhite)
         val icon = BitmapDescriptorFactory.fromBitmap(bitmap)
        return  icon
    }





}