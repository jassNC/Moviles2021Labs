package com.jasson.tourAppMobile.ui.profile

import android.content.ClipData
import android.content.Context
import android.content.SharedPreferences
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
import android.widget.TextView
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
import com.jasson.tourAppMobile.helpers.JsonPlaceHolderApi
import com.jasson.tourAppMobile.helpers.SelectDateFragment
import com.jasson.tourAppMobile.helpers.checkLogin
import com.jasson.tourAppMobile.model.User
import com.jasson.tourAppMobile.ui.register.RegisterFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var user = User()
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val btnRegister: Button = root.findViewById(R.id.btnRegister)
        val btnLogin: Button = root.findViewById(R.id.btnLogin)
        val inputEmail: AppCompatEditText = root.findViewById(R.id.inputEmail)
        val inputPassword: AppCompatEditText = root.findViewById(R.id.inputPassword)
        val topText: TextView = root.findViewById(R.id.topText)
        val textViewAccount: TextView = root.findViewById(R.id.textViewAccount)
        val btnLogout: Button = root.findViewById(R.id.logoutBtn)
        val userNameView: TextView = root.findViewById(R.id.nameView)
        sharedPreferences = activity?.getSharedPreferences("PREFERNCES", Context.MODE_PRIVATE)
        if (sharedPreferences?.getString("Username", "Unknown") != "Unknown") {
            setLoginVisibility(
                btnRegister, btnLogin, inputEmail, inputPassword, userNameView,
                topText, textViewAccount, false, root.context, btnLogout,
                sharedPreferences?.getString("Username", "Unknown")!!
            )
        }

        val retrofit = Retrofit.Builder().baseUrl("https://5ae448e79a28.ngrok.io/tourApi/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val jsonPlaceHolderApi = retrofit.create(
            JsonPlaceHolderApi::class.java
        )

        btnLogin.setOnClickListener {
            login(
                inputEmail, inputPassword, jsonPlaceHolderApi, btnRegister, userNameView,
                btnLogin, topText, textViewAccount, root.context, btnLogout
            )
        }

        btnLogout.setOnClickListener {
            sharedPreferences?.edit()?.remove("Username")?.apply()
            setLoginVisibility(
                btnRegister, btnLogin, inputEmail, inputPassword, userNameView,
                topText, textViewAccount, true, root.context, btnLogout, ""
            )
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
                checkLogin(inputEmail, inputPassword, btnLogin, root.context)

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
                checkLogin(inputEmail, inputPassword, btnLogin, root.context)
            }

        })

        btnRegister.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavigationProfileToRegisterFragment()
            findNavController().navigate(action)
        }
        return root
    }

    fun setLoginVisibility(
        btnRegister: Button,
        btnLogin: Button,
        inputEmail: AppCompatEditText,
        inputPassword: AppCompatEditText,
        userNameView: TextView,
        topText: TextView,
        textViewAccount: TextView,
        visible: Boolean,
        context: Context,
        btnLogout: Button,
        name: String
    ) {
        btnRegister.isVisible = visible
        btnLogin.isVisible = visible
        inputEmail.isVisible = visible
        inputPassword.isVisible = visible
        textViewAccount.isVisible = visible
        btnLogout.isVisible = !visible
        userNameView.isVisible = !visible
        topText.text = if (visible) context.getText(R.string.title_already)
        else context.getText(R.string.title_welcome)
        if (!visible) {
            userNameView.text = "${context.getText(R.string.title_name)}: $name"
        }
    }

    fun login(
        inputEmail: AppCompatEditText,
        inputPassword: AppCompatEditText,
        jsonPlaceHolderApi: JsonPlaceHolderApi,
        btnRegister: Button,
        userNameView: TextView,
        btnLogin: Button,
        topText: TextView,
        textViewAccount: TextView,
        context: Context,
        btnLogout: Button
    ) {
        user.email = inputEmail.text.toString()
        user.password = inputPassword.text.toString()
        var call = jsonPlaceHolderApi.getUser(user)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (!response.isSuccessful) {
                    Toast.makeText(
                        context,
                        context.getText(R.string.conn_failed),
                        Toast.LENGTH_LONG
                    )
                        .show();
                    return
                }
                user = response.body()!!
                if (user.name != "") {
                    sharedPreferences =
                        activity?.getSharedPreferences("PREFERNCES", Context.MODE_PRIVATE)
                    var editor = sharedPreferences?.edit()
                    editor?.putString("Username", user.name)
                    editor?.apply()
                    setLoginVisibility(
                        btnRegister, btnLogin, inputEmail, inputPassword, userNameView,
                        topText, textViewAccount, false, context, btnLogout, user.name
                    )
                } else {
                    Toast.makeText(
                        context,
                        context.getText(R.string.user_not_exists),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(
                    context,
                    context.getText(R.string.user_not_exists),
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        })
    }

}