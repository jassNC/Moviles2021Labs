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
import com.jasson.tourAppMobile.R
import com.jasson.tourAppMobile.model.Tour
import com.jasson.tourAppMobile.utils.SelectDateFragment
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset


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

        val but: Button = root.findViewById(R.id.filterBtn)
        but.setOnClickListener{
            try {
                val obj = JSONObject(loadJSONFromAsset())
                val tourArray = obj.getJSONArray("tours")
                val tourList = ArrayList<Tour>()
                var m_li: HashMap<String, String>
                for (i in 0 until tourArray.length()) {
                    val tourIndex = tourArray.getJSONObject(i)
                    /*Log.d("Details-->", tourIndex.getString("formule"))
                    Log.d("Details-->", tourIndex.getString("url"))
                    val formula_value = tourIndex.getString("formule")
                    val url_value = tourIndex.getString("url")
                    */
                     val myTour = Tour(
                             tourIndex.getInt("id"),
                             tourIndex.getString("name")
                     )

                    //Add your values in your `ArrayList` as below:
                    /*m_li = HashMap()
                    m_li["formule"] = formula_value
                    m_li["url"] = url_value
                    */

                    tourList.add(myTour)
                }
                Log.d("aaaa",tourList.toString())
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        return root
    }

    fun loadJSONFromAsset(): String? {
        var json: String? = null
        try {
            val inp: InputStream = requireActivity().assets.open("tours.json")
            val size: Int = inp.available()
            val buffer = ByteArray(size)
            inp.read(buffer)
            inp.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }


}