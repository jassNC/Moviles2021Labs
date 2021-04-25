package com.jasson.tourAppMobile.model

import java.util.*
import kotlin.collections.ArrayList

data class Tour(var id:Int = -1, var name: String = "", var description: String="", var rating: Int = -1,
                var checkIn: String = "", var checkOut: String = "",
                var price: Double = 0.0, var seats: Int = -1, var city: String = "",
                var category: Category = Category(), var activities: ArrayList<Activity> = ArrayList(),
                var reviews: ArrayList<Review> = ArrayList())