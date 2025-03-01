package com.esgi4al.discooker.ui.viewHolders.list_items

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R

class AccountCreatedRecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val recipeImage: ImageView = itemView.findViewById(R.id.accountRecipeImage)
    val recipeTitle: TextView = itemView.findViewById(R.id.accountRecipeTitle)

    val recipeCategoryName: TextView = itemView.findViewById(R.id.accountRecipeCategoryName)
    val recipeCategoryImage: ImageView = itemView.findViewById(R.id.accountRecipeCategoryImage)

    val recipeRegionName: TextView = itemView.findViewById(R.id.accountRecipeRegionName)
    val recipeRegionImage: ImageView = itemView.findViewById(R.id.accountRecipeRegionImage)

    val recipeLikes: TextView = itemView.findViewById(R.id.accountRecipeNbLikes)
    val recipeComments: TextView = itemView.findViewById(R.id.accountRecipeNbComments)

    val deleteRecipeButton: Button = itemView.findViewById(R.id.accountRecipeDeleteButton)
}