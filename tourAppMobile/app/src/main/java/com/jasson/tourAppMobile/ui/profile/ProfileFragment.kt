package com.jasson.tourAppMobile.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jasson.tourAppMobile.R
import com.jasson.tourAppMobile.helpers.SelectDateFragment
import com.jasson.tourAppMobile.ui.register.RegisterFragment

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
        btnRegister.setOnClickListener{
            val action = ProfileFragmentDirections.actionNavigationProfileToRegisterFragment()
            findNavController().navigate(action)
        }
        return root
    }
}