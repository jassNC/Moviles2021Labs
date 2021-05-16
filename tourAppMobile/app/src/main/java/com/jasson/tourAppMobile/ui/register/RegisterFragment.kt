package com.jasson.tourAppMobile.ui.register

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jasson.tourAppMobile.R
import com.jasson.tourAppMobile.helpers.SelectDateFragment
import com.jasson.tourAppMobile.ui.profile.ProfileViewModel
import java.util.*

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
        val inputName: AppCompatEditText = root.findViewById(R.id.inputName)
        val inputSurnames: AppCompatEditText = root.findViewById(R.id.inputSurnameRegister)
        val inputPassword: AppCompatEditText = root.findViewById(R.id.inputPasswordRegister)
        val inputEmail: AppCompatEditText = root.findViewById(R.id.inputEmailRegister)
        val inputCountry: AppCompatEditText = root.findViewById(R.id.inputCountry)
        val inputBirth: AppCompatEditText = root.findViewById(R.id.inputBirth)

        inputBirth.setOnClickListener{
            val newFragment: DialogFragment = SelectDateFragment(inputBirth)
            newFragment.show(parentFragmentManager, "DatePicker")
        }

        //inputEmail.addTextChangedListener(object : TextWatcher {
            //override fun afterTextChanged(s: Editable) {}
           // override fun beforeTextChanged(
             //   s: CharSequence, start: Int,
             //   count: Int, after: Int
           // ) {
           // }

           // @RequiresApi(Build.VERSION_CODES.M)
           // override fun onTextChanged(
               // s: CharSequence, start: Int,
               // before: Int, count: Int
           // ) {
            //    checkValues(inputEmail, inputPassword, btnLogin, root.context)
            //}

        //})
        return root
    }
}