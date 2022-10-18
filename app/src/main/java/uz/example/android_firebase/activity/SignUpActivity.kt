package uz.example.android_firebase.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import uz.example.android_firebase.R
import uz.example.android_firebase.manager.AuthHandler
import uz.example.android_firebase.manager.AuthManager
import uz.example.android_firebase.utils.Extensions.toast
import java.lang.Exception

class SignUpActivity : BaseActivity() {
    lateinit var et_fullName: EditText
    lateinit var et_email:EditText
    lateinit var et_password:EditText
    lateinit var et_cpassword:EditText
    lateinit var tv_signUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initViews()
    }

    fun initViews() {
        et_email = findViewById(R.id.et_emailSU)
        et_password = findViewById(R.id.et_passwordSU)
        tv_signUp = findViewById(R.id.tv_signIn)
        val btn_signUp = findViewById<Button>(R.id.btn_signUp)
        btn_signUp.setOnClickListener {
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            if (!email.isEmpty() && !password.isEmpty()){
                firebaseSignUp(email,password)
            }
        }
        tv_signUp.setOnClickListener(View.OnClickListener { openSignInActivity(this) })
    }

    private fun firebaseSignUp(email: String, password: String) {
        showLoading(this)
        AuthManager.signUp(email,password,object: AuthHandler {
            override fun onSuccess() {
                dismissLoading()
                openMainActivity(context)
                toast("Sign Up Successfully")
            }

            override fun onError(exception: Exception?,code:String?) {
                dismissLoading()
                Log.d("@@@_error","$code - ${exception.toString()}")
                when (code) {
                    "ERROR_WRONG_PASSWORD" ->{
                        et_password.setError(getString(R.string.error_weak_password))
                        et_password.requestFocus()
                        Log.d("@@@_error","$code - $exception")
                        toast(exception!!.message.toString())
                    }
                    "ERROR_WEAK_PASSWORD" ->{
                        et_password.setError(getString(R.string.error_weak_password))
                        et_password.requestFocus()
                        Log.d("@@@_error","$code - $exception")
                        toast(exception!!.message.toString())
                    }
                    "ERROR_INVALID_EMAIL" ->{
                        et_email.setError(getString(R.string.error_invalid_email))
                        et_email.requestFocus()
                        Log.d("@@@_error","$code - $exception")
                        toast(exception!!.message.toString())
                    }
                    "ERROR_CREDENTIAL_ALREADY_IN_USE" ->{
                        et_email.setError(exception!!.message)
                        et_email.requestFocus()
                        Log.d("@@@_error","$code - $exception")
                        toast(exception!!.message.toString())
                    }
                    "ERROR_EMAIL_ALREADY_IN_USE" ->{
                        et_email.setError(exception!!.message)
                        et_email.requestFocus()
                        Log.d("@@@_error","$code - $exception")
                        toast(exception!!.message.toString())
                    }


                }
            }
        })
    }


}