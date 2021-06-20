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
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import java.lang.Exception

class CustomAdapter(
    var tourList: ArrayList<Tour>,
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
                val poster = itemView.findViewById(R.id.tourPoster) as ImageView
                val rating = itemView.findViewById(R.id.ratingBar) as RatingBar
                val opinions = itemView.findViewById(R.id.textViewOpinions) as TextView
                val price = itemView.findViewById(R.id.textViewPrice) as TextView
                val duration = itemView.findViewById(R.id.durationText) as TextView
                titleTextView.text = tour.name
                rating.numStars = tour.rating
                opinions.text = "${tour.reviews.size} ${itemView.context.getString(R.string.reviews)}"
                price.text = "${itemView.context.getString(R.string.price)}${tour.price}"
                loadRemoteImage(tour.image,poster)
                duration.text = "${tour.duration} ${itemView.context.getString(R.string.hours)}"
            } catch (ex: Exception) {

            }

        }

        private fun loadRemoteImage(url: String, imageView: ImageView){
            imageView.run{
                Picasso.get().load(url).fetch()
                Picasso.get().load(url)
                    .resize(800,500)
                    .error(R.drawable.vulcan)
                    .into(this)
            }
        }
    }
}