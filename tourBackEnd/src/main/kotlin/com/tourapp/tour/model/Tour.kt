package com.tourapp.tour.model

import java.util.*
import kotlin.collections.ArrayList

data class Tour(var id:Int = -1, var name: String = "", var description: String="", var rating: Int = -1,
                var checkIn: String = "1999-01-01", var checkOut: String = "3000-01-01", var duration: Float = 1F,
                var price: Double = 0.0, var seats: Int = -1, var city: String = "", var image: String = "",
                var category: Category = Category(), var activities: ArrayList<Activity> = ArrayList(),
                var reviews: ArrayList<Review> = ArrayList())