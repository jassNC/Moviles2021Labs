package com.jasson.tourAppMobile.helpers

import com.jasson.tourAppMobile.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface JsonPlaceHolderApi {
    @POST("getUser")
    fun getUser(@Body user: User): Call<User>

    @PUT("putUser")
    fun putUser(@Body user: User): Call<Boolean>
}