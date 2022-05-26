package com.example.breddit.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.breddit.R
import com.example.breddit.DataClasses.Post
import com.example.breddit.MainActivity
import com.example.breddit.Util.FirestoreTools
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        initUI()
    }

    fun initUI(){
        create_post_create_post_button.setOnClickListener {
            val title = create_post_title_edittext.text.toString()
            val content = create_post_content_edittext.text.toString()

            FirestoreTools.createConversation(Post(
                MainActivity.me,
                MainActivity.me,
                MainActivity.me,
                title,
                content,
                Timestamp.now()
            ))
            finish()
        }
    }
}