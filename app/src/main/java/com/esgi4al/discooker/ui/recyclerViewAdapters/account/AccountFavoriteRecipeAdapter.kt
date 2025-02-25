package com.esgi4al.discooker.ui.recyclerViewAdapters.account

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.Recipe
import com.google.android.material.button.MaterialButton

class AccountFavoriteRecipeAdapter(
    private var recipes: List<Recipe>,
    private val onFavoriteClicked: (Recipe) -> Unit,
    private val showFavoriteButton: Boolean
) : RecyclerView.Adapter<AccountFavoriteRecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.recipeTitle)
        val descriptionTextView: TextView = view.findViewById(R.id.recipeDescription)
        val favoriteButton: MaterialButton = view.findViewById(R.id.favoriteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.account_favorite_item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.titleTextView.text = recipe.title
        holder.descriptionTextView.text = recipe.description

        if (showFavoriteButton) {
            holder.favoriteButton.visibility = View.VISIBLE
            holder.favoriteButton.setOnClickListener {
                onFavoriteClicked(recipe)
            }
        } else {
            holder.favoriteButton.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    val currentList: List<Recipe>
        get() = recipes

    fun updateData(newRecipes: List<Recipe>) {
        Log.d("RecipeAdapterFavorites", "Nouvelle liste de recettes : $newRecipes")
        recipes = newRecipes
        notifyDataSetChanged()
    }
}
