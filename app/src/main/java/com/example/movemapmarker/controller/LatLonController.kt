package com.example.movemapmarker.controller

import com.airbnb.epoxy.EpoxyController
import com.example.movemapmarker.viewmodel.LatLonViewModel_

class LatLonController : EpoxyController() {
    private var ID = 1

    var latLon = listOf<Pair<Double, Double>>()
        set(value) {
            field = value
            requestModelBuild()
        }


    override fun buildModels() {
        latLon.forEach {
            addItem(it.first, it.second)
            ID++
        }
    }

    private fun addItem(lat: Double, lon: Double) {
        LatLonViewModel_()
            .id(ID)
            .index(ID)
            .lat(lat)
            .lon(lon)
            .addTo(this)

    }


}