package com.esgi4al.discooker.ui.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val userProfileImage: ImageView = itemView.findViewById(R.id.listableUserProfileImage)
    val userName: TextView = itemView.findViewById(R.id.listableUserName)
    val userNbCumultedLikes: TextView = itemView.findViewById(R.id.listableUserCumulatedLikes)
    val userNbCumultedRecipes: TextView = itemView.findViewById(R.id.listableUserCumulatedRecipes)
}