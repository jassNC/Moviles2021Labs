package com.jasson.tourAppMobile.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jasson.tourAppMobile.R
import com.jasson.tourAppMobile.ui.helpers.SelectDateFragment


class ExploreFragment : Fragment() {

    private lateinit var exploreViewModel: ExploreViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        exploreViewModel =
            ViewModelProvider(this).get(ExploreViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_explore, container, false)
        /*
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        exploreViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

         */
        val checkInDate: EditText = root.findViewById(R.id.editTextChkIn)
        val checkOutDate: EditText = root.findViewById(R.id.editTextChkOut)

        checkInDate.setOnClickListener{
            val newFragment: DialogFragment = SelectDateFragment(checkInDate)
            newFragment.show(parentFragmentManager, "DatePicker")
        }
        checkOutDate.setOnClickListener{
            val newFragment: DialogFragment = SelectDateFragment(checkOutDate)
            newFragment.show(parentFragmentManager, "DatePicker")
        }

        return root
    }


}