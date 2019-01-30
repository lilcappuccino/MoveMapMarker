package com.example.movemapmarker.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.movemapmarker.data.entity.LatLonEntity
import com.example.movemapmarker.data.dao.LatLonDao

@Database(entities = [LatLonEntity::class], version = 1, exportSchema = false)
abstract class MapDatabase : RoomDatabase() {

    abstract fun latLonDao(): LatLonDao



    companion object {
        var INSTANCE: MapDatabase? = null

        fun getAppDataBase(context: Context): MapDatabase? {
            if (INSTANCE == null) {
                synchronized(MapDatabase::class) {
                    INSTANCE =
                            Room.databaseBuilder(context.applicationContext, MapDatabase::class.java, DB_NAME).build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }

    }
}

const val DB_NAME = "map.db"

