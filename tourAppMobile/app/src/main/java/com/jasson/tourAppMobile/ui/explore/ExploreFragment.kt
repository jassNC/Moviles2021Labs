package com.jasson.tourAppMobile.ui.explore

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jasson.tourAppMobile.R
import com.jasson.tourAppMobile.model.Tour
import com.jasson.tourAppMobile.helpers.SelectDateFragment
import com.jasson.tourAppMobile.helpers.loadJSONFromAsset
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset


class ExploreFragment : Fragment(), TourSelectionListener {

    private lateinit var exploreViewModel: ExploreViewModel
    private var tourList = ArrayList<Tour>()
    private  var customAdapter: CustomAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        exploreViewModel = ViewModelProvider(this).get(ExploreViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_explore, container, false)
        val checkInDate: EditText = root.findViewById(R.id.editTextChkIn)
        val checkOutDate: EditText = root.findViewById(R.id.editTextChkOut)
        val recyclerView: RecyclerView = root.findViewById(R.id.my_recycler_view)


        checkInDate.setOnClickListener {
            val newFragment: DialogFragment = SelectDateFragment(checkInDate)
            newFragment.show(parentFragmentManager, "DatePicker")
        }
        checkOutDate.setOnClickListener {
            val newFragment: DialogFragment = SelectDateFragment(checkOutDate)
            newFragment.show(parentFragmentManager, "DatePicker")
        }

        loadTours()

        recyclerView.layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        customAdapter = CustomAdapter(tourList, this)
        recyclerView.adapter = customAdapter

        return root
    }

    fun loadTours(){
        try {
            val obj =
                JSONObject(loadJSONFromAsset(requireActivity().
                assets.open("tours.json"))!!)
            val tourArray = obj.getJSONArray("tours")
            for (i in 0 until tourArray.length()) {
                val tourIndex = tourArray.getJSONObject(i)
                tourList.add(Tour(tourIndex))
            }
            Log.d("aaaa", tourList.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun onTourSelected(tourIndex: Int) {

    }
}