package com.nguyen.topcodertaasi.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CovidAPI {
    @GET("general-stats")
    Call<World> getWorld();

    @GET("countries-search")
    Call<Countries> getCountries(@Query("order") String order, @Query("page") int page);
}
