package com.esgi4al.discooker.ui.fragments.account

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.esgi4al.discooker.R
import com.esgi4al.discooker.ui.auth.LoginActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AccountFragment : Fragment(R.layout.fragment_account) {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)

        val adapter = AccountPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.favorites)
                1 -> getString(R.string.recipes)
                else -> null
            }
        }.attach()

        val logoutButton: Button = view.findViewById(R.id.logout_button_account)
        logoutButton.setOnClickListener {
            val sharedPreferences = requireActivity().getSharedPreferences(
                "app_prefs",
                AppCompatActivity.MODE_PRIVATE
            )
            sharedPreferences.edit().remove("auth_token").apply()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private inner class AccountPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> AccountFavoritesFragment()
                1 -> RecipeFragment()
                else -> throw IllegalStateException("Invalid position")
            }
        }
    }
}
