package uz.example.android_firebase.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import uz.example.android_firebase.R
import uz.example.android_firebase.manager.StorageHandler
import uz.example.android_firebase.manager.StorageManager
import uz.example.android_firebase.utils.Extensions.toast

class EditActivity : AppCompatActivity() {

    lateinit var et_title: EditText
    lateinit var et_body: EditText
    lateinit var iv_close: ImageView
    lateinit var iv_photo_edit: ImageView
    lateinit var iv_camera_edit: ImageView
    lateinit var btn_edit: Button
    lateinit var id: String
    var exten: String? = null
    var old_image: String? = null
    var pickedPhoto: Uri? = null
    var allPhotos = ArrayList<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        initViews()
    }

    fun initViews(){
        et_title = findViewById(R.id.et_title_edit)
        et_body = findViewById(R.id.et_body_edit)
        iv_close = findViewById(R.id.iv_close_edit)
        iv_photo_edit = findViewById(R.id.iv_photo_edit)
        iv_camera_edit = findViewById(R.id.iv_camera_edit)
        btn_edit = findViewById(R.id.btn_update)

        val extras = intent.extras
        if (extras != null) {
            et_title.setText(extras.getString("title"))
            et_body.setText(extras.getString("body"))
            if (extras.getString("image") != null){
                old_image = extras.getString("image")
                Glide.with(this).asBitmap().load(old_image).into(iv_photo_edit)
            }
            id = extras.getString("id")!!
        }
        iv_close.setOnClickListener { finish() }
        iv_camera_edit.setOnClickListener { pickProfilePhoto() }
        btn_edit.setOnClickListener {
            val title = et_title.text.toString().trim()
            val body = et_body.text.toString().trim()
            if (title.isNotEmpty() && body.isNotEmpty()){
                val intent = Intent(this@EditActivity, MainActivity::class.java)
                intent.putExtra("title", title)
                intent.putExtra("body", body)
                intent.putExtra("id", id)
                if (old_image != null) {
                    intent.putExtra("image", old_image)
                }
                startActivity(intent)
            }else{
                toast("Enter Post Parameters")
            }
        }
    }

    private fun pickProfilePhoto() {
        allPhotos.clear()
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
            iv_photo_edit.setImageURI(pickedPhoto)

            val mimeType: String? = allPhotos[0].let { returnUri ->
                contentResolver.getType(returnUri)
            }
            exten = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)!!

            apiStoreStorage(pickedPhoto!!)
        }
    }

    private fun apiStoreStorage(pickedPhoto: Uri) {
        if (old_image != null) {
            StorageManager.deletePhoto(old_image)
        }
        StorageManager.uploadPhoto(pickedPhoto, exten!!, object : StorageHandler {
            override fun onSuccess(imgUri: String) {
                old_image = imgUri
            }

            override fun onError(exception: Exception) {
                old_image = null
            }

        })
    }
}