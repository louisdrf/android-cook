package com.esgi4al.discooker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import com.esgi4al.discooker.ui.auth.LoginActivity
import com.esgi4al.discooker.ui.fragments.HomePageFragment
import com.esgi4al.discooker.ui.fragments.users.UsersListFragment
import com.esgi4al.discooker.ui.interfaces.FragmentNavigation

class MainActivity : AppCompatActivity(), FragmentNavigation {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)

        if (token == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        loadFragment(HomePageFragment())

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_recipe -> {
                    loadFragment(HomePageFragment())
                    true
                }
                R.id.navigation_account -> {
                    loadFragment(UsersListFragment())
                    true
                }
                else -> false
            }
        }
    }

    override fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
