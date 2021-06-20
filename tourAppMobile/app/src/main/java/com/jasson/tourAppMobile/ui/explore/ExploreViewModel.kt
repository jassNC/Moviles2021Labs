package com.jasson.tourAppMobile.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jasson.tourAppMobile.model.Tour
import com.jasson.tourAppMobile.model.repositories.TourRepository

class ExploreViewModel : ViewModel() {

    var tours = MutableLiveData<ArrayList<Tour>>()
    var tourRepository = TourRepository()

    init {
        var t = Tour()
        tours = tourRepository.tours
        tourRepository.getTours(t)
    }

    fun searchTours(tour: Tour){
        tourRepository.getTours(tour)
    }
}