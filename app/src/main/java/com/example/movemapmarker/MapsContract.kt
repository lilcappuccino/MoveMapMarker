package com.example.movemapmarker

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface MapsContract {
    interface Presenter : MvpPresenter<View> {
        fun calculateNewLatLon(positionX: Float, positionY: Float) {}

    }

    interface View : MvpView{
       fun moveMarker(displacement: DisplacementModel)
    }
}