package com.esgi4al.discooker.ui.recyclerViewAdapters.account

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.ui.interfaces.UserCreatedRecipeClickHandler
import com.esgi4al.discooker.ui.viewHolders.list_items.AccountCreatedRecipeViewHolder

class AccountRecipeAdapter(
    private var recipes: List<Recipe>,
    private val clickHandler: UserCreatedRecipeClickHandler
) : RecyclerView.Adapter<AccountCreatedRecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountCreatedRecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.account_item_recipe, parent, false)
        return AccountCreatedRecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountCreatedRecipeViewHolder, position: Int) {
        val recipe = recipes[position]

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
            clickHandler.onRecipeClick(recipe._id)
        }

        holder.deleteRecipeButton.setOnClickListener {
            clickHandler.onDeleteRecipeButtonClick(recipe._id)
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}

