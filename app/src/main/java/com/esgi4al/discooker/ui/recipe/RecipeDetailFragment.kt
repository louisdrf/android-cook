package com.esgi4al.discooker.ui.recipe

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.RecipeModel
import coil3.load

class RecipeDetailFragment : Fragment() {

    companion object {
        fun newInstance() = RecipeDetailFragment()
    }

    private val viewModel: RecipeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.recipe.observe(this) { recipe -> updateUI(recipe)}

        viewModel.getRecipeDetails()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    private fun updateUI(recipe: RecipeModel) {
        val gridLayout = view?.findViewById<GridLayout>(R.id.recipe_ingredients)

        recipe.ingredients.forEach { ingredient ->
            val ingredientLayout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_HORIZONTAL
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
            }

            val imageView = ImageView(context).apply {
                val imageUrl = "https://www.themealdb.com/images/ingredients/${ingredient.name}.png"
                load(imageUrl)
                layoutParams = ViewGroup.LayoutParams(150, 150)
            }
            val ingredientNameTextView = TextView(context).apply {
                text = ingredient.name
                gravity = Gravity.CENTER
            }
            val ingredientQuantityTextView = TextView(context).apply {
                text = ingredient.quantity
                gravity = Gravity.CENTER
            }

            ingredientLayout.addView(imageView)
            ingredientLayout.addView(ingredientNameTextView)
            ingredientLayout.addView(ingredientQuantityTextView)

            gridLayout?.addView(ingredientLayout)
        }

        if (recipe.thumbnail.isNullOrEmpty()) {
            view?.findViewById<ImageView>(R.id.recipe_thumb_iv)?.setImageResource(R.drawable.ic_recipeplaceholder)
        } else {
            view?.findViewById<ImageView>(R.id.recipe_thumb_iv)?.load(recipe.thumbnail)
        }

        val commentsRecyclerView = view?.findViewById<RecyclerView>(R.id.recipe_comments_rv)
        commentsRecyclerView?.layoutManager = LinearLayoutManager(context)
        commentsRecyclerView?.adapter = CommentsAdapter(recipe.comments)

        view?.findViewById<TextView>(R.id.recipe_title_tv)?.text = recipe.title
        view?.findViewById<TextView>(R.id.recipe_region_tv)?.text = recipe.region
        view?.findViewById<TextView>(R.id.recipe_category_tv)?.text = recipe.category
        view?.findViewById<TextView>(R.id.recipe_instructions_tv)?.text = recipe.instructions.joinToString("\n") { it.instruction }
    }
}
