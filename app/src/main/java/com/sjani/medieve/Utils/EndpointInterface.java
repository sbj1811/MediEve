package com.sjani.medieve.Utils;

import com.sjani.medieve.Models.UserEvents;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 *  API Endpoint to Connect
 */
public interface EndpointInterface {
    String API_ENDPOINT = "https://s3-us-west-2.amazonaws.com";

    @GET("/ph-svc-mobile-interview-jyzi2gyja/propeller_mobile_assessment_data.json")
    Call<UserEvents> getEvents();
}
