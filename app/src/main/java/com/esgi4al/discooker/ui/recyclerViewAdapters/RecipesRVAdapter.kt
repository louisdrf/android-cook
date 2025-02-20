package com.esgi4al.discooker.ui.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.ui.viewHolders.RecipeViewHolder

class RecipesRVAdapter(private val recipes: List<Recipe>) : RecyclerView.Adapter<RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listable_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = this.recipes[position]

        Glide
            .with(holder.itemView)
            .load(recipe.thumbnail)
            .into(holder.recipeImage)

        Glide
            .with(holder.itemView)
            .load("https://robohash.org/${recipe.user.username}")
            .into(holder.userProfileImage)

        holder.userName.text = recipe.user.username
        holder.recipeTitle.text = recipe.title
        holder.recipeCategory.text = recipe.category
        holder.recipeRegion.text = recipe.region
        holder.recipeDescription.text = recipe.description
        holder.recipeLikes.text = buildString {
            append(recipe.likes.size.toString())
            append(" likes")
        }
    }

    override fun getItemCount(): Int = recipes.size
}