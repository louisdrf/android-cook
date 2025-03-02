package com.esgi4al.discooker.ui.fragments.account

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.esgi4al.discooker.ui.fragments.users.UserProfileFragment
import com.esgi4al.discooker.ui.interfaces.FragmentNavigation
import com.esgi4al.discooker.ui.interfaces.UserFavoriteRecipeClickHandler
import com.esgi4al.discooker.ui.recyclerViewAdapters.account.AccountFavoriteRecipeAdapter
import com.esgi4al.discooker.ui.shared.ToastUtils.showCustomToast
import com.esgi4al.discooker.ui.viewModels.AccountFavoritesViewModel
import com.esgi4al.discooker.ui.viewModels.factories.ViewModelFactory
import kotlinx.coroutines.launch


class AccountFavoritesFragment : Fragment(),
    UserFavoriteRecipeClickHandler {

    private lateinit var favoritesRv: RecyclerView
    private var fragmentNavigation: FragmentNavigation? = null

    private val viewModel: AccountFavoritesViewModel by viewModels {
        val accountDataService: AccountService = ApiClient.getAccountService()
        val repository = AccountDataRepository(accountDataService)
        ViewModelFactory(repository, this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            fragmentNavigation = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.likedRecipes.observe(viewLifecycleOwner) { recipes ->
            setupFavoritesRecyclerView(recipes, view)
        }

        viewModel.getUserLikedRecipes()
    }

    private fun setupFavoritesRecyclerView(favorites: List<Recipe>, fragmentView: View) {
        favoritesRv = fragmentView.findViewById(R.id.recyclerViewFavorites)
        favoritesRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        favoritesRv.adapter = AccountFavoriteRecipeAdapter(favorites, this)
    }

    override fun onDeleteFavoriteButtonClick(recipeId: String) {
        lifecycleScope.launch {
            val success = viewModel.deleteFavoriteRecipe(recipeId)
            showCustomToast(requireContext(),
                if (success) "Recette supprimée des favoris." else "Erreur lors de la suppression.",
                success
            )
        }
    }


    override fun onRecipeClick(recipeId: String) {
        fragmentNavigation?.loadFragment(RecipeDetailFragment.newInstance(recipeId))
    }

    override fun onRecipeUserClick(userId: String) {
        fragmentNavigation?.loadFragment(UserProfileFragment.newInstance(userId))
    }
}
