package com.esgi4al.discooker.ui.viewModels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.esgi4al.discooker.repositories.UsersListDataRepository

class UsersListViewModel(
    private val globalDataRepo: UsersListDataRepository,
    val context: LifecycleOwner
): ViewModel() {
}