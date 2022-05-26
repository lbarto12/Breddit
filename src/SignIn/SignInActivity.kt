package com.example.breddit.SignIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.breddit.MainActivity
import com.example.breddit.R
import com.example.breddit.Util.FirestoreTools
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initUI()
    }

    fun initUI(){

        sign_in_button.setOnClickListener {
            FirestoreTools.credentialsValid(sign_in_username.text.toString(),
                sign_in_password.text.toString()){ valid ->
                if (valid) finish()
                else {
                    Toast.makeText(this,
                        "Incorrect username / password",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

        sign_in_create_account.setOnClickListener {
            val intent = Intent(this, CreateNewAccountActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {

    }
}