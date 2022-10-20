package uz.example.android_firebase.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import uz.example.android_firebase.R
import uz.example.android_firebase.manager.DatabaseHandler
import uz.example.android_firebase.manager.DatabaseManager
import uz.example.android_firebase.model.Post
import uz.example.android_firebase.utils.Extensions.toast

class CreateActivity : BaseActivity() {
    lateinit var et_title:EditText
    lateinit var et_body:EditText
    lateinit var iv_close:ImageView
    lateinit var btn_create:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        initViews()
    }
    fun initViews(){
        et_title = findViewById(R.id.et_title)
        et_body = findViewById(R.id.et_body)
        iv_close = findViewById(R.id.iv_close)
        btn_create = findViewById(R.id.btn_create)

        iv_close.setOnClickListener { finish() }

        btn_create.setOnClickListener {
            val title = et_title.text.toString().trim()
            val body = et_body.text.toString().trim()
            if (title.isNotEmpty() && body.isNotEmpty()){
                val post = Post(title,body)
                storeDatabase(post)
            }else{
                toast("Enter Post Parameters")
            }
        }
    }

    private fun storeDatabase(post: Post) {
        showLoading(this)
        DatabaseManager.storePost(post, object :DatabaseHandler{
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                dismissLoading()
                finishIntent()
            }

            override fun onError() {
                dismissLoading()
            }

        })
    }
    fun finishIntent(){
        val returnIntent = intent
        setResult(RESULT_OK,returnIntent)
        finish()
    }
}