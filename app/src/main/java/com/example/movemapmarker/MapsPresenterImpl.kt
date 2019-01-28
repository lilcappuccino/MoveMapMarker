package com.example.movemapmarker

import android.os.SystemClock
import android.util.Log
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class MapsPresenterImpl : MapsContract.Presenter, MvpBasePresenter<MapsContract.View>() {

    private var currentTime = SystemClock.currentThreadTimeMillis()

    var latLonSubject = BehaviorSubject.create<DisplacementModel?>()
    private var subscriptions = CompositeDisposable()

    override fun destroy() {
        subscriptions.dispose()
    }

    override fun attachView(view: MapsContract.View) {
        subscriptions.add(
            latLonSubject
//                .debounce(30, TimeUnit.MILLISECONDS)
                .map { it -> var a = CalculatePoints.calculateNewLatLon(it.x, it.y)
                    a
                }
                .filter { it.x != 0f || it.y !=0f }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    it?.let {
                        view.moveMarker(it)
                    }
                }, { Log.e("MapsPresenterImpl", it.message) })
        )
    }

    override fun detachView(retainInstance: Boolean) {
    }

    override fun detachView() {
        subscriptions.clear()
    }

    override fun calculateNewLatLon(positionX: Float, positionY: Float) {
        val displacement = DisplacementModel(positionX, positionY)
        latLonSubject.onNext(displacement)
    }


}