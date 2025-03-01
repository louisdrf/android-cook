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
import com.esgi4al.discooker.ui.shared.ToastUtils
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
        followTextView?.text = if (isLiked) "Supprimer des favoris" else "Ajouter aux favoris"

        followTextView?.setOnClickListener {
            val newIsLiked = !isLiked
            updateLiked(newIsLiked)
            recipeId?.let {
                viewModel.toggleLikeRecipe(recipeId!!, requireContext(), newIsLiked)
                val toastText = if (newIsLiked) "Recette ajoutée à vos favoris !" else "Recette supprimée de vos favoris."
                ToastUtils.showCustomToast(requireContext(), toastText, true)
            }
        }
    }

    private fun updateUI(recipe: Recipe) {
        val gridLayout = view?.findViewById<GridLayout>(R.id.recipe_ingredients)
        gridLayout?.removeAllViews()

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

        val instructionsContainer = view?.findViewById<LinearLayout>(R.id.recipe_instructions_container)
        instructionsContainer?.removeAllViews()

        recipe.instructions.forEach { instruction ->
            val instructionLayout = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { setMargins(0, 8, 0, 8) }
            }

            val stepTextView = TextView(context).apply {
                text = "${instruction.step}."
                textSize = 18f
                setTypeface(typeface, android.graphics.Typeface.BOLD)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { setMargins(0, 0, 16, 0) }
            }

            val instructionTextView = TextView(context).apply {
                text = instruction.instruction
                textSize = 18f
            }

            instructionLayout.addView(stepTextView)
            instructionLayout.addView(instructionTextView)
            instructionsContainer?.addView(instructionLayout)
        }


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
