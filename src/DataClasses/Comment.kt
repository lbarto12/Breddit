package com.example.breddit.DataClasses

import com.google.firebase.Timestamp


data class Comment(
    val id: String,
    val poster: String,
    val content: String,
    val timestamp: Timestamp
)
