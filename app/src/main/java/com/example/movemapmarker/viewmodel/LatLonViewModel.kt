package com.example.movemapmarker.viewmodel

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.movemapmarker.R
import com.example.movemapmarker.base.KotlinHolder
import kotlinx.android.synthetic.main.view_latlon.view.*
import java.text.DecimalFormat


@EpoxyModelClass(layout = R.layout.view_latlon)
abstract class LatLonViewModel : EpoxyModelWithHolder<LatLonViewModel.Holder>() {
    var df = DecimalFormat("#.0000")

    @EpoxyAttribute
    var lat = 0.0
    @EpoxyAttribute
    var lon = 0.0
    @EpoxyAttribute
    var index = 1

    override fun bind(holder: Holder) {
        super.bind(holder)
        with(holder.view) {
            textLatModelView.text = df.format(lat)
            textLonModelView.text = df.format( lon)
            indexLatModelView.text = index.toString()
        }
    }

    class Holder : KotlinHolder()
}
