package com.vanshika.klf

import android.content.Context
import android.content.Intent
import android.graphics.drawable.PictureDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.vanshika.klf.model.Event
import com.vanshika.klf.model.MetaData
import com.vanshika.klf.svg.GlideApp
import com.vanshika.klf.svg.SvgSoftwareLayerSetter
import kotlin.Unit

class EventAdapter(
    private val onItemClick: (Event) -> Unit
) : ListAdapter<Event, EventAdapter.EventViewHolder>(EventDiffCallback()) {

    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventImageView: ImageView = view.findViewById(R.id.eventImageView)
        val eventTitleTextView: TextView = view.findViewById(R.id.eventTitleTextView)
        val eventDateTimeTextView: TextView = view.findViewById(R.id.eventDateTimeTextView)
        val eventLocationTextView: TextView = view.findViewById(R.id.eventLocationTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_attendance, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)

        holder.eventTitleTextView.text = event.title

        val startDate = event.metaData?.startDate ?: ""
        val startTime = event.metaData?.startTime ?: ""
        holder.eventDateTimeTextView.text = "$startDate | $startTime"

        val location = event.metaData?.address ?: "Location not available"
        holder.eventLocationTextView.text = location

        event.image?.let { imageUrl ->
            if (imageUrl.endsWith(".svg", ignoreCase = true)) {
                loadSvgIntoImageView(holder.eventImageView, imageUrl)
            } else {
                Glide.with(holder.itemView.context)
                    .load(imageUrl)
                    .into(holder.eventImageView)
            }
        }

        holder.itemView.setOnClickListener {
            // Invoke the callback passed in constructor
            onItemClick(event)

            // Or start Activity directly here (if preferred)
            // val context = holder.itemView.context
            // val intent = Intent(context, EventDetailActivity::class.java).apply {
            //    putExtra("event_id", event.id)
            // }
            // context.startActivity(intent)
        }
    }

    private fun loadSvgIntoImageView(imageView: ImageView, url: String) {
        GlideApp.with(imageView.context)
            .asResource(PictureDrawable::class.java)
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(SvgSoftwareLayerSetter())
            .load(url)
            .into(imageView)
    }
}
