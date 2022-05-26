package com.example.breddit.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.breddit.DataClasses.Comment
import com.example.breddit.R
import kotlinx.android.synthetic.main.post_comment_cell.view.*

class CommentsAdapter (var comments: MutableList<Comment>):
    RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>(){
    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view)


    fun addcomment(comment : Comment){
        comments.add(comment)
        notifyItemInserted(comments.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.post_comment_cell, parent, false)
        )
    }



    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val current = comments[position]

        holder.itemView.apply {
            comment_username.text = current.poster
            comment_content.text = current.content
        }
    }



    override fun getItemCount(): Int {
        return comments.size
    }
}