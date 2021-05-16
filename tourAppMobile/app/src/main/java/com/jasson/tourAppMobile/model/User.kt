package com.jasson.tourAppMobile.model

import java.util.*
import kotlin.collections.ArrayList

class User(
    var id: Int = -1, var name: String = "", var email: String = "", var birthDate: Date = Date(),
    var password: String = "", var country: String = "", var favs: ArrayList<Tour> = ArrayList()
)