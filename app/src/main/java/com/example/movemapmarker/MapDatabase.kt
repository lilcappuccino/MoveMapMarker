package com.example.movemapmarker

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [LatLonEntity::class], version = 1)
abstract class MapDatabase : RoomDatabase() {

    abstract fun latLonDao(): LatLonDao


    companion object {
        var INSTANCE: MapDatabase? = null

        fun getAppDataBase(context: Context): MapDatabase? {
            if (INSTANCE == null) {
                synchronized(MapDatabase::class) {
                    INSTANCE =
                            Room.databaseBuilder(context.applicationContext, MapDatabase::class.java, "map.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}

