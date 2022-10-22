package uz.example.android_firebase.activity

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import uz.example.android_firebase.R
import uz.example.android_firebase.manager.DatabaseHandler
import uz.example.android_firebase.manager.DatabaseManager
import uz.example.android_firebase.manager.StorageHandler
import uz.example.android_firebase.manager.StorageManager
import uz.example.android_firebase.model.Post
import uz.example.android_firebase.utils.Extensions.toast


class CreateActivity : BaseActivity() {
    lateinit var et_title:EditText
    lateinit var et_body:EditText
    lateinit var iv_close:ImageView
    lateinit var iv_photo:ImageView
    lateinit var iv_camera:ImageView
    lateinit var btn_create:Button
    var pickedPhoto: Uri? = null
    var allPhotos = ArrayList<Uri>()
    lateinit var exten:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        initViews()
    }
    fun initViews(){
        et_title = findViewById(R.id.et_title)
        et_body = findViewById(R.id.et_body)
        iv_close = findViewById(R.id.iv_close)
        iv_photo = findViewById(R.id.iv_photo)
        iv_camera = findViewById(R.id.iv_camera)
        btn_create = findViewById(R.id.btn_create)


        iv_camera.setOnClickListener {
            pickProfilePhoto()
        }
        iv_close.setOnClickListener { finish() }

        btn_create.setOnClickListener {
            val title = et_title.text.toString().trim()
            val body = et_body.text.toString().trim()
            if (title.isNotEmpty() && body.isNotEmpty()){
                val post = Post(title,body)
                apiStoreNewPost(post)
            }else{
                toast("Enter Post Parameters")
            }
        }
    }

    private fun pickProfilePhoto() {
        allPhotos.clear()
        FishBun.with(this).setImageAdapter(GlideAdapter())
            .setMinCount(1)
            .setMaxCount(1)
            .setCamera(true)
            .setSelectedImages(allPhotos)
            .startAlbumWithActivityResultCallback(photoLuncher)

    }
    val photoLuncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            allPhotos = it.data?.getParcelableArrayListExtra(FishBun.INTENT_PATH) ?: arrayListOf()
            pickedPhoto = allPhotos[0]
            iv_photo.setImageURI(pickedPhoto)

            val mimeType: String? = allPhotos[0].let { returnUri ->
                contentResolver.getType(returnUri)
            }
            exten = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)!!

        }
    }
    private fun apiStoreNewPost(post:Post){
        if (pickedPhoto != null){
            apiStoreStorage(post)
        }else{
            storeDatabase(post)
        }
    }
    private fun apiStoreStorage(post:Post){
        showLoading(this)

        StorageManager.uploadPhoto(pickedPhoto!!, exten, object :StorageHandler{
            override fun onSuccess(imgUri: String) {
                post.image = imgUri
                storeDatabase(post)
            }

            override fun onError(exception: Exception) {
                storeDatabase(post)
            }

        })
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