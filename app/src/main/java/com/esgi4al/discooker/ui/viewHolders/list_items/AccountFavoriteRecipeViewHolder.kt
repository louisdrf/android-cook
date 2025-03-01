package com.esgi4al.discooker.ui.viewHolders.list_items

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R

class AccountFavoriteRecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val userDataCard: CardView = itemView.findViewById(R.id.accountFavoriteRecipeCreatorHeaderCard)
    val userProfileImage: ImageView = itemView.findViewById(R.id.accountFavoriteUserProfileImage)
    val userName: TextView = itemView.findViewById(R.id.accountFavoriteUserName)
    val recipeImage: ImageView = itemView.findViewById(R.id.accountFavoriteRecipeImage)
    val recipeTitle: TextView = itemView.findViewById(R.id.accountFavoriteRecipeTitle)

    val recipeCategoryName: TextView = itemView.findViewById(R.id.accountFavoriteRecipeCategoryName)
    val recipeCategoryImage: ImageView = itemView.findViewById(R.id.accountFavoriteRecipeCategoryImage)

    val recipeRegionName: TextView = itemView.findViewById(R.id.accountFavoriteRecipeRegionName)
    val recipeRegionImage: ImageView = itemView.findViewById(R.id.accountFavoriteRecipeRegionImage)

    val recipeLikes: TextView = itemView.findViewById(R.id.accountFavoriteRecipeNbLikes)
    val recipeComments: TextView = itemView.findViewById(R.id.accountFavoriteRecipeNbComments)

    val deleteFavoriteButton: Button = itemView.findViewById(R.id.accountFavoriteDeleteButton)
}