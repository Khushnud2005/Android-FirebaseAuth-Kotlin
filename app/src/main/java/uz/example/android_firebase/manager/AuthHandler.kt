package uz.example.android_firebase.manager


import kotlin.Exception

interface AuthHandler {
    fun onSuccess()
    fun onError(exception:Exception?,code:String?)
}