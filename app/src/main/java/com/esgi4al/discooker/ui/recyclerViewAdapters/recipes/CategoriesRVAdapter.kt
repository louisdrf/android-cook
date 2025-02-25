package com.esgi4al.discooker.ui.recyclerViewAdapters.recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.Category
import com.esgi4al.discooker.ui.interfaces.HomePageItemsClickHandler
import com.esgi4al.discooker.ui.viewHolders.list_items.ListableCategoryViewHolder

class CategoriesRVAdapter(
    private val categories: List<Category>,
    private val clickHandler: HomePageItemsClickHandler
): RecyclerView.Adapter<ListableCategoryViewHolder>() {

    private var selectedPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListableCategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listable_category, parent, false)
        return ListableCategoryViewHolder(view)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: ListableCategoryViewHolder, position: Int) {
        val category = categories[position]

        Glide
            .with(holder.itemView)
            .load(category.imgUrl)
            .into(holder.categoryImage)

        holder.categoryName.text = category.name

        holder.closeCategoryIcon.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            selectedPosition = holder.getAdapterPosition()
            notifyDataSetChanged()
            clickHandler.onCategoryClick(category.name)
        }

        holder.closeCategoryIcon.setOnClickListener {
            selectedPosition = null
            notifyDataSetChanged()
            clickHandler.onCategoryCloseIconClick()
        }
    }

}