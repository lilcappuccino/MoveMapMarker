package com.example.movemapmarker

import android.os.SystemClock
import android.util.Log
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class MapsPresenterImpl : MapsContract.Presenter, MvpBasePresenter<MapsContract.View>() {

    private var currentTime = SystemClock.currentThreadTimeMillis()

    var latLonSubject = BehaviorSubject.create<DisplacementModel>()
    private var subscriptions = CompositeDisposable()

    override fun destroy() {
        subscriptions.dispose()
    }

    override fun attachView(view: MapsContract.View) {
        subscriptions.add(
            latLonSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    view.moveMarker(it)
                }, { Log.e("MapsPresenterImpl", it.message) })
        )
    }

    override fun detachView(retainInstance: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun detachView() {
        subscriptions.clear()
    }

    override fun calculateNewLatLon(positionX: Float, positionY: Float) {
        if (SystemClock.currentThreadTimeMillis() - currentTime > 25) {
            currentTime = SystemClock.currentThreadTimeMillis()
            Log.e("Vadim", "sd adsdsad $positionX, $positionY")
        } else {
            return
        }
        if (Math.abs(positionX) < 0.3 && Math.abs(positionY) < 0.3) return
        var coordinationX = 0f
        var coordinationY = 0f
        if (Math.abs(positionX.toInt()) - Math.abs(positionY.toInt()) > 0) {
            coordinationX = getPointX(positionX)
        } else {
            coordinationY = getPointY(positionY)
        }
        val displacement = DisplacementModel(coordinationX, coordinationY)
        latLonSubject.onNext(displacement)
    }


    private fun getPointY(coordinate: Float): Float {
        return when {
            coordinate > 0 -> +0.001f
            else -> -0.001f
        }
    }

    private fun getPointX(coordinate: Float): Float {
        return when {
            coordinate < 0 -> +0.001f
            else -> -0.001f
        }
    }
}