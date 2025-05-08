package com.vanshika.attendancescanner
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AttendanceAdapter(private val items: List<AttendanceItem>) :
    RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder>() {

    class AttendanceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val surnameTextView: TextView = view.findViewById(R.id.surnameTextView)
        val classTextView: TextView = view.findViewById(R.id.classTextView)
        val sectionTextView: TextView = view.findViewById(R.id.sectionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_attendance, parent, false)
        return AttendanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.name
        holder.surnameTextView.text = item.surname
        holder.classTextView.text = item.studentClass
        holder.sectionTextView.text = item.section
    }

    override fun getItemCount(): Int = items.size
}
