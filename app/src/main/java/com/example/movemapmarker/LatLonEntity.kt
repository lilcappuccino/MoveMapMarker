package com.example.movemapmarker

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "latLonEntity")
data class LatLonEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double
)