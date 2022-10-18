package uz.example.android_firebase.utils

import android.app.Activity
import android.widget.Toast

object Extensions {

    fun Activity.toast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}