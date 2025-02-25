package com.esgi4al.discooker.ui.viewHolders.list_items

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R

class ListableCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val categoryName: TextView = itemView.findViewById(R.id.categoryName)
    val categoryImage: ImageView = itemView.findViewById(R.id.categoryImage)
    val closeCategoryIcon: ImageView = itemView.findViewById(R.id.closeCategoryIcon)
}