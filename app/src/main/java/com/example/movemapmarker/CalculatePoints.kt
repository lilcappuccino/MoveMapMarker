package com.example.movemapmarker

import android.os.SystemClock
import android.util.Log

object CalculatePoints {
    private var currentTime = SystemClock.currentThreadTimeMillis()

    fun calculateNewLatLon(positionX: Float, positionY: Float): DisplacementModel? {

        if (SystemClock.currentThreadTimeMillis() - currentTime > 25) {
            currentTime = SystemClock.currentThreadTimeMillis()
            Log.e("Vadim", "sd adsdsad $positionX, $positionY")
        } else {
            return DisplacementModel(0.0f, 0.0f)
        }
        if (Math.abs(positionX) < 0.3 && Math.abs(positionY) < 0.3) return DisplacementModel(0.0f, 0.0f)

        var coordinationX = 0f
        var coordinationY = 0f
        if (Math.abs(positionX.toInt()) - Math.abs(positionY.toInt()) > 0) {
            coordinationX = getPointX(positionX)
        } else {
            coordinationY = getPointY(positionY)
        }
        return DisplacementModel(coordinationX, coordinationY)
    }


    private fun getPointY(coordinate: Float): Float {
        return when {
            coordinate > 0 -> +0.001f
            else -> -0.001f
        }
    }

    private fun getPointX(coordinate: Float): Float {
        return when {
            coordinate < 0 -> +0.001f
            else -> -0.001f
        }
    }
}