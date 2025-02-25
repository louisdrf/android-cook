package com.esgi4al.discooker.ui.recyclerViewAdapters.account

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.Recipe

class AccountRecipeAdapter(
    private var recipes: List<Recipe>
) : RecyclerView.Adapter<AccountRecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.recipeTitle)
        val descriptionTextView: TextView = view.findViewById(R.id.recipeDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.account_item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.titleTextView.text = recipe.title
        holder.descriptionTextView.text = recipe.description
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun updateData(newRecipes: List<Recipe>) {
        Log.d("RecipeAdapter", "Nouvelle liste de recettes : $newRecipes")
        recipes = newRecipes
        notifyDataSetChanged()
    }
}

