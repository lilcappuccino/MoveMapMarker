package com.example.movemapmarker.repository

import com.example.movemapmarker.data.MapDatabase
import com.example.movemapmarker.data.entity.LatLonEntity
import io.reactivex.Single

class MapRepositoryImpl {
    private var database = MapDatabase.INSTANCE


    fun put(latLon: LatLonEntity) {
        database?.latLonDao()?.insert(latLon)
    }

    fun getLatLon(): Single<LatLonEntity>? = database?.latLonDao()?.getLatLon()


}