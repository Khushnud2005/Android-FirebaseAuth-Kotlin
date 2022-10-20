package uz.example.android_firebase.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uz.example.android_firebase.R
import uz.example.android_firebase.adapter.PostAdapter
import uz.example.android_firebase.manager.AuthManager
import uz.example.android_firebase.manager.DatabaseHandler
import uz.example.android_firebase.manager.DatabaseManager
import uz.example.android_firebase.model.Post

class MainActivity : BaseActivity() {
    lateinit var signOut:ImageView
    lateinit var fab_create:FloatingActionButton
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }
    fun initViews(){
        signOut = findViewById(R.id.iv_signOut)
        fab_create = findViewById(R.id.fab_create)
        signOut.setOnClickListener {
            AuthManager.signOut()
            openSignInActivity(context)
        }
        fab_create.setOnClickListener {
            callCreateActivity()
        }
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this,1)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        apiLoadPosts()

        val extras = intent.extras
        if (extras != null) {
            Log.d("###", "extras not NULL - ")
            val edit_title = extras.getString("title")
            val edit_body = extras.getString("body")
            val edit_id = extras.getString("id")
            val post = Post(edit_id!!, edit_title, edit_body)
            apiEditPost(post)
        }

    }



    private fun refreshAdapter(posts:ArrayList<Post>){
        val adapter:PostAdapter = PostAdapter(this,posts)
        recyclerView.adapter = adapter
    }

    private fun apiLoadPosts(){
        showLoading(this)
        DatabaseManager.loadPosts(object : DatabaseHandler{
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                dismissLoading()
                refreshAdapter(posts)
            }

            override fun onError() {
                dismissLoading()
            }

        })
    }
    private fun apiDeletePost(post: Post){
        showLoading(this)
        DatabaseManager.deletePost(post.id!!,object : DatabaseHandler{
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                dismissLoading()
                apiLoadPosts()
            }

            override fun onError() {
                dismissLoading()
            }

        })
    }
    private fun apiEditPost(post: Post) {
        showLoading(this)
        DatabaseManager.editPost(post,object : DatabaseHandler{
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                dismissLoading()
                apiLoadPosts()
            }
            override fun onError() {
                dismissLoading()
            }

        })
    }
    fun dialogPoster(post: Post) {
        AlertDialog.Builder(this)
            .setTitle("Delete Post")
            .setMessage("Are you sure you want to delete this post?")
            .setPositiveButton(
                android.R.string.yes
            ) { dialog, which -> apiDeletePost(post) }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
    var resultlauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK){
            val data:Intent? = result.data
            apiLoadPosts()
        }

    }
    private fun callCreateActivity() {
        val intent = Intent(context,CreateActivity::class.java)
        resultlauncher.launch(intent)
    }
}