package com.jasson.tourAppMobile.ui.register

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hbb20.CountryCodePicker
import com.jasson.tourAppMobile.R
import com.jasson.tourAppMobile.helpers.SelectDateFragment
import com.jasson.tourAppMobile.helpers.formatDate
import com.jasson.tourAppMobile.model.User
import kotlin.collections.ArrayList

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_register, container, false)

        val btnReg: Button = root.findViewById(R.id.btnReg)
        val inputName: EditText = root.findViewById(R.id.inputName)
        val inputSurnames: EditText = root.findViewById(R.id.inputSurnameRegister)
        val inputPassword: AppCompatEditText = root.findViewById(R.id.inputPasswordRegister)
        val inputEmail: AppCompatEditText = root.findViewById(R.id.inputEmailRegister)
        val inputCountry: CountryCodePicker = root.findViewById(R.id.inputCountry)
        val birthDateField: EditText = root.findViewById(R.id.birthDateField)

        registerViewModel.status.observe(viewLifecycleOwner, {
            if(registerViewModel.logged.value!!) {
                Toast.makeText(root.context, R.string.user_created, Toast.LENGTH_SHORT).show()
                val action = RegisterFragmentDirections.actionRegisterFragmentToNavigationProfile()
                findNavController().navigate(action)
            }else if (registerViewModel.status.value=="connError"){
                Toast.makeText(root.context, R.string.conn_failed, Toast.LENGTH_SHORT).show()
            }else if (registerViewModel.status.value=="notFound"){
                Toast.makeText(root.context, R.string.user_not_created, Toast.LENGTH_SHORT).show()
            }
        })

        btnReg.setOnClickListener {
            register(
                inputName, inputSurnames, inputEmail, inputPassword,
                inputCountry, birthDateField
            )
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
        inputCountry: CountryCodePicker,
        birthDateField: EditText,
    ) {
        val user = User(
            1,
            "${name.text} ${surnames.text}",
            email.text.toString(),
            formatDate(birthDateField.text.toString()),
            password.text.toString(),
            inputCountry.selectedCountryName,
            ArrayList()
        )
        registerViewModel.registerUser(user);
    }
}