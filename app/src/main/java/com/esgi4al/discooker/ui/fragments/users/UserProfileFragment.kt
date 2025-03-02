package com.esgi4al.discooker.ui.fragments.users

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.bumptech.glide.Glide
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.ListableUser
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.repositories.UserProfileDataRepository
import com.esgi4al.discooker.service.ApiClient
import com.esgi4al.discooker.service.UserService
import com.esgi4al.discooker.ui.fragments.recipes.RecipeDetailFragment
import com.esgi4al.discooker.ui.interfaces.FragmentNavigation
import com.esgi4al.discooker.ui.interfaces.UserRecipeClickHandler
import com.esgi4al.discooker.ui.recyclerViewAdapters.recipes.UserProfileRecipesRvAdapter
import com.esgi4al.discooker.ui.viewModels.UserProfileViewModel
import com.esgi4al.discooker.ui.viewModels.factories.ViewModelFactory

class UserProfileFragment: Fragment(), UserRecipeClickHandler {

    private val viewModel: UserProfileViewModel by viewModels {
        val userService: UserService = ApiClient.getUserService()
        val repository = UserProfileDataRepository(userService)
        ViewModelFactory(repository, this)
    }
    private var fragmentNavigation: FragmentNavigation? = null
    private lateinit var userRecipesRv: RecyclerView
    private lateinit var userNoRecipeTv: TextView
    private lateinit var userCreatedRecipesTitleTV: TextView

    companion object {
        fun newInstance(userId: String): UserProfileFragment {
            val fragment = UserProfileFragment()
            val args = Bundle()
            args.putString("userId", userId)
            fragment.arguments = args
            return fragment
        }
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
    ): View {
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)
        userNoRecipeTv = view.findViewById(R.id.userProfileNoRecipeTv)
        userCreatedRecipesTitleTV = view.findViewById(R.id.recipesTitle)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getString("userId") ?: return

        viewModel.getUserData(userId)
        viewModel.getUserRecipes(userId)

        var username: String? = null

        viewModel.userData.observe(viewLifecycleOwner) { userData ->
            if (userData != null) {
                username = userData.username
            }
            setUpUserCardData(userData, view)
        }

        viewModel.userRecipes.observe(viewLifecycleOwner) { recipes ->
            if (recipes.isEmpty()) {
                val nameToDisplay = username ?: "Cet utilisateur"
                userNoRecipeTv.text = "$nameToDisplay n'a pas encore créé de recette"
                userNoRecipeTv.visibility = View.VISIBLE
            } else {
                userNoRecipeTv.visibility = View.GONE
                userCreatedRecipesTitleTV.visibility = View.VISIBLE
            }
            setUpUserRecipesRv(recipes, view)
        }
    }

    private fun setUpUserRecipesRv(recipes: List<Recipe>, fragmentView: View) {
        userRecipesRv = fragmentView.findViewById(R.id.user_profile_recipes_rv)
        userRecipesRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        userRecipesRv.adapter = UserProfileRecipesRvAdapter(recipes, this)
    }

    private fun setUpUserCardData(userData: ListableUser?, fragmentView: View) {
        userData?.let {
            val userNameTv: TextView = fragmentView.findViewById(R.id.profileUserName)
            val userLikesTv: TextView = fragmentView.findViewById(R.id.profileUserCumulatedLikes)
            val userRecipesTv: TextView = fragmentView.findViewById(R.id.profileUserCumulatedRecipes)
            val userProfileImage: ImageView = fragmentView.findViewById(R.id.profileUserImage)

            userNameTv.text = userData.username
            userLikesTv.text = userData.nbLikes.toString()
            userRecipesTv.text = userData.nbRecipes.toString()

            Glide.with(this)
                .load("https://robohash.org/${userData.username}")
                .placeholder(R.drawable.person_24px)
                .error(R.drawable.person_24px)
                .into(userProfileImage)
        }
    }

    override fun onUserRecipeClick(recipeId: String) {
        fragmentNavigation?.loadFragment(RecipeDetailFragment.newInstance(recipeId))
    }
}