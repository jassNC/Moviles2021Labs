package com.jasson.tourAppMobile.ui.reservations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jasson.tourAppMobile.R

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




        return root
    }
}