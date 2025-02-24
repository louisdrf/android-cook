package com.esgi4al.discooker.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.esgi4al.discooker.R
import com.esgi4al.discooker.repositories.UsersListDataRepository
import com.esgi4al.discooker.ui.viewModels.UsersListViewModel
import com.esgi4al.discooker.ui.viewModels.factories.UsersListViewModelFactory

class UsersListFragment: Fragment() {

    private val usersListViewModel: UsersListViewModel by viewModels {
        UsersListViewModelFactory(UsersListDataRepository(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_users_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}