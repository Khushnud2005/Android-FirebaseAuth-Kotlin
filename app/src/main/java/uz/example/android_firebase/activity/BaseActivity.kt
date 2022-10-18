package uz.example.android_firebase.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import uz.example.android_firebase.R

open class BaseActivity :AppCompatActivity() {
    lateinit var context: Context
    var progressDialog:AppCompatDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
    }
    fun showLoading(activity:Activity?){
        if (activity == null)return
        if (progressDialog != null && progressDialog!!.isShowing){

        }else{
            progressDialog = AppCompatDialog(activity,R.style.CustomDialog)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setCanceledOnTouchOutside(false)
            progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog!!.setContentView(R.layout.custom_progress_dialog)

            val iv_progress = progressDialog!!.findViewById<ImageView>(R.id.iv_progress)
            val animationDrawable = iv_progress!!.drawable as AnimationDrawable
            animationDrawable.start()

            if (!activity.isFinishing)progressDialog!!.show()
        }

    }
    fun dismissLoading(){
        if (progressDialog != null && progressDialog!!.isShowing){
            progressDialog!!.dismiss()
        }
    }
    fun openSignInActivity(context: Context) {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun openMainActivity(context: Context) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}