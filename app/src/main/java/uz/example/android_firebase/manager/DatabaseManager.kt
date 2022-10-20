package uz.example.android_firebase.manager

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import uz.example.android_firebase.model.Post

object DatabaseManager {
    private var database = FirebaseDatabase.getInstance().reference
    private var referance = database.child("posts")

    fun storePost(post: Post, handler: DatabaseHandler){
        val key = referance.push().key
        if (key == null)return

        post.id = key
        referance.child(key).setValue(post).addOnSuccessListener {
            handler.onSuccess()
        }.addOnFailureListener {
            handler.onError()
        }
    }

    fun loadPosts(handler: DatabaseHandler){
        referance.addValueEventListener(object :ValueEventListener{
            var posts:ArrayList<Post> = ArrayList()
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    for (snapshot in dataSnapshot.children){
                        val post:Post? = snapshot.getValue(Post::class.java)
                        post.let {
                            posts.add(post!!)
                        }
                    }
                    handler.onSuccess(posts = posts)
                }else{
                    handler.onSuccess(posts = ArrayList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                handler.onError()
            }
        })
    }

    fun deletePost(id: String, handler: DatabaseHandler){
        referance.child(id).removeValue().addOnSuccessListener {
            handler.onSuccess()
        }.addOnFailureListener {
            handler.onError()
        }
    }

    fun editPost(post: Post, handler: DatabaseHandler){
        referance.child(post.id!!).setValue(post).addOnSuccessListener {
            handler.onSuccess()
        }.addOnFailureListener {
            handler.onError()
        }
    }
}