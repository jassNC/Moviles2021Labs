package com.jasson.tourAppMobile.model

import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

data class Tour(var id:Int = -1, var name: String = "", var description: String="", var rating: Int = -1,
                var checkIn: String = "2000-01-01", var checkOut: String = "2090-01-01",
                var duration: Float = 1F, var price: Double = 0.0,
                var seats: Int = -1, var city: String = "", var image: String = "",
                var category: Category = Category(), var activities: ArrayList<Activity> = ArrayList(),
                var reviews: ArrayList<Review> = ArrayList()){

    constructor(jsonTour: JSONObject) : this() {
        this.id = jsonTour.getInt("id")
        this.name = jsonTour.getString("name")
        this.description = jsonTour.getString("description")
        this.rating = jsonTour.getInt("rating")
        this.checkIn = jsonTour.getString("checkIn")
        this.checkOut = jsonTour.getString("checkOut")
        this.price = jsonTour.getDouble("price")
        this.seats = jsonTour.getInt("seats")
        this.city = jsonTour.getString("city")
        this.image = jsonTour.getString("image")
        this.category = Category(jsonTour.getJSONObject("category"))
        val activities = jsonTour.getJSONArray("activities")
        for (i in 0 until activities.length()){
            this.activities.add(Activity(activities.getJSONObject(i)))
        }
        val reviews = jsonTour.getJSONArray("reviews")
        for (i in 0 until reviews.length()){
            this.reviews.add(Review(reviews.getJSONObject(i)))
        }
    }
}