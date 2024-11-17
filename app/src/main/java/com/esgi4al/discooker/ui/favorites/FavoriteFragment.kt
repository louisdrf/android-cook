package com.esgi4al.discooker.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.ApiResponseGetLikedRecipes
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.service.ApiClient
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
            val sharedPreferences = requireActivity().getSharedPreferences(
                "app_prefs",
                AppCompatActivity.MODE_PRIVATE
            )
            val editor = sharedPreferences.edit()
            editor.remove("auth_token")
            editor.apply()

            val loginIntent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(loginIntent)
            requireActivity().finish()
        }

        recyclerView = rootView.findViewById(R.id.recyclerViewRecipes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recipeAdapter = RecipeAdapter(emptyList()) { recipe ->
            removeLikedRecipe(recipe)
        }
        recyclerView.adapter = recipeAdapter

        fetchLikedRecipes()

        return rootView
    }

    private fun fetchLikedRecipes() {
        val apiService = ApiClient.getFavService()
        apiService.getLikedRecipesByUser().enqueue(object : Callback<ApiResponseGetLikedRecipes> {
            override fun onResponse(
                call: Call<ApiResponseGetLikedRecipes>,
                response: Response<ApiResponseGetLikedRecipes>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    apiResponse?.let {
                        recipeAdapter.updateData(it.data)
                        Log.d("FavoriteFragment", "Recettes récupérées avec succès.")
                    }
                } else {
                    val responseCode = response.code()
                    val responseMessage = response.message()
                    Log.e("FavoriteFragment", "Erreur de réponse: $responseCode - $responseMessage")
                }
            }

            override fun onFailure(call: Call<ApiResponseGetLikedRecipes>, t: Throwable) {
                Log.e("FavoriteFragment", "Erreur de connexion: ${t.message}")
            }
        })
    }



    private var isRemoving = false

    private fun removeLikedRecipe(recipe: Recipe) {
        if (isRemoving) {
            return
        }

        isRemoving = true
        val apiService = ApiClient.getFavService()
        apiService.unlikeRecipe(recipe._id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                isRemoving = false

                if (response.isSuccessful) {
                    val updatedRecipes = recipeAdapter.currentList.filter { it._id != recipe._id }
                    recipeAdapter.updateData(updatedRecipes)
                } else {
                    val responseCode = response.code()
                    Log.e("FavoriteFragment", "Erreur lors de la suppression : $responseCode")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                isRemoving = false
                Log.e("FavoriteFragment", "Échec de la connexion : ${t.message}")
            }
        })
    }



}
