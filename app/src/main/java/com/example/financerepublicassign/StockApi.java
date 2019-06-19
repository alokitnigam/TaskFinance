package com.example.financerepublicassign;

import com.example.financerepublicassign.Model.StockResponse;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StockApi {
    @GET("/query")
Call<StockResponse> getNewsList(@Query("function") String newsSource, @Query("symbol") String symbol,
                                @Query("apikey") String apiKey);
}
