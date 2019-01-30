package com.example.movemapmarker.contract

import com.google.android.gms.maps.model.LatLng
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface MapsContract {
    interface Presenter : MvpPresenter<View> {
        fun calculateNewLatLon(positionX: Float, positionY: Float)
        fun currentPosition(currentMarkerPosition : LatLng)
    }

    interface View : MvpView{
       fun moveMarker(displacement: Pair<Float, Float>)
    }
}