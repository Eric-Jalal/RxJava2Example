package com.example.ahhosseini.rxjava2.network;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ahHosseini on 2/12/18.
 */

public interface CityService {
    @GET("citiesJSON")
    Single<CityResponse> queryGeoname(
            @Query("north") double north,
            @Query("south") double south,
            @Query("east") double east,
            @Query("west") double west,
            @Query("lang") String lang);
}
