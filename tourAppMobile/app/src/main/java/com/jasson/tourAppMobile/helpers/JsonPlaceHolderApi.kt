package com.jasson.tourAppMobile.helpers

import com.jasson.tourAppMobile.apiConnection.Post
import com.jasson.tourAppMobile.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface JsonPlaceHolderApi {
    @GET("posts")
    fun getPosts() : Call<List<Post>>

    @POST("getUser")
    fun getUser(@Body user: User): Call<User>
}