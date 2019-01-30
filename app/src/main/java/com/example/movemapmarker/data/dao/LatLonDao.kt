package com.example.movemapmarker.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.movemapmarker.data.entity.LatLonEntity
import io.reactivex.Single

@Dao
interface LatLonDao {
    @Insert
    fun insert(latLon : LatLonEntity)

    @Query("SELECT * FROM latLonEntity")
    fun getLatLon(): Single<LatLonEntity>
}