package com.example.movemapmarker

import android.app.Application
import com.example.movemapmarker.data.MapDatabase

class MoveMapMarkerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MapDatabase.getAppDataBase(applicationContext)
    }
}