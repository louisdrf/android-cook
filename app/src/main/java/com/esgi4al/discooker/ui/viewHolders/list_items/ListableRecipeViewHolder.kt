package com.esgi4al.discooker.ui.viewHolders.list_items

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.models.User
import com.esgi4al.discooker.ui.interfaces.UserItemClickHandler

class ListableRecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val userDataCard : CardView = itemView.findViewById(R.id.recipeCreatorHeaderCard)
    private val userProfileImage: ImageView = itemView.findViewById(R.id.userProfileImage)
    private val userName: TextView = itemView.findViewById(R.id.userName)
    val recipeImage: ImageView = itemView.findViewById(R.id.recipeImage)
    val recipeTitle: TextView = itemView.findViewById(R.id.recipeTitle)

    val recipeCategoryName: TextView = itemView.findViewById(R.id.recipeCategoryName)
    val recipeCategoryImage: ImageView = itemView.findViewById(R.id.recipeCategoryImage)

    val recipeRegionName: TextView = itemView.findViewById(R.id.recipeRegionName)
    val recipeRegionImage: ImageView = itemView.findViewById(R.id.recipeRegionImage)

    val recipeLikes: TextView = itemView.findViewById(R.id.recipeNbLikes)
    val recipeComments: TextView = itemView.findViewById(R.id.recipeNbComments)

    fun bind(recipe: Recipe, showUserInfo: Boolean=true) {
        if (showUserInfo)
            loadUserInfos(recipe.user)
         else
            userDataCard.visibility = View.GONE
    }

    private fun loadUserInfos(user: User) {
        userDataCard.visibility = View.VISIBLE
        userName.text = user.username
        Glide
            .with(this.itemView)
            .load("https://robohash.org/${user.username}")
            .into(this.userProfileImage)
    }
}