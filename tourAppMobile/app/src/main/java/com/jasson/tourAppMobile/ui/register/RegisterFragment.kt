package com.jasson.tourAppMobile.ui.register

import android.content.Context
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
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hbb20.CountryCodePicker
import com.jasson.tourAppMobile.R
import com.jasson.tourAppMobile.helpers.JsonPlaceHolderApi
import com.jasson.tourAppMobile.helpers.SelectDateFragment
import com.jasson.tourAppMobile.helpers.formatDate
import com.jasson.tourAppMobile.model.User
import com.jasson.tourAppMobile.ui.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_register.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerViewModel =
            ViewModelProvider(this).get(RegisterViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_register, container, false)

        val btnReg: Button = root.findViewById(R.id.btnReg)
        val inputName: EditText = root.findViewById(R.id.inputName)
        val inputSurnames: EditText = root.findViewById(R.id.inputSurnameRegister)
        val inputPassword: AppCompatEditText = root.findViewById(R.id.inputPasswordRegister)
        val inputEmail: AppCompatEditText = root.findViewById(R.id.inputEmailRegister)
        val inputCountry: CountryCodePicker = root.findViewById(R.id.inputCountry)
        val birthDateField: EditText = root.findViewById(R.id.birthDateField)

        //birthDateField.text.isEmpty()
        //inputCountry.selectedCountryName

        val retrofit = Retrofit.Builder().baseUrl("https://5ae448e79a28.ngrok.io/tourApi/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val jsonPlaceHolderApi = retrofit.create(
            JsonPlaceHolderApi::class.java
        )

        btnReg.setOnClickListener {
            register(
                inputName, inputSurnames, inputEmail, inputPassword, btnReg,
                inputCountry, birthDateField, root.context, jsonPlaceHolderApi
            )
            val action = RegisterFragmentDirections.actionRegisterFragmentToNavigationProfile()
            findNavController().navigate(action)
        }

        birthDateField.setOnClickListener {
            val newFragment: DialogFragment = SelectDateFragment(birthDateField)
            newFragment.show(parentFragmentManager, "DatePicker")
        }

        inputName.addTextChangedListener(object : TextWatcher {
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
                checkValues(
                    inputName,
                    inputSurnames,
                    inputEmail,
                    inputPassword,
                    btnReg,
                    root.context
                )
            }

        })

        inputSurnames.addTextChangedListener(object : TextWatcher {
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
                checkValues(
                    inputName,
                    inputSurnames,
                    inputEmail,
                    inputPassword,
                    btnReg,
                    root.context
                )
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
                checkValues(
                    inputName,
                    inputSurnames,
                    inputEmail,
                    inputPassword,
                    btnReg,
                    root.context
                )
            }

        })

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
                checkValues(
                    inputName,
                    inputSurnames,
                    inputEmail,
                    inputPassword,
                    btnReg,
                    root.context
                )
            }

        })

        return root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkValues(
        name: EditText,
        surnames: EditText,
        email: AppCompatEditText,
        password: AppCompatEditText,
        btn: Button,
        context: Context
    ) {
        if (name.text.isNotEmpty() && surnames.text.isNotEmpty() &&
            email.text!!.matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")) &&
            password.text!!.matches(
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

    fun register(
        name: EditText,
        surnames: EditText,
        email: AppCompatEditText,
        password: AppCompatEditText,
        btn: Button,
        inputCountry: CountryCodePicker,
        birthDateField: EditText,
        context: Context,
        jsonPlaceHolderApi: JsonPlaceHolderApi
    ) {
        val user = User(
            1,
            "${name.text} ${surnames.text}",
            email.text.toString(),
            formatDate(birthDateField.text.toString()),
            password.text.toString(),
            inputCountry.defaultCountryName,
            ArrayList()
        )
        var call = jsonPlaceHolderApi.putUser(user)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (!response.isSuccessful) {
                    Toast.makeText(
                        context,
                        context.getText(R.string.conn_failed),
                        Toast.LENGTH_LONG
                    )
                        .show();
                    return
                }
                if (response.body()!!) {
                    Toast.makeText(
                        context,
                        context.getText(R.string.user_created),
                        Toast.LENGTH_LONG
                    )
                        .show();
                }

            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(context, context.getText(R.string.conn_failed), Toast.LENGTH_LONG)
                    .show();
            }
        })
    }
}