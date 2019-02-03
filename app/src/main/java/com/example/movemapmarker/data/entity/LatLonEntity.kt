package com.example.movemapmarker.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "latLonEntity")
data class LatLonEntity(
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @PrimaryKey(autoGenerate = true) var id: Long = 0L)