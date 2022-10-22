package uz.example.android_firebase.manager

import java.lang.Exception

interface StorageHandler {
    fun onSuccess(imgUri:String)
    fun onError(exception: Exception)
}