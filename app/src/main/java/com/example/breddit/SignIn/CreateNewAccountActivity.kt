package com.example.breddit.SignIn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.breddit.MainActivity
import com.example.breddit.R
import com.example.breddit.Util.FirestoreTools
import kotlinx.android.synthetic.main.activity_create_new_account.*

class CreateNewAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_account)

        initUI()
    }

    private fun initUI(){
        create_new_account_button.setOnClickListener {
            val username = create_new_account_username_1.text.toString()
            val password = create_new_account_password.text.toString()
            val confirm = create_new_account_password_confirm.text.toString()

            Log.d("CREATENEWACCOUNT", "$username $password $confirm")

            if (password != confirm) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirestoreTools.userExists(username){ exists ->
                if (!exists){
                    FirestoreTools.createUser(
                        username, password
                    )
                    MainActivity.me = username
                    finish()
                }
                else{
                    Toast.makeText(this, "User Already Exists", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}