package com.esgi4al.discooker.ui.recyclerViewAdapters.recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.Region
import com.esgi4al.discooker.ui.interfaces.HomePageItemsClickHandler
import com.esgi4al.discooker.ui.viewHolders.list_items.ListableRegionViewHolder

class RegionsRVAdapter(
    private val regions: List<Region>,
    private val clickHandler: HomePageItemsClickHandler
): RecyclerView.Adapter<ListableRegionViewHolder>() {

    private var selectedPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListableRegionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listable_region, parent, false)
        return ListableRegionViewHolder(view)
    }

    override fun getItemCount(): Int = regions.size

    override fun onBindViewHolder(holder: ListableRegionViewHolder, position: Int) {
        val region = regions[position]

        Glide
            .with(holder.itemView)
            .load(region.imgUrl)
            .into(holder.regionImage)

        holder.regionName.text = region.name

        holder.closeRegionIcon.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            selectedPosition = holder.getAdapterPosition()
            notifyDataSetChanged()
            clickHandler.onRegionClick(region.name)
        }

        holder.closeRegionIcon.setOnClickListener {
            selectedPosition = null
            notifyDataSetChanged()
            clickHandler.onRegionCloseIconClick()
        }
    }
}