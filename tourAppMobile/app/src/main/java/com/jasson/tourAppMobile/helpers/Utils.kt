package com.jasson.tourAppMobile.helpers

import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

fun loadJSONFromAsset(inp: InputStream): String? {
    var json: String? = null
    try {
        val size: Int = inp.available()
        val buffer = ByteArray(size)
        inp.read(buffer)
        inp.close()
        json = String(buffer, Charset.forName("UTF-8"))
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    return json
}