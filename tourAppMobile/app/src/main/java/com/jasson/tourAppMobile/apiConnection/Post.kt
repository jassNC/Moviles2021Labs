package com.jasson.tourAppMobile.apiConnection

import com.google.gson.annotations.SerializedName

class Post {
    var userId: String = ""
    var id: Int = 0
    var title: String = ""

    @SerializedName("body")
    var text: String = ""

}