package uz.example.android_firebase.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import uz.example.android_firebase.R
import uz.example.android_firebase.model.Post
import uz.example.android_firebase.utils.Extensions.toast

class EditActivity : AppCompatActivity() {

    lateinit var et_title: EditText
    lateinit var et_body: EditText
    lateinit var iv_close: ImageView
    lateinit var btn_edit: Button
    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        initViews()
    }

    fun initViews(){
        et_title = findViewById(R.id.et_title_edit)
        et_body = findViewById(R.id.et_body_edit)
        iv_close = findViewById(R.id.iv_close_edit)
        btn_edit = findViewById(R.id.btn_update)

        val extras = intent.extras
        if (extras != null) {
            et_title.setText(extras.getString("title"))
            et_body.setText(extras.getString("body"))
            id = extras.getString("id")!!
        }
        iv_close.setOnClickListener { finish() }

        btn_edit.setOnClickListener {
            val title = et_title.text.toString().trim()
            val body = et_body.text.toString().trim()
            val post = Post(id,title,body)
            if (title.isNotEmpty() && body.isNotEmpty()){
                val intent = Intent(this@EditActivity, MainActivity::class.java)
                intent.putExtra("title", title)
                intent.putExtra("body", body)
                intent.putExtra("id", id)
                startActivity(intent)
            }else{
                toast("Enter Post Parameters")
            }
        }
    }
}