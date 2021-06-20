package com.jasson.tourAppMobile.model.repositories

import androidx.lifecycle.MutableLiveData
import com.jasson.tourAppMobile.helpers.TourAppApi
import com.jasson.tourAppMobile.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    var user = MutableLiveData<User>()
    var logged = MutableLiveData<Boolean>()
    var status = MutableLiveData<String>()

    init {
        user.value = User()
        logged.value = false
        status.value = ""
    }

    fun userLogin(pUser: User) {
        TourAppApi().getUser(pUser)
            .enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (!response.isSuccessful) {
                        user.value = User()
                        status.value = "notFound"
                    } else {
                        user.value = response.body()
                        status.value = "success"
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    user.value = User()
                    status.value = "connError"
                }
            })
    }

    fun registerUser(pUser: User) {
        TourAppApi().putUser(pUser)
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        if (!response.isSuccessful) {
                            logged.value = false
                            status.value = "notFound"
                        } else {
                            logged.value = response.body()
                            status.value = "success"
                        }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    logged.value = false
                    status.value = "connError"
                }
            })
    }
}