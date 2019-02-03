package com.example.movemapmarker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movemapmarker.data.entity.LatLonEntity
import io.reactivex.Single

@Dao
interface LatLonDao {
    @Insert
    fun insert(latLon: LatLonEntity)

    @Query("SELECT * FROM latLonEntity")
    fun getLatLon(): Single<List<LatLonEntity>>
}