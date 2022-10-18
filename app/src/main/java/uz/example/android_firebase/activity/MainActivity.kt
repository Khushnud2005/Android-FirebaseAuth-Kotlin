package uz.example.android_firebase.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import uz.example.android_firebase.R
import uz.example.android_firebase.manager.AuthManager

class MainActivity : BaseActivity() {
    lateinit var signOut:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signOut = findViewById(R.id.btn_signOut)
        signOut.setOnClickListener {
            AuthManager.signOut()
            openSignInActivity(context)
        }
    }
}