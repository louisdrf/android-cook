package com.esgi4al.discooker.ui.recyclerViewAdapters.account

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.ui.interfaces.HomePageItemsClickHandler
import com.esgi4al.discooker.ui.interfaces.UserFavoriteRecipeClickHandler
import com.esgi4al.discooker.ui.interfaces.UserItemClickHandler
import com.esgi4al.discooker.ui.viewHolders.list_items.AccountFavoriteRecipeViewHolder
import com.esgi4al.discooker.ui.viewHolders.list_items.ListableRecipeViewHolder
import com.google.android.material.button.MaterialButton

class AccountFavoriteRecipeAdapter(
    private var recipes: List<Recipe>,
    private val favoriteClickHandler: UserFavoriteRecipeClickHandler
) : RecyclerView.Adapter<AccountFavoriteRecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountFavoriteRecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.account_favorite_item_recipe, parent, false)
        return AccountFavoriteRecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountFavoriteRecipeViewHolder, position: Int) {
        val recipe = this.recipes[position]

        holder.userName.text = recipe.user.username
        Glide
            .with(holder.itemView)
            .load("https://robohash.org/${recipe.user.username}")
            .into(holder.userProfileImage)

        Glide.with(holder.itemView).load(recipe.thumbnail)
            .apply(
                RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_recipeplaceholder))
            .into(holder.recipeImage)

        holder.recipeTitle.text = recipe.title

        holder.recipeCategoryName.text = recipe.category.name
        holder.recipeRegionName.text = recipe.region.name

        Glide.with(holder.itemView).load(recipe.category.imgUrl).into(holder.recipeCategoryImage)
        Glide.with(holder.itemView).load(recipe.region.imgUrl).into(holder.recipeRegionImage)

        recipe.likes.size.toString().also { holder.recipeLikes.text = it }
        recipe.comments.size.toString().also { holder.recipeComments.text = it }

        holder.itemView.setOnClickListener {
            favoriteClickHandler.onRecipeClick(recipe._id)
        }

        holder.userDataCard.setOnClickListener {
            favoriteClickHandler.onRecipeUserClick(recipe.user._id)
        }

        holder.deleteFavoriteButton.setOnClickListener {
            favoriteClickHandler.onDeleteFavoriteButtonClick(recipe._id)
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}
