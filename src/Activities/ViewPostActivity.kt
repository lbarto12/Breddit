package com.example.breddit.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breddit.Adapters.CommentsAdapter
import com.example.breddit.DataClasses.Comment
import com.example.breddit.DataClasses.Post
import com.example.breddit.MainActivity
import com.example.breddit.R
import com.example.breddit.Util.FirestoreTools
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.activity_view_post.*

class ViewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post)
        initpost()
    }

    fun initpost(){
        adapter = CommentsAdapter(mutableListOf())
        post_view_comment_recycler.adapter = adapter
        post_view_comment_recycler.layoutManager = LinearLayoutManager(this)

        post_view_comment_recycler.addItemDecoration(DividerItemDecoration(
            post_view_comment_recycler.context,
            DividerItemDecoration.VERTICAL
        ))

        post_view_title.text = post.title
        post_view_content.text = post.content

        for (comment in post.comments){
            adapter.addcomment(comment)
        }


        send_comment_button.setOnClickListener {

            FirestoreTools.uploadComment(Comment(
                System.currentTimeMillis().toString() + MainActivity.me,
                MainActivity.me,
                comment_edit_text.text.toString(),
                Timestamp.now()
            ))

            comment_edit_text.text.clear()
        }

        Thread{
            while (running){
                FirestoreTools.downloadComments(adapter)
                Thread.sleep(500)
            }
        }.start()
    }

    var running = true

    override fun finish() {
        super.finish()
        running = false
    }

    companion object {
        lateinit var post: Post
        lateinit var adapter: CommentsAdapter
    }
}