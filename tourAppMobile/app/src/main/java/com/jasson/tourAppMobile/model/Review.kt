package com.jasson.tourAppMobile.model

import org.json.JSONObject

data class Review (var id: Int = -1, var body: String = ""){

    constructor(jsonReview: JSONObject) : this() {
        this.id = jsonReview.getInt("id")
        this.body = jsonReview.getString("body")
    }
}