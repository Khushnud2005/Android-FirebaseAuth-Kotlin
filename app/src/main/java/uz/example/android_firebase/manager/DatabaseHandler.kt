package uz.example.android_firebase.manager

import uz.example.android_firebase.model.Post

interface DatabaseHandler {
    fun onSuccess(post: Post? = null,posts:ArrayList<Post> = ArrayList())
    fun onError()
}