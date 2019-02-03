package com.example.movemapmarker.presenter

import android.util.Log
import com.example.movemapmarker.contract.LatLonListContract
import com.example.movemapmarker.data.entity.LatLonEntity
import com.example.movemapmarker.repository.MapRepositoryImpl
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

const val TAG_LATLON = "LatLonListPresenterImpl"

class LatLonListPresenterImpl : MvpPresenter<LatLonListContract.View>, LatLonListContract.Presenter {
    private var subscriptions = CompositeDisposable()
    private var latLonList = mutableListOf<Pair<Double, Double>>()
    private var view: LatLonListContract.View? = null

    var repository = MapRepositoryImpl()
    override fun destroy() {
    }

    override fun attachView(view: LatLonListContract.View) {
        this.view = view
        subscriptions.add(repository.getLatLon()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it -> latLonToPair(it) }, { Log.e(TAG_LATLON, it.message) }
            ))
    }

    private fun latLonToPair(latLonEntityList: List<LatLonEntity>) {
        latLonEntityList.forEach {
            latLonList.add(it.latitude to it.longitude)
        }
        view?.setData(latLonList)
    }

    override fun cleanDB() {
        subscriptions.add(
            Completable.fromAction {
                repository.clearDataStore()
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.e(TAG_LATLON, it.message) }
                .subscribe()
        )
    }


    override fun detachView(retainInstance: Boolean) {
    }

    override fun detachView() {
        subscriptions.clear()
        view = null
    }
}