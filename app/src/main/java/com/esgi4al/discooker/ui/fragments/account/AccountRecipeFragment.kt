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
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.service.ApiClient
import com.esgi4al.discooker.ui.recyclerViewAdapters.account.AccountRecipeAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountRecipeFragment : Fragment(R.layout.fragment_recipe) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeAdapter: AccountRecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_recipe, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerViewRecipes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recipeAdapter = AccountRecipeAdapter(emptyList())
        recyclerView.adapter = recipeAdapter

        fetchRecipes()

        return rootView
    }

    private fun fetchRecipes() {
        val apiService = ApiClient.getAccountService()
        apiService.getUserRecipes().enqueue(object : Callback<List<Recipe>> {
            override fun onResponse(
                call: Call<List<Recipe>>,
                response: Response<List<Recipe>>,
            ) {
                if (response.isSuccessful) {
                    val recipes = response.body()
                    recipes?.let {
                        recipeAdapter.updateData(it)
                        Log.d("recipeFragment", "Recettes récupérées avec succès.")
                    }
                } else {
                    val responseCode = response.code()
                    val responseMessage = response.message()
                    Log.e("recipeFragment", "Erreur de réponse: $responseCode - $responseMessage")
                }
            }

            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                Log.e("recipeFragment", "Erreur de connexion: ${t.message}")
            }
        })
    }

}
