package com.jasson.tourAppMobile.ui.explore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jasson.tourAppMobile.R
import com.jasson.tourAppMobile.model.Tour
import java.lang.Exception

class CustomAdapter(
    private val tourList: ArrayList<Tour>,
    private val tourSelectionListener: TourSelectionListener
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_tour_cell,
                                                        parent, false)
        return ViewHolder(view, tourSelectionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindCell(tourList[position])
    }

    override fun getItemCount(): Int {
        return tourList.size
    }


    class ViewHolder(
        itemView: View,
        private val tourSelectionListener: TourSelectionListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            tourSelectionListener.onTourSelected(adapterPosition)
        }

        fun bindCell(tour: Tour) {
            try {
                val titleTextView = itemView.findViewById(R.id.textViewTourTitle) as TextView
                val datesTextView = itemView.findViewById(R.id.textViewDates) as TextView
                val poster = itemView.findViewById(R.id.tourPoster) as ImageView
                val rating = itemView.findViewById(R.id.ratingBar) as RatingBar

                titleTextView.text = tour.name
                datesTextView.text = "${tour.checkIn} - ${tour.checkOut}"
                rating.numStars = tour.rating
                poster.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.vulcan))

            } catch (ex: Exception) {

            }

        }
    }
}