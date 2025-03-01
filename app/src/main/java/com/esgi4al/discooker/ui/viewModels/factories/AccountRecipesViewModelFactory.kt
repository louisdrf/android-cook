package com.esgi4al.discooker.ui.viewModels.factories

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esgi4al.discooker.repositories.account.AccountDataRepository
import com.esgi4al.discooker.ui.viewModels.AccountRecipesViewModel

class AccountRecipesViewModelFactory(
    private val repository: AccountDataRepository,
    private val fragment: Fragment
): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountRecipesViewModel::class.java)) {
            return AccountRecipesViewModel(repository, fragment) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}