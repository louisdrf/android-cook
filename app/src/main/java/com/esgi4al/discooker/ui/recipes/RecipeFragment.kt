package com.esgi4al.discooker.ui.recipes
import androidx.fragment.app.Fragment
import com.esgi4al.discooker.R
import android.os.Bundle
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup

class RecipeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }
}
