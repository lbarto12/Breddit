package com.example.breddit.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.breddit.Activities.ViewPostActivity
import com.example.breddit.DataClasses.Post
import com.example.breddit.MainActivity
import com.example.breddit.R
import kotlinx.android.synthetic.main.post_cell.view.*
import java.io.FilterReader

class PostsAdapter(var posts: MutableList<Post>):
    RecyclerView.Adapter<PostsAdapter.PostViewHolder>(), Filterable {
    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view)


    fun addpost(post: Post){
        posts.add(post)
        notifyItemInserted(posts.size - 1)
    }

    fun insertpost(post: Post, index: Int){
        posts.add(index, post)
        notifyItemInserted(index)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostsAdapter.PostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.post_cell, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val current = posts[position]
        holder.itemView.apply {
            post_cell_title.text = current.title
            post_cell_preview.text = current.content

            setOnClickListener {
                val intent = Intent(MainActivity.self, ViewPostActivity::class.java)
                ViewPostActivity.post = current
                MainActivity.self.startActivity(intent)
                // MainActivity.self.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_left)
            }
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }


    var filteredPosts = this.posts

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (p0 == null || p0.isEmpty()){
                    filterResults.count = filteredPosts.size
                    filterResults.values = filteredPosts
                }
                else {
                    val searchResult = p0.toString().lowercase()
                    val postItems = ArrayList<Post>()
                    for (post in filteredPosts){
                        if (searchResult in post.title.lowercase() ||
                                searchResult in post.content.lowercase()){
                            postItems.add(post)
                        }
                    }
                    filterResults.count = postItems.size
                    filterResults.values = postItems
                }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                if (p1 != null){
                    posts = p1!!.values as MutableList<Post>
                    notifyDataSetChanged()
                }
            }
        }
    }

}