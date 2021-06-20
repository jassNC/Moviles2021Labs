package com.jasson.tourAppMobile.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jasson.tourAppMobile.R
import com.jasson.tourAppMobile.helpers.TourAppApi
import com.jasson.tourAppMobile.helpers.checkLogin
import com.jasson.tourAppMobile.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModels()
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val btnRegister: Button = root.findViewById(R.id.btnRegister)
        val btnLogin: Button = root.findViewById(R.id.btnLogin)
        val inputEmail: AppCompatEditText = root.findViewById(R.id.inputEmail)
        val inputPassword: AppCompatEditText = root.findViewById(R.id.inputPassword)
        val topText: TextView = root.findViewById(R.id.topText)
        val textViewAccount: TextView = root.findViewById(R.id.textViewAccount)
        val btnLogout: Button = root.findViewById(R.id.logoutBtn)
        val userNameView: TextView = root.findViewById(R.id.nameView)

        profileViewModel.user.observe(viewLifecycleOwner, {
            if (profileViewModel.user.value!!.name != "") {
                Toast.makeText(root.context, R.string.title_welcome, Toast.LENGTH_SHORT).show()
                val editor = sharedPreferences?.edit()
                editor?.putString("username", profileViewModel.user.value!!.name)
                editor?.apply()
                val action = ProfileFragmentDirections.actionNavigationProfileToNavigationExplore()
                findNavController().navigate(action)
            }
            if (profileViewModel.status.value=="notFound"){
                Toast.makeText(root.context, R.string.user_not_exists, Toast.LENGTH_SHORT).show()
            }else if(profileViewModel.status.value=="connError"){
                Toast.makeText(root.context, R.string.conn_failed, Toast.LENGTH_SHORT).show()
            }
            var username = sharedPreferences?.getString("username", "unknown")
            setLoginVisibility(
                btnRegister, btnLogin, inputEmail, inputPassword, userNameView,
                topText, textViewAccount, username == "unknown", root.context, btnLogout,
                username!!
            )
        })

        btnLogin.setOnClickListener {
            login(inputEmail, inputPassword)
        }

        btnLogout.setOnClickListener {
            sharedPreferences?.edit()?.remove("username")?.apply()
            profileViewModel.logout()
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
        inputPassword: AppCompatEditText
    ) {
        var user = User();
        user.email = inputEmail.text.toString()
        user.password = inputPassword.text.toString()
        this.profileViewModel.loginUser(user)
    }

}