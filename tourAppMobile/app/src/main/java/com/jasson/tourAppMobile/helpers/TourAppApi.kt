package com.jasson.tourAppMobile.helpers

import com.jasson.tourAppMobile.Constants
import com.jasson.tourAppMobile.model.Tour
import com.jasson.tourAppMobile.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface TourAppApi {
    @GET("users")
    fun getUsers():Call<ArrayList<User>>

    @POST("users")
    fun getUser(@Body user: User): Call<User>

    @PUT("users")
    fun putUser(@Body user: User): Call<Boolean>

    @POST("tours")
    fun getTours(@Body tour: Tour): Call<ArrayList<Tour>>

    companion object {
        operator fun invoke(): TourAppApi {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(TourAppApi::class.java)
        }
    }
}