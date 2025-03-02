package com.esgi4al.discooker.ui.viewModels.factories

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esgi4al.discooker.repositories.CreateRecipeGlobalDataRepository
import com.esgi4al.discooker.repositories.HomePageGlobalDataRepository
import com.esgi4al.discooker.repositories.UserProfileDataRepository
import com.esgi4al.discooker.repositories.UsersListDataRepository
import com.esgi4al.discooker.repositories.account.AccountDataRepository
import com.esgi4al.discooker.ui.viewModels.AccountFavoritesViewModel
import com.esgi4al.discooker.ui.viewModels.AccountRecipesViewModel
import com.esgi4al.discooker.ui.viewModels.CreateRecipeViewModel
import com.esgi4al.discooker.ui.viewModels.HomePageViewModel
import com.esgi4al.discooker.ui.viewModels.RecipeDetailViewModel
import com.esgi4al.discooker.ui.viewModels.UserProfileViewModel
import com.esgi4al.discooker.ui.viewModels.UsersListViewModel

class ViewModelFactory(
    private val repository: Any,
    private val fragment: Fragment
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomePageViewModel::class.java) -> {
                HomePageViewModel(repository as HomePageGlobalDataRepository, fragment) as T
            }
            modelClass.isAssignableFrom(CreateRecipeViewModel::class.java) -> {
                CreateRecipeViewModel(repository as CreateRecipeGlobalDataRepository, fragment) as T
            }
            modelClass.isAssignableFrom(AccountRecipesViewModel::class.java) -> {
                AccountRecipesViewModel(repository as AccountDataRepository, fragment) as T
            }
            modelClass.isAssignableFrom(AccountFavoritesViewModel::class.java) -> {
                AccountFavoritesViewModel(repository as AccountDataRepository, fragment) as T
            }
            modelClass.isAssignableFrom(UserProfileViewModel::class.java) -> {
                UserProfileViewModel(repository as UserProfileDataRepository, fragment) as T
            }
            modelClass.isAssignableFrom(UsersListViewModel::class.java) -> {
                UsersListViewModel(repository as UsersListDataRepository, fragment) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.simpleName}")
        }
    }
}