package com.example.breddit.DataClasses


data class Post(
    val id: String,
    val userid: String,
    val op: String,
    val title: String,
    val content: String,
    val timestamp: com.google.firebase.Timestamp,
    val comments: ArrayList<Comment> = ArrayList<Comment>(),
    // Bitmap?
)
