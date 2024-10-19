package com.esgi4al.discooker.ui.recipe

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        view?.findViewById<TextView>(R.id.recipe_title_tv)?.text = recipe.strMeal
        view?.findViewById<TextView>(R.id.recipe_area_tv)?.text = recipe.strArea
        view?.findViewById<TextView>(R.id.recipe_category_tv)?.text = recipe.strCategory
        view?.findViewById<TextView>(R.id.recipe_instructions_tv)?.text = recipe.strInstructions
        view?.findViewById<ImageView>(R.id.recipe_thumb_iv)?.load(recipe.strMealThumb)
    }
}