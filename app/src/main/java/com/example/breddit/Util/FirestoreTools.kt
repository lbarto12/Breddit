package com.example.breddit.Util

import android.os.health.TimerStat
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.breddit.Activities.ViewPostActivity
import com.example.breddit.Adapters.CommentsAdapter
import com.example.breddit.Adapters.PostsAdapter
import com.example.breddit.DataClasses.Comment
import com.example.breddit.DataClasses.Post
import com.example.breddit.MainActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.merge

class FirestoreTools {
    companion object{

        fun <K, V> put(collection: String, document: String, data: HashMap<K, V>){
            val db = FirebaseFirestore.getInstance()
            db.collection(collection)
                .document(document)
                .set(data)
                .addOnFailureListener {
                    Log.d("FIREBASE", it.message.toString())
                }
        }

        fun createConversation(post: Post){
            val collectionName = System.currentTimeMillis().toString() + post.title + post.userid
            val db = FirebaseFirestore.getInstance()
            put(collectionName, "info", hashMapOf(
                "id" to collectionName,
                "userid" to post.userid,
                "title" to post.title,
                "content" to post.content,
                "op" to post.op,
                "timestamp" to Timestamp.now()
            ))
            put("allposts", collectionName, hashMapOf(
                "id" to collectionName
            ))
        }

        fun createUser(username: String, password: String){
            put("users", username, hashMapOf(
                "username" to username,
                "password" to password
            ))
        }

        fun userExists(username: String, callback: (result: Boolean) -> Unit){
            withcollection("users"){ users ->
                users.forEach { user ->
                    if (user.get("username").toString() == username){
                        callback.invoke(true)
                        return@withcollection
                    }
                }
                callback.invoke(false)
            }
        }

        fun credentialsValid(username: String, password: String, callback: (result: Boolean) -> Unit){
            withcollection("users"){ users ->
                users.forEach { user ->
                    if (user.get("username").toString() == username &&
                        user.get("password").toString() == password){
                        MainActivity.me = username
                        callback.invoke(true)
                        return@withcollection
                    }
                }
                callback.invoke(false)
            }
        }

        fun uploadComment(comment: Comment){
            put(ViewPostActivity.post.id, comment.id, hashMapOf(
                "id" to comment.id,
                "poster" to comment.poster,
                "content" to comment.content,
                "timestamp" to Timestamp.now()
            ))
        }



        fun downloadComments(adapter: CommentsAdapter){
            withcollection(ViewPostActivity.post.id){ collection ->
                collection.forEach start@{ comment ->
                    if (comment.id == "info") return@start
                    for (com in adapter.comments){
                        if (comment.get("id").toString() == com.id){
                            return@start
                        }
                    }
                    val final = Comment(
                        comment.get("id").toString(),
                        comment.get("poster").toString(),
                        comment.get("content").toString(),
                        Timestamp.now()
                    )
                    adapter.addcomment(final)
                    ViewPostActivity.post.comments.add(final)
                }
            }
        }



        fun downloadPosts(adapter: PostsAdapter){
            withcollection("allposts"){ collection ->
                collection.forEach start@{ doc ->
                    withdocument(doc.get("id").toString(), "info"){ info ->
                        for (post in adapter.posts){
                            if (post.id == doc.get("id").toString()){
                                return@withdocument
                            }
                        }
                        adapter.addpost(Post(
                            info .get("id").toString(),
                            info .get("userid").toString(),
                            info .get("op").toString(),
                            info .get("title").toString(),
                            info .get("content").toString(),
                            Timestamp.now()
                        ))
                    }
                }
            }
        }


        fun withcollection(collection: String, callback: (result : QuerySnapshot) -> Unit){
            val db = FirebaseFirestore.getInstance()
            db.collection(collection).get()
                .addOnSuccessListener(callback)
        }

        fun withdocument(collection: String, document: String, callback: (result: DocumentSnapshot) -> Unit){
            val db = FirebaseFirestore.getInstance()
            db.collection(collection)
                .document(document)
                .get()
                .addOnSuccessListener(callback)
        }

    }
}