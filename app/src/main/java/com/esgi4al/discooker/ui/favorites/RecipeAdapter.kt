package com.esgi4al.discooker.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.RecipeModel
import com.esgi4al.discooker.network.ApiClient
import com.esgi4al.discooker.ui.auth.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_favorite, container, false)

        // Déconnexion
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

        // RecyclerView setup
        recyclerView = rootView.findViewById(R.id.recipesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recipeAdapter = RecipeAdapter(emptyList())
        recyclerView.adapter = recipeAdapter

        fetchRecipes()

        return rootView
    }

    private fun fetchRecipes() {
        val apiService = ApiClient.getApiService()
        apiService.getRecipes().enqueue(object : Callback<List<RecipeModel>> {
            override fun onResponse(call: Call<List<RecipeModel>>, response: Response<List<RecipeModel>>) {
                if (response.isSuccessful) {
                    response.body()?.let { recipes ->
                        recipeAdapter.updateData(recipes)
                    }
                } else {
                    Toast.makeText(requireContext(), "Erreur de chargement des recettes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<RecipeModel>>, t: Throwable) {
                Toast.makeText(requireContext(), "Échec de la connexion au serveur", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
