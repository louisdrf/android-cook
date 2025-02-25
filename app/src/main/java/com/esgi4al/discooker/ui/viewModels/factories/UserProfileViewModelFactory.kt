package com.esgi4al.discooker.ui.viewModels.factories

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esgi4al.discooker.repositories.UserProfileDataRepository
import com.esgi4al.discooker.ui.viewModels.UserProfileViewModel

class UserProfileViewModelFactory(
    private val repository: UserProfileDataRepository,
    private val fragment: Fragment
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            return UserProfileViewModel(repository, fragment) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}