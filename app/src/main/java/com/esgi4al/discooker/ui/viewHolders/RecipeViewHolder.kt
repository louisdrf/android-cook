package com.esgi4al.discooker.ui.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R

class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val userProfileImage: ImageView = itemView.findViewById(R.id.userProfileImage)
    val userName: TextView = itemView.findViewById(R.id.userName)
    val recipeImage: ImageView = itemView.findViewById(R.id.recipeImage)
    val recipeTitle: TextView = itemView.findViewById(R.id.recipeTitle)

    val recipeCategoryName: TextView = itemView.findViewById(R.id.recipeCategoryName)
    val recipeCategoryImage: ImageView = itemView.findViewById(R.id.recipeCategoryImage)

    val recipeRegionName: TextView = itemView.findViewById(R.id.recipeRegionName)
    val recipeRegionImage: ImageView = itemView.findViewById(R.id.recipeRegionImage)

    val recipeLikes: TextView = itemView.findViewById(R.id.recipeLikes)
}