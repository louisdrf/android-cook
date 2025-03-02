package com.esgi4al.discooker.ui.recyclerViewAdapters.recipeDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.Ingredient

class IngredientsAdapter(private var ingredients: List<Ingredient>, private val onDelete: (Ingredient) -> Unit) : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    class IngredientsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientsTextView: TextView = view.findViewById(R.id.ingredient_text_tv)
        val ingredientsQuantityInput: EditText = view.findViewById(R.id.quantityInput)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return IngredientsViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.ingredientsTextView.text = ingredient.name
        holder.ingredientsQuantityInput.setText(ingredient.quantity)

        holder.itemView.setOnLongClickListener {
            onDelete(ingredient)
            true
        }

        holder.ingredientsQuantityInput.addTextChangedListener { text ->
            ingredient.quantity = text.toString()
        }
    }

    override fun getItemCount() = ingredients.size

}
