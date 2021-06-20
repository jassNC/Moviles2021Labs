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
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jasson.tourAppMobile.R
import com.jasson.tourAppMobile.model.Tour
import com.jasson.tourAppMobile.helpers.SelectDateFragment
import com.jasson.tourAppMobile.helpers.formatDate
import com.jasson.tourAppMobile.helpers.loadJSONFromAsset
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList


class ExploreFragment : Fragment(), TourSelectionListener {

    private val exploreViewModel: ExploreViewModel by viewModels()
    private var tourList = ArrayList<Tour>()
    private  var customAdapter: CustomAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_explore, container, false)
        val checkInDate: EditText = root.findViewById(R.id.editTextChkIn)
        val checkOutDate: EditText = root.findViewById(R.id.editTextChkOut)
        val recyclerView: RecyclerView = root.findViewById(R.id.my_recycler_view)
        val searchBtn: Button = root.findViewById(R.id.filterBtn)
        val cityText: EditText = root.findViewById(R.id.editTextCity)

        recyclerView.layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        customAdapter = CustomAdapter(tourList, this)
        recyclerView.adapter = customAdapter

        exploreViewModel.tours.observe(viewLifecycleOwner, {
            customAdapter!!.tourList = exploreViewModel.tours.value!!
            customAdapter!!.notifyDataSetChanged()
            Log.d("aa",customAdapter!!.tourList.toString())
            customAdapter!!.notifyDataSetChanged()
        })

        checkInDate.setOnClickListener {
            val newFragment: DialogFragment = SelectDateFragment(checkInDate)
            newFragment.show(parentFragmentManager, "DatePicker")
        }
        checkOutDate.setOnClickListener {
            val newFragment: DialogFragment = SelectDateFragment(checkOutDate)
            newFragment.show(parentFragmentManager, "DatePicker")
        }

        searchBtn.setOnClickListener{
            var tour = Tour()
            tour.city = cityText.text.toString()
            if(checkInDate.text.toString() == ""){
                tour.checkIn = "1999-01-01"
            }else {
                tour.checkIn = formatDate(checkInDate.text.toString())
            }
            if(checkOutDate.text.toString()==""){
                tour.checkOut = "4000-01-01"
            }else{
                tour.checkOut = formatDate(checkOutDate.text.toString())
            }
            this.exploreViewModel.searchTours(tour)
        }
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
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun onTourSelected(tourIndex: Int) {

    }
}