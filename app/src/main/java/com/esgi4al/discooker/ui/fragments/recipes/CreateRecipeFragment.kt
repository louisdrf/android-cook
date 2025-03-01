package com.esgi4al.discooker.ui.fragments.recipes

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.Category
import com.esgi4al.discooker.models.Ingredient
import com.esgi4al.discooker.models.Instruction
import com.esgi4al.discooker.models.Region
import com.esgi4al.discooker.repositories.CreateRecipeGlobalDataRepository
import com.esgi4al.discooker.service.ApiClient
import com.esgi4al.discooker.service.GlobalDataService
import com.esgi4al.discooker.service.RecipeService
import com.esgi4al.discooker.ui.interfaces.FragmentNavigation
import com.esgi4al.discooker.ui.recipe.IngredientsAdapter
import com.esgi4al.discooker.ui.recipe.InstructionsAdapter
import com.esgi4al.discooker.ui.viewModels.CreateRecipeViewModel
import com.esgi4al.discooker.ui.viewModels.factories.CreateRecipeViewModelFactory


class CreateRecipeFragment : Fragment() {
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var submitButton: Button
    private lateinit var selectedCategory: Category
    private lateinit var selectedRegion: Region
    private var currentSearch: String? = ""
    private var fragmentNavigation: FragmentNavigation? = null
    private var searchRunnable: Runnable? = null
    private val searchHandler = Handler(Looper.getMainLooper())
    private var isFirstSelection = true

    private val viewModel: CreateRecipeViewModel by viewModels() {
        val globalDataService: GlobalDataService = ApiClient.getGlobalDataService()
        val recipeService: RecipeService = ApiClient.getRecipeService()
        CreateRecipeViewModelFactory(CreateRecipeGlobalDataRepository(globalDataService, recipeService), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_add_recipe, container, false)
        searchView = view.findViewById(R.id.searchBarIngredients)
        submitButton = view.findViewById(R.id.submitButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSearchView()
        setUpSubmitButton()

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            updateCategories(categories)
        }
        viewModel.regions.observe(viewLifecycleOwner) { regions ->
            updateRegions(regions)
        }
        viewModel.instructions.observe(viewLifecycleOwner) { instructions ->
            updateInstructions(instructions)
        }

        viewModel.ingredients.observe(viewLifecycleOwner) { ingredients ->
            updateIngredients(ingredients)
        }

        viewModel.searchIngredients.observe(viewLifecycleOwner) { searchIngredients ->
            updateSearchIngredients(searchIngredients)
        }

        viewModel.createdRecipeId.observe(viewLifecycleOwner) { recipeId ->
            recipeId?.let {
                Log.e("ZBLEUG", it)
                fragmentNavigation?.loadFragment(RecipeDetailFragment.newInstance(it))
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            fragmentNavigation = context
        }
    }

    private fun setUpSearchView() {
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                currentSearch = newText

                searchRunnable?.let { searchHandler.removeCallbacks(it) }

                searchRunnable = Runnable {
                    viewModel.fetchIngredientsBySearch(newText)
                }

                searchHandler.postDelayed(searchRunnable!!, 200)
                return true
            }
        })
    }

    private fun setUpSubmitButton() {
        val title = view?.findViewById<TextView>(R.id.titleInput)
        val description = view?.findViewById<TextView>(R.id.descriptionInput)
        submitButton.setOnClickListener {
            viewModel.submitRecipe(title?.text.toString(), description?.text.toString(), selectedCategory, selectedRegion)
        }
    }

    private fun updateCategories(categories: List<Category>) {
        val spinner = view?.findViewById<Spinner>(R.id.categorySpinner)
        val adapter = spinner?.let {
            ArrayAdapter(
                it.context,
                android.R.layout.simple_spinner_item,
                categories
            )
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCategory = parent.getItemAtPosition(position) as Category
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun updateRegions(regions: List<Region>) {
        val spinner = view?.findViewById<Spinner>(R.id.regionSpinner)
        val adapter = spinner?.let {
            ArrayAdapter(
                it.context,
                android.R.layout.simple_spinner_item,
                regions
            )
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedRegion = parent.getItemAtPosition(position) as Region
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }

    private fun updateInstructions(instructions: List<Instruction>) {
        val instructionInput = view?.findViewById<EditText>(R.id.instructionInput)
        val addInstructionButton = view?.findViewById<Button>(R.id.addInstructionButton)
        val instructionsRecyclerView = view?.findViewById<RecyclerView>(R.id.instructions_rv)

        instructionsRecyclerView?.layoutManager = LinearLayoutManager(context)
        instructionsRecyclerView?.adapter = InstructionsAdapter(instructions) { instruction ->
            viewModel.removeInstruction(instruction)
        }

        addInstructionButton?.setOnClickListener {
            val instructionText = instructionInput?.text.toString()
            if (instructionText.isNotEmpty()) {
                viewModel.addInstruction(instructionText)
                instructionInput?.text?.clear()
            }
        }
    }

    private fun updateIngredients(ingredients: List<Ingredient>) {
        val ingredientsRecyclerView = view?.findViewById<RecyclerView>(R.id.ingredients_rv)

        ingredientsRecyclerView?.layoutManager = LinearLayoutManager(context)
        ingredientsRecyclerView?.adapter = IngredientsAdapter(ingredients) { ingredient ->
            viewModel.removeIngredient(ingredient)
        }
    }

    private fun updateSearchIngredients(ingredients: List<Ingredient>) {
        val spinner = view?.findViewById<Spinner>(R.id.ingredientSpinner)
        val adapter = spinner?.let {
            ArrayAdapter(
                it.context,
                android.R.layout.simple_spinner_item,
                ingredients.map { it.name }
            )
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapter

        isFirstSelection = true
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (isFirstSelection) {
                    isFirstSelection = false
                    return
                }

                val selectedIngredient = ingredients[position]
                viewModel.addIngredient(selectedIngredient)
                adapter?.notifyDataSetChanged()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}
