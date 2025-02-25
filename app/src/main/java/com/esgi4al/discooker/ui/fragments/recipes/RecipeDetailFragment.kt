package com.esgi4al.discooker.ui.fragments.recipes

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R
import coil3.load
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.ui.recyclerViewAdapters.recipes.CommentsAdapter
import com.esgi4al.discooker.ui.viewModels.RecipeDetailViewModel

class RecipeDetailFragment : Fragment() {

    companion object {
        fun newInstance(recipeId: String): RecipeDetailFragment {
            val fragment = RecipeDetailFragment()
            val args = Bundle()
            args.putString("recipe_id", recipeId)
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: RecipeDetailViewModel by viewModels()
    private var recipeId: String? = null
    private lateinit var commentInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeId = arguments?.getString("recipe_id")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.recipe.observe(viewLifecycleOwner) { recipe -> updateUI(recipe)}
        viewModel.isLiked.observe(viewLifecycleOwner) { isLiked -> updateLiked(isLiked)}

        recipeId?.let {
            viewModel.getRecipeDetails(recipeId!!)
            viewModel.isRecipeLiked(recipeId!!, requireContext())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_recipe_detail, container, false)
        commentInput = view.findViewById(R.id.comment_input_et)
        return view
    }

    private fun updateLiked(isLiked: Boolean) {
        val followTextView = view?.findViewById<TextView>(R.id.follow_tv)
        followTextView?.text = if (isLiked) "Suivi" else "Suivre"

        followTextView?.setOnClickListener {
            val newIsLiked = !isLiked
            updateLiked(newIsLiked)
            recipeId?.let {
                viewModel.toggleLikeRecipe(recipeId!!, requireContext(), newIsLiked)
            }
        }
    }

    private fun updateUI(recipe: Recipe) {
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
        view?.findViewById<TextView>(R.id.recipe_region_tv)?.text = recipe.region.name
        view?.findViewById<TextView>(R.id.recipe_category_tv)?.text = recipe.category.name
        view?.findViewById<TextView>(R.id.recipe_instructions_tv)?.text = recipe.instructions.joinToString("\n") { it.instruction }

        val submitButton = view?.findViewById<Button>(R.id.submit_comment_btn)
        submitButton?.setOnClickListener {
            val comment = commentInput.text.toString()
            if (comment.isNotEmpty()) {
                recipeId?.let {
                    viewModel.postRecipeComment(recipeId!!, comment, requireContext())
                }
                commentInput.text.clear()
            }
        }
    }
}
