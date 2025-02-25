package com.esgi4al.discooker.ui.viewModels

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esgi4al.discooker.models.ListableUser
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.repositories.UserProfileDataRepository
import kotlinx.coroutines.launch

class UserProfileViewModel(private val userDataRepo: UserProfileDataRepository, val context: LifecycleOwner): ViewModel() {
    private val _userData = MutableLiveData<ListableUser?>()
    private val _userRecipes = MutableLiveData<List<Recipe>>()

    val userData: MutableLiveData<ListableUser?> get() = _userData
    val userRecipes: MutableLiveData<List<Recipe>> get() = _userRecipes

    fun getUserData(userId: String) {
        viewModelScope.launch {
            try {
                val userData = userDataRepo.getUserData(userId)
                _userData.postValue(userData)
            } catch (e: Exception) {
                Log.e("UserProfileViewModel", "Erreur inattendue lors de la récupération de les infos utilisateur: ${e.message}")
            }
        }
    }
}