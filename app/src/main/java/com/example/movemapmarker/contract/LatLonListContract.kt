package com.example.movemapmarker.contract

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface LatLonListContract {
    interface Presenter : MvpPresenter<View> {
        fun cleanDB()
    }

    interface View : MvpView {
        fun setData(dbLatLonList: List<Pair<Double, Double>>)

    }
}