package com.example.ahhosseini.rxjava2.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ahHosseini on 2/12/18.
 */

interface CityService {
    @GET("citiesJSON")
    fun queryGeoname(
            @Query("north") north: Double,
            @Query("south") south: Double,
            @Query("east") east: Double,
            @Query("west") west: Double,
            @Query("lang") lang: String): Single<CityResponse>
}
