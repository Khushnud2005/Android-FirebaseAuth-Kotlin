package uz.example.android_firebase.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import uz.example.android_firebase.R
import uz.example.android_firebase.manager.AuthHandler
import uz.example.android_firebase.manager.AuthManager
import uz.example.android_firebase.utils.Extensions.toast
import java.lang.Exception

class SignInActivity : BaseActivity() {

    lateinit var et_email: EditText
    lateinit var et_password:EditText
    lateinit var tv_signUp: TextView
    lateinit var btn_signIn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initViews()
    }

    fun initViews() {
        et_email = findViewById(R.id.et_emailSi)
        et_password = findViewById(R.id.et_passwordSi)
        tv_signUp = findViewById(R.id.tv_signup)

        btn_signIn = findViewById(R.id.btn_signIn)
        btn_signIn.setOnClickListener {
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            if (!email.isEmpty() && !password.isEmpty()){
                firebaseSignIn(email,password)
            }else{
                val str = "Email or Password is Empty! \nPlease Try Again!"
                Toast.makeText(this@SignInActivity, str, Toast.LENGTH_LONG).show()
            }

        }
        tv_signUp.setOnClickListener{ openSignUpActivity() }
    }

    private fun firebaseSignIn(email: String, password: String) {
        showLoading(this)
        Log.d("@@@","Email- "+password)
        AuthManager.signIn(email,password,object:AuthHandler{
            override fun onSuccess() {
                dismissLoading()
                openMainActivity(context)
                toast("Signed In Successfully")
            }

            override fun onError(exception: Exception?,code:String?) {
                dismissLoading()
            when (code) {

                    "ERROR_INVALID_EMAIL" ->{
                        et_email.setError(getString(R.string.error_invalid_email))
                        et_email.requestFocus()
                        Log.d("@@@_error","$code - $exception")
                        toast(exception!!.message.toString())
                    }
                    "ERROR_WRONG_PASSWORD" ->{
                        et_password.setError(getString(R.string.error_wrong_password))
                        et_password.requestFocus()
                        Log.d("@@@_error","$code - $exception")
                        toast(exception!!.message.toString())
                    }

                    "ERROR_USER_NOT_FOUND" ->{
                        et_email.setError(getString(R.string.error_user_not_found))
                        et_email.requestFocus()
                        Log.d("@@@_error","$code - $exception")
                        toast(exception!!.message.toString())
                    }

                }




                /*try {
                    throw exception
                } catch (e: FirebaseAuthWeakPasswordException) {
                    et_password.setError(getString(R.string.error_weak_password))
                    et_password.requestFocus()
                    Log.d("@@@_error","Error - ${e.toString()}")
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    if (e.message!!.equals(R.string.error_weak_password)){
                        et_password.setError(getString(R.string.error_weak_password))
                        et_password.requestFocus()
                    }else{
                        et_email.setError(getString(R.string.error_invalid_email))
                        et_email.requestFocus()
                    }
                    Log.d("@@@_error","Error - ${e.toString()}")
                } catch (e: FirebaseAuthUserCollisionException) {
                    et_email.setError(getString(R.string.error_user_exists))
                    et_email.requestFocus()
                    Log.d("@@@_error","Error - ${e.toString()}")
                }catch (e: FirebaseAuthInvalidUserException) {
                    et_email.setError(getString(R.string.error_user_not_found))
                    et_email.requestFocus()
                    Log.d("@@@_error","Error - ${e.toString()}")
                }catch (e: Exception) {
                    Log.d("@@@_error","Error - ${e.toString()}")
                }*/

            }
        })
    }

    fun openSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}