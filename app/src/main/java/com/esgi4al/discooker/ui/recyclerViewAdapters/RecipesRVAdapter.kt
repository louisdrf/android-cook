package com.esgi4al.discooker.ui.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.ui.interfaces.HomePageItemsClickHandler
import com.esgi4al.discooker.ui.viewHolders.RecipeViewHolder


class RecipesRVAdapter(
    private val recipes: List<Recipe>,
    private val recipeClickHandler: HomePageItemsClickHandler
    ) : RecyclerView.Adapter<RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listable_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = this.recipes[position]

        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.ic_recipeplaceholder)

        Glide
            .with(holder.itemView)
            .load(recipe.thumbnail)
            .apply(requestOptions)
            .into(holder.recipeImage)

        Glide
            .with(holder.itemView)
            .load("https://robohash.org/${recipe.user.username}")
            .into(holder.userProfileImage)

        holder.userName.text = buildString {
            append("Créée par ")
            append(recipe.user.username)
        }
        holder.recipeTitle.text = recipe.title

        holder.recipeCategoryName.text = recipe.category.name
        Glide
            .with(holder.itemView)
            .load(recipe.category.imgUrl)
            .into(holder.recipeCategoryImage)

        holder.recipeRegionName.text = recipe.region.name
        Glide
            .with(holder.itemView)
            .load(recipe.region.imgUrl)
            .into(holder.recipeRegionImage)

        recipe.likes.size.toString().also { holder.recipeLikes.text = it }
        recipe.comments.size.toString().also { holder.recipeComments.text = it }

        holder.itemView.setOnClickListener {
            recipeClickHandler.onRecipeClick(recipe._id)
        }
    }

    override fun getItemCount(): Int = recipes.size
}