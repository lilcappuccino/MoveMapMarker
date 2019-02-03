package com.example.movemapmarker.activity

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.animation.LinearInterpolator
import com.example.movemapmarker.R
import com.example.movemapmarker.contract.MapsContract
import com.example.movemapmarker.presenter.MapsPresenterImpl
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : MvpActivity<MapsContract.View, MapsContract.Presenter>(), OnMapReadyCallback, SensorEventListener,
    MapsContract.View {

    private lateinit var map: GoogleMap
    private var sensorManager: SensorManager? = null
    private var accelerometerSensor: Sensor? = null
    private var myMarker: Marker? = null


    // coordinate of marker lat, lon
    private var markerCoordinateX = 0.0
    private var markerCoordinateY = 0.0

    private var isMapReady = false
    private val zoomLevel = 13f
    private val zoomDuration = 500

    override fun createPresenter(): MapsContract.Presenter = MapsPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)?.let {
            it.getMapAsync(this)
        }
        btnMapActivityOpenList.setOnClickListener { startActivity(Intent(this, LatLonListActivity::class.java)) }
    }

    override fun onResume() {
        super.onResume()
        initSensor()
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        markerCoordinateX = 30.5238  //Kiev`s coordinate
        markerCoordinateY = 50.45466 //Kiev`s coordinate
        val kiev = LatLng(markerCoordinateY, markerCoordinateX)
        with(map) {
            myMarker = addMarker(
                MarkerOptions().position(kiev).icon(BitmapDescriptorFactory.fromResource(R.drawable.tractor_ic)).title("Трактор в полі др др др")
            )
            moveCamera(CameraUpdateFactory.newLatLng(myMarker?.position))
            animateCamera(CameraUpdateFactory.newLatLngZoom(kiev, zoomLevel), zoomDuration, null)
        }
        isMapReady = true

    }

    private fun initSensor() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager?.let {
            accelerometerSensor = it.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            it.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        event?.apply {
            if (sensor?.type != Sensor.TYPE_ACCELEROMETER || !isMapReady) return
            presenter.calculateNewLatLon(values[0], values[1])
        }

    }

    override fun moveMarker(displacement: Pair<Float, Float>) {
        markerCoordinateX += displacement.first
        markerCoordinateY += displacement.second
        animateMarker(myMarker, LatLng(markerCoordinateY, markerCoordinateX))
    }


    private fun animateMarker(marker: Marker?, toPosition: LatLng) {
        presenter.currentPosition(toPosition)
        if (marker == null) return
        val handler = Handler()
        val start = SystemClock.uptimeMillis()
        val proj = map.projection
        val startPoint = proj.toScreenLocation(marker.position)
        val startLatLng = proj.fromScreenLocation(startPoint)
        val duration: Long = 500

        val interpolator = LinearInterpolator()

        handler.post(object : Runnable {
            override fun run() {
                val elapsed = SystemClock.uptimeMillis() - start
                val t = interpolator.getInterpolation(elapsed.toFloat() / duration)
                val lng = t * toPosition.longitude + (1 - t) * startLatLng.longitude
                val lat = t * toPosition.latitude + (1 - t) * startLatLng.latitude
                marker.position = LatLng(lat, lng)

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16)
                } else {
                    marker.isVisible = true
                }
            }
        })
    }
}
