package com.esgi4al.discooker.ui.shared

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.esgi4al.discooker.R

object ToastUtils {
    fun showCustomToast(context: Context, message: String, isSuccess: Boolean) {
        val layoutInflater = LayoutInflater.from(context)
        val layout: View = layoutInflater.inflate(R.layout.custom_toast, null)

        val toastIcon = layout.findViewById<ImageView>(R.id.toast_icon)
        val toastText = layout.findViewById<TextView>(R.id.toast_text)

        toastText.text = message
        if (isSuccess) {
            toastIcon.setImageResource(R.drawable.ic_success)
            layout.setBackgroundResource(R.drawable.toast_background_success)
        } else {
            toastIcon.setImageResource(R.drawable.ic_error)
            layout.setBackgroundResource(R.drawable.toast_background_error)
        }

        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }
}