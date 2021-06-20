package com.jasson.tourAppMobile.model.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jasson.tourAppMobile.helpers.TourAppApi
import com.jasson.tourAppMobile.model.Tour
import com.jasson.tourAppMobile.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TourRepository {

    var tours = MutableLiveData<ArrayList<Tour>>()

    fun getTours(tour: Tour){
        TourAppApi().getTours(tour)
            .enqueue(object :Callback<ArrayList<Tour>>{
                override fun onResponse(
                    call: Call<ArrayList<Tour>>,
                    response: Response<ArrayList<Tour>>
                ) {
                    if (!response.isSuccessful)
                        tours.value = ArrayList()
                    else {
                        tours.value = response.body()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Tour>>, t: Throwable) {
                    tours.value = ArrayList()
                }
            })
    }
}