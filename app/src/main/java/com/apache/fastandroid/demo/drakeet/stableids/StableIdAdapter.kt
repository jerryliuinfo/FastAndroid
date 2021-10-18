package com.microsoft.sample.stableids

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R

class StableIdAdapter(val items: MutableList<Item>) : RecyclerView.Adapter<StableIdAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.drakket_stableids_item, parent, false))
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = items[position]
    holder.subjectView.text = item.subject
    holder.summaryView.text = item.summary
    Log.d("StableIdAdapter", "onBindViewHolder: $position: ${item.subject} - ${item.summary}")
  }

  override fun getItemCount(): Int = items.size

  // If the data don't have an id field, it's OK to just use hashcode:
  // items[position].hashCode().toLong()
  override fun getItemId(position: Int): Long = items[position].id

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val subjectView: TextView = itemView.findViewById(R.id.subject)
    val summaryView: TextView = itemView.findViewById(R.id.summary)
  }
}
