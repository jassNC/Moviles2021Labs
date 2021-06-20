package com.jasson.tourAppMobile.helpers

import android.content.Context
import android.os.Build
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import com.jasson.tourAppMobile.R
import com.jasson.tourAppMobile.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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



@RequiresApi(Build.VERSION_CODES.M)
fun checkLogin(
    email: AppCompatEditText,
    password: AppCompatEditText,
    btn: Button,
    context: Context
) {
    if (email.text!!.matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")) && password.text!!.matches(
            Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!.%*?&])[A-Za-z\\d@\$.!%*?&]{8,}")
        )
    ) {
        btn.backgroundTintList = context.resources.getColorStateList(R.color.primaryColor, null)
        btn.isEnabled = true
    } else {
        btn.backgroundTintList = context.resources.getColorStateList(R.color.disabled, null)
        btn.isEnabled = false
    }

}

fun formatDate(date: String):String{
    val dateArr = date.split("/").toTypedArray()
    if(dateArr[0].length==1){
        dateArr[0]="0${dateArr[0]}"
    }
    if(dateArr[1].length==1){
        dateArr[1]="0${dateArr[1]}"
    }
    var p = "${dateArr[2]}-${dateArr[0]}-${dateArr[1]}"
    return p
}