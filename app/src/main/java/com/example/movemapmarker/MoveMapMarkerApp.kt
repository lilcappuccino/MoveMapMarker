package com.example.movemapmarker

import android.app.Application
import android.arch.persistence.room.Room
import com.example.movemapmarker.data.DB_NAME
import com.example.movemapmarker.data.MapDatabase

class MoveMapMarkerApp : Application() {

    override fun onCreate() {
        super.onCreate()
       MapDatabase.getAppDataBase(applicationContext)
    }
}