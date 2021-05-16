package com.jasson.tourAppMobile.ui.profile

import android.content.ClipData
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.jasson.tourAppMobile.R
import com.jasson.tourAppMobile.apiConnection.Post
import com.jasson.tourAppMobile.helpers.JsonPlaceHolderApi
import com.jasson.tourAppMobile.helpers.SelectDateFragment
import com.jasson.tourAppMobile.model.User
import com.jasson.tourAppMobile.ui.register.RegisterFragment
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val btnRegister: Button = root.findViewById(R.id.btnRegister)
        val btnLogin: Button = root.findViewById(R.id.btnLogin)
        val inputEmail: AppCompatEditText = root.findViewById(R.id.inputEmail)
        val inputPassword: AppCompatEditText = root.findViewById(R.id.inputPassword)

        val retrofit = Retrofit.Builder().baseUrl("https://5ae448e79a28.ngrok.io/tourApi/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val jsonPlaceHolderApi = retrofit.create(
            JsonPlaceHolderApi::class.java
        )
        var user: User
        btnLogin.setOnClickListener {
            user = this.validateUser(inputEmail, inputPassword, jsonPlaceHolderApi, root.context)
        }

        inputEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                checkValues(inputEmail, inputPassword, btnLogin, root.context)

                inflater.inflate(R.layout.fragment_favorites, container, false)
            }
        })

        inputPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                checkValues(inputEmail, inputPassword, btnLogin, root.context)
            }

        })

        btnRegister.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavigationProfileToRegisterFragment()
            findNavController().navigate(action)
        }
        return root
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun checkValues(
        email: AppCompatEditText,
        password: AppCompatEditText,
        btn: Button,
        context: Context
    ) {
        if (email.text!!.matches(Regex("[\\w]+@[\\w]+.+[\\w]{2,4}")) && password.text!!.matches(
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

    fun validateUser(
        inputEmail: AppCompatEditText,
        inputPassword: AppCompatEditText,
        jsonPlaceHolderApi: JsonPlaceHolderApi,
        context: Context
    ): User {
        var userRes = User()
        var user = User()
        user.email = inputEmail.text.toString()
        user.password = inputPassword.text.toString()
        var call = jsonPlaceHolderApi.getUser(user);
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (!response.isSuccessful) {
                    Toast.makeText(context, "The user does not exists", Toast.LENGTH_LONG).show();
                    return
                }
                userRes = response.body()!!

            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(context, "The user does not exists", Toast.LENGTH_LONG).show();
            }
        })

        return userRes
    }
}