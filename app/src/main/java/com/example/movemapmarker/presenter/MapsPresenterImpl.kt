package com.example.movemapmarker.presenter

import android.util.Log
import com.example.movemapmarker.CalculatePoints
import com.example.movemapmarker.contract.MapsContract
import com.example.movemapmarker.data.entity.LatLonEntity
import com.example.movemapmarker.repository.MapRepositoryImpl
import com.google.android.gms.maps.model.LatLng
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

const val TAG_MAP = "MapsPresenterImpl"

class MapsPresenterImpl : MapsContract.Presenter, MvpBasePresenter<MapsContract.View>() {
    private var repository = MapRepositoryImpl()

    private var latLonSubject = BehaviorSubject.create<Pair<Float, Float>>()
    private var subscriptions = CompositeDisposable()

    override fun destroy() {
        subscriptions.dispose()
    }

    override fun attachView(view: MapsContract.View) {
        subscriptions.add(
            latLonSubject
                .map {
                    CalculatePoints.calculateNewLatLon(it.first, it.second)
                }
                .filter { it.first != 0f || it.second != 0f }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    it?.let {
                        view.moveMarker(it)
                    }
                }, { Log.e(TAG_MAP, it.message) })
        )
    }

    override fun detachView(retainInstance: Boolean) {
    }

    override fun detachView() {
        subscriptions.clear()
    }

    override fun calculateNewLatLon(positionX: Float, positionY: Float) {
        latLonSubject.onNext(positionX to positionY)
    }

    override fun currentPosition(currentMarkerPosition: LatLng) {
        subscriptions.add(
            Completable.fromAction {
                repository
                    .put(LatLonEntity(currentMarkerPosition.latitude, currentMarkerPosition.longitude))
            }
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { it -> Log.e(TAG_MAP, it.message) }
                .subscribe())
    }


}
