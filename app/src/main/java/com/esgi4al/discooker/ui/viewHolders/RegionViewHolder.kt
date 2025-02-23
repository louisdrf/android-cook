package com.esgi4al.discooker.ui.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R

class RegionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val regionName: TextView = itemView.findViewById(R.id.regionName)
    val regionImage: ImageView = itemView.findViewById(R.id.regionImage)
    val closeRegionIcon: ImageView = itemView.findViewById(R.id.closeRegionIcon)
}