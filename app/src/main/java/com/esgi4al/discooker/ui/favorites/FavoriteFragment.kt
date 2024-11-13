package com.esgi4al.discooker.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.esgi4al.discooker.R
import com.esgi4al.discooker.ui.auth.LoginActivity

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_favorite, container, false)

        val logoutButton: Button = rootView.findViewById(R.id.logout_button)

        logoutButton.setOnClickListener {
            val sharedPreferences = requireActivity().getSharedPreferences("app_prefs", AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove("auth_token")
            editor.apply()

            val loginIntent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(loginIntent)
            requireActivity().finish()
        }

        return rootView
    }
}
