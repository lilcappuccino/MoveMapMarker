package com.example.movemapmarker

import android.os.SystemClock

const val ACCURACY = 0.5
const val DISPLACEMENT = 0.001f
const val TIME_LIMIT = 35

object CalculatePoints {
    private var currentTime = SystemClock.currentThreadTimeMillis()

    fun calculateNewLatLon(positionX: Float, positionY: Float): Pair<Float, Float> {

        if (SystemClock.currentThreadTimeMillis() - currentTime > TIME_LIMIT) {
            currentTime = SystemClock.currentThreadTimeMillis()
        } else {
            return 0f to 0f
        }
        val xPointAbs = Math.abs(positionX)
        val yPointAbs = Math.abs(positionY)
        if (xPointAbs < ACCURACY && yPointAbs < ACCURACY) return 0f to 0f
        var coordinationX = 0f
        var coordinationY = 0f
        /** select axis  */
        if (xPointAbs - yPointAbs > 0) {
            coordinationX = getPointX(positionX)
        } else {
            coordinationY = getPointY(positionY)
        }
        return coordinationX to coordinationY
    }


    private fun getPointY(coordinate: Float): Float {
        return when {
            coordinate > 0 -> DISPLACEMENT
            else -> -DISPLACEMENT
        }
    }

    private fun getPointX(coordinate: Float): Float {
        return when {
            coordinate < 0 -> DISPLACEMENT
            else -> -DISPLACEMENT
        }
    }
}