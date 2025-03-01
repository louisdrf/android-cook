package com.esgi4al.discooker.ui.fragments.account

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.repositories.account.AccountDataRepository
import com.esgi4al.discooker.service.AccountService
import com.esgi4al.discooker.service.ApiClient
import com.esgi4al.discooker.ui.fragments.recipes.RecipeDetailFragment
import com.esgi4al.discooker.ui.interfaces.FragmentNavigation
import com.esgi4al.discooker.ui.interfaces.UserCreatedRecipeClickHandler
import com.esgi4al.discooker.ui.recyclerViewAdapters.account.AccountRecipeAdapter
import com.esgi4al.discooker.ui.shared.ToastUtils.showCustomToast
import com.esgi4al.discooker.ui.viewModels.AccountRecipesViewModel
import com.esgi4al.discooker.ui.viewModels.factories.AccountRecipesViewModelFactory
import kotlinx.coroutines.launch

class AccountRecipeFragment : Fragment(), UserCreatedRecipeClickHandler {

    private lateinit var createdRecipesRV: RecyclerView
    private var fragmentNavigation: FragmentNavigation? = null

    private val viewModel: AccountRecipesViewModel by viewModels {
        val accountDataService: AccountService = ApiClient.getAccountService()
        AccountRecipesViewModelFactory(AccountDataRepository(accountDataService), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account_recipes, container, false)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            fragmentNavigation = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserCreatedRecipes()

        viewModel.createdRecipes.observe(viewLifecycleOwner) { recipes ->
            setupCreatedRecipesRecyclerView(recipes, view)
        }
    }

    private fun setupCreatedRecipesRecyclerView(createdRecipes: List<Recipe>, fragmentView: View) {
        createdRecipesRV = fragmentView.findViewById(R.id.accountCreatedRecipesRv)
        createdRecipesRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        createdRecipesRV.adapter = AccountRecipeAdapter(createdRecipes, this)
    }

    override fun onRecipeClick(recipeId: String) {
        fragmentNavigation?.loadFragment(RecipeDetailFragment.newInstance(recipeId))
    }

    override fun onDeleteRecipeButtonClick(recipeId: String) {
        lifecycleScope.launch {
            val success = viewModel.deleteRecipe(recipeId)
            showCustomToast(requireContext(),
                if (success) "Recette supprimée." else "Erreur lors de la suppression.",
                success
            )
        }
    }
}
