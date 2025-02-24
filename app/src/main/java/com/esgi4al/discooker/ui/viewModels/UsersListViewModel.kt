package com.esgi4al.discooker.ui.viewModels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esgi4al.discooker.models.ListableUser
import com.esgi4al.discooker.repositories.UsersListDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersListViewModel(
    private val globalUsersDataRepo: UsersListDataRepository,
    val context: LifecycleOwner
): ViewModel() {

    private val _users = MutableLiveData<List<ListableUser>>()
    val users: LiveData<List<ListableUser>> get() = _users

    init {
        fetchAllUsers()
    }

    private fun fetchAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedUsers = globalUsersDataRepo.getAllUsers() ?: emptyList()
            _users.postValue(fetchedUsers)
        }
    }

}