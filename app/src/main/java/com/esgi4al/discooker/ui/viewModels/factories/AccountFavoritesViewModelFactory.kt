package com.esgi4al.discooker.ui.viewModels.factories

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esgi4al.discooker.repositories.account.AccountDataRepository
import com.esgi4al.discooker.ui.viewModels.AccountFavoritesViewModel

class AccountFavoritesViewModelFactory(
    private val repository: AccountDataRepository,
    private val fragment: Fragment
): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountFavoritesViewModel::class.java)) {
            return AccountFavoritesViewModel(repository, fragment) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}