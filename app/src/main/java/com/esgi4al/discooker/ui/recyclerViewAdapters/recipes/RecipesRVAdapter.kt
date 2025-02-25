package com.esgi4al.discooker.ui.recyclerViewAdapters.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.ui.interfaces.HomePageItemsClickHandler
import com.esgi4al.discooker.ui.interfaces.UserItemClickHandler
import com.esgi4al.discooker.ui.viewHolders.list_items.ListableRecipeViewHolder


class RecipesRVAdapter(
    private val recipes: List<Recipe>,
    private val recipeClickHandler: HomePageItemsClickHandler,
    private val userClickHandler: UserItemClickHandler
    ) : RecyclerView.Adapter<ListableRecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListableRecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listable_recipe, parent, false)
        return ListableRecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListableRecipeViewHolder, position: Int) {
        val recipe = this.recipes[position]
        holder.bind(recipe)

        Glide.with(holder.itemView).load(recipe.thumbnail)
            .apply(RequestOptions()
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
            recipeClickHandler.onRecipeClick(recipe._id)
        }

        holder.userDataCard.setOnClickListener {
            userClickHandler.onUserClick(recipe.user._id)
        }
    }

    override fun getItemCount(): Int = recipes.size
}