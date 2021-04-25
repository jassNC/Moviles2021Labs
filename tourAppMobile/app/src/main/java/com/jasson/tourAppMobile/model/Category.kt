package com.jasson.tourAppMobile.model

import org.json.JSONObject

data class Category(var id:Int = 1,var name:String = "" ){

    constructor(jsonCat: JSONObject) : this() {
        this.id = jsonCat.getInt("id")
        this.name = jsonCat.getString("name")
    }
}