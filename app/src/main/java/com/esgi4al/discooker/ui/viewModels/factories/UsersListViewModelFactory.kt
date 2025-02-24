package com.esgi4al.discooker.ui.viewModels.factories

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esgi4al.discooker.repositories.UsersListDataRepository
import com.esgi4al.discooker.ui.viewModels.UsersListViewModel

class UsersListViewModelFactory(
    private val repository: UsersListDataRepository,
    private val fragment: Fragment
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersListViewModel::class.java)) {
            return UsersListViewModel(repository, fragment) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}