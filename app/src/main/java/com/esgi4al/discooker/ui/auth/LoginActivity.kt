package com.esgi4al.discooker.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.esgi4al.discooker.MainActivity
import com.esgi4al.discooker.R
import com.esgi4al.discooker.models.auth.LoginRequest
import com.esgi4al.discooker.service.ApiClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login_button)
        val registerButton = findViewById<Button>(R.id.register_button)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            login(username, password)
        }

        registerButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(username: String, password: String) {
        val loginRequest = LoginRequest(username = username, password = password)
        lifecycleScope.launch {
            try {
                val response: Response<ResponseBody> = ApiClient.getAuthService().login(loginRequest)
                Log.d("coderesponse", "Response Body: ${response}")

                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    if (responseBody != null) {
                        val authToken = extractAuthToken(responseBody)

                        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("auth_token", authToken)
                        editor.apply()

                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        showErrorDialog("Login Failed", "No data found in the response.")
                    }
                } else {
                    showErrorDialog("Login Failed", "Invalid username or password. Please try again.")
                }
            } catch (e: Exception) {
                showErrorDialog("Error", "Failed to connect. Please check your internet connection and try again.")
            }
        }
    }

    private fun extractAuthToken(responseBody: String): String {
        val jsonObject = JSONObject(responseBody)
        return jsonObject.getString("accessToken")
    }


    private fun showErrorDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(this@LoginActivity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
