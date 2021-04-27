package com.jasson.tourAppMobile.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jasson.tourAppMobile.R

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
        /*
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        profileViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

         */
        return root
    }
}