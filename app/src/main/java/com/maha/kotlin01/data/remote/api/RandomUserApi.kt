package com.maha.kotlin01.data.remote.api

import com.maha.kotlin01.data.remote.dto.RandomUserResponse
import retrofit2.http.GET


interface RandomUserApi {
    @GET("api/")
    suspend fun getRandomUser(): RandomUserResponse

    companion object {
        const val BASE_URL = "https://randomuser.me/"
    }
}