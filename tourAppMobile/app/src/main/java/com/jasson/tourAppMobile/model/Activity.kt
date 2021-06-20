package com.jasson.tourAppMobile.model

import org.json.JSONObject

data class Activity(var id: Int=-1, var body: String = ""){
    
    constructor(jsonAct: JSONObject) : this() {
        this.id = jsonAct.getInt("id")
        this.body = jsonAct.getString("body")
    }
}