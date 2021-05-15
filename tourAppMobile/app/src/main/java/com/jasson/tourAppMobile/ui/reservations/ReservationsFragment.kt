package com.jasson.tourAppMobile.ui.reservations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jasson.tourAppMobile.R
import com.jasson.tourAppMobile.apiConnection.Post
import com.jasson.tourAppMobile.helpers.JsonPlaceHolderApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ReservationsFragment : Fragment() {

    private lateinit var reservationViewModel: ReservationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reservationViewModel =
            ViewModelProvider(this).get(ReservationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_reservations, container, false)


        val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val jsonPlaceHolderApi = retrofit.create(
            JsonPlaceHolderApi::class.java
        )

        var call = jsonPlaceHolderApi.getPosts();


        call.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if(!response.isSuccessful){
                    //aaa.setText(response.code())
                    return
                }
                response.body()!!.forEach{ el ->
                    Log.d("aaa", el.title)
                }

            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {

                Log.d("aaa",t.message!!)
            }
        })



        return root
    }
}