package com.example.tests

import MatchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/venues/search")
    fun getMatches(
        @Query("ll") ll: String,
        @Query("oauth_token") token: String,
        @Query("v") version: String
    ): Call<MatchResponse> // Ensure it returns a list of Venue objects
}
