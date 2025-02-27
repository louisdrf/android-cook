package com.esgi4al.discooker.ui.viewModels.factories

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esgi4al.discooker.repositories.CreateRecipeGlobalDataRepository
import com.esgi4al.discooker.ui.viewModels.CreateRecipeViewModel

class CreateRecipeViewModelFactory(
    private val repository: CreateRecipeGlobalDataRepository,
    private val fragment: Fragment
): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateRecipeViewModel::class.java)) {
            return CreateRecipeViewModel(repository, fragment) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}