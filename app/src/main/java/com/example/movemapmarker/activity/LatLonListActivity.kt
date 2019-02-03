package com.example.movemapmarker.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movemapmarker.R
import com.example.movemapmarker.contract.LatLonListContract
import com.example.movemapmarker.controller.LatLonController
import com.example.movemapmarker.presenter.LatLonListPresenterImpl
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import kotlinx.android.synthetic.main.activity_latlon_list.*

class LatLonListActivity : MvpActivity<LatLonListContract.View, LatLonListContract.Presenter>(),
    LatLonListContract.View {
    private var controller = LatLonController()
    override fun createPresenter(): LatLonListContract.Presenter = LatLonListPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latlon_list)
        recyclerLatLonList.layoutManager = LinearLayoutManager(this)
        recyclerLatLonList.adapter = controller.adapter
        cleanLatLonList.setOnClickListener { onDeleteClick() }
    }


    private fun onDeleteClick() {
        presenter.cleanDB()
        controller.latLon = arrayListOf()
        emptyImgLatLonList.visibility = View.VISIBLE
        emptyTextLatLonList.visibility = View.VISIBLE
    }

    override fun setData(dbLatLonList: List<Pair<Double, Double>>) {
        controller.latLon = dbLatLonList
    }
}