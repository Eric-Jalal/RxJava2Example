package com.example.ahhosseini.rxjava2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.example.ahhosseini.rxjava2.network.CityResponse
import com.example.ahhosseini.rxjava2.network.CityService
import com.example.ahhosseini.rxjava2.network.Geoname
import com.example.ahhosseini.rxjava2.network.RetrofitHelper

import org.w3c.dom.Text

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {


    @NonNull
    private var mCityService: CityService? = null

    @NonNull
    private val mCompositeDisposable = CompositeDisposable()

    private var mOutputTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mOutputTextView = findViewById<View>(R.id.output) as TextView?
        mCityService = RetrofitHelper().cityService

        requestGeonames()
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
    }

    private fun displayGeonames(@NonNull geonames: List<Geoname>) {
        val output = StringBuilder()
        for (geoname in geonames) {
            output.append(geoname.name).append("\n")
            output.append(geoname.countryCode).append("\n")
        }
        mOutputTextView!!.text = output.toString()
    }

    private fun requestGeonames() {
        mCompositeDisposable.add(mCityService!!.queryGeoname(44.1, -9.9, -22.4, 55.2, "de")
                .subscribeOn(Schedulers.io()) // "work" on io thread
                .observeOn(AndroidSchedulers.mainThread()) // "listen" on UIThread
                .map { cityResponse ->
                    // we want to have the geonames and not the wrapper object
                    cityResponse.geonames
                }
                .subscribe { geonames -> displayGeonames(geonames) }
        )
    }
}