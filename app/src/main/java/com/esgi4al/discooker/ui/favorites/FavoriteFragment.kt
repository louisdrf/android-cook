package com.esgi4al.discooker.ui.favorites
import androidx.fragment.app.Fragment
import com.esgi4al.discooker.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }
}
