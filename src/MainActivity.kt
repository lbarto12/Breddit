package com.example.breddit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breddit.Activities.CreatePostActivity
import com.example.breddit.Adapters.PostsAdapter
import com.example.breddit.DataClasses.Comment
import com.example.breddit.DataClasses.Post
import com.example.breddit.SignIn.CreateNewAccountActivity
import com.example.breddit.SignIn.SignInActivity
import com.example.breddit.Util.FirestoreTools
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        self = this
        initUI()
        login()
        main()
    }

    private fun main() {
        posts_recycler.adapter = postAdapter
        posts_recycler.layoutManager = LinearLayoutManager(this)




        Thread{
            while(true){
                if (currentQuery == ""){
                    FirestoreTools.downloadPosts(postAdapter)
                    Thread.sleep(500)
                }
            }
        }.start()
    }

    private fun initUI(){
        new_post_button.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }

        search_posts_searchbar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                postAdapter.filter.filter(p0)
                currentQuery = p0?: ""
                return true
            }
        })

    }

    private fun login(){
        if (!hasloggedin){
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            hasloggedin = true
        }
    }

    companion object{
        var postAdapter = PostsAdapter(mutableListOf())
        lateinit var self: MainActivity
        var hasloggedin = false
        var me = "NULL"
        var currentQuery = ""
    }
}