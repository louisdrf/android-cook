package com.esgi4al.discooker.ui.fragments.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.ApiResponseGetLikedRecipes
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountFavoritesFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeFavoriteAdapter: RecipeFavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_favorite, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recipeFavoriteAdapter = RecipeFavoriteAdapter(
            recipes = emptyList(),
            onFavoriteClicked = { recipe -> removeLikedRecipe(recipe) },
            showFavoriteButton = true
        )
        recyclerView.adapter = recipeFavoriteAdapter

        fetchLikedRecipes()

        return rootView
    }

    private fun fetchLikedRecipes() {
        val apiService = ApiClient.getAccountService()
        apiService.getLikedRecipesByUser().enqueue(object : Callback<ApiResponseGetLikedRecipes> {
            override fun onResponse(
                call: Call<ApiResponseGetLikedRecipes>,
                response: Response<ApiResponseGetLikedRecipes>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    apiResponse?.let {
                        recipeFavoriteAdapter.updateData(it.data)
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
        val apiService = ApiClient.getAccountService()
        apiService.unlikeRecipe(recipe._id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                isRemoving = false

                if (response.isSuccessful) {
                    val updatedRecipes = recipeFavoriteAdapter.currentList.filter { it._id != recipe._id }
                    recipeFavoriteAdapter.updateData(updatedRecipes)
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
