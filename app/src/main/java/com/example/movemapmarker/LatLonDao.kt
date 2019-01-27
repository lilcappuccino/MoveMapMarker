package com.example.movemapmarker

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert

@Dao
interface LatLonDao {
    @Insert
    fun insert(latitude: Double, longitude: Double)
}