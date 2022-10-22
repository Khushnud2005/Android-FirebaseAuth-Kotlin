package uz.example.android_firebase.manager

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

object StorageManager {
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference
    val photosRef = storageRef.child("photos")

    fun uploadPhoto(uri: Uri,extension:String, handler: StorageHandler){

        val fileName = "${System.currentTimeMillis().toString()}.$extension"
        val uploadTask:UploadTask = photosRef.child(fileName).putFile(uri)
        uploadTask.addOnSuccessListener {
            Log.d("@@@upload", extension)
            val result = it.metadata!!.reference!!.downloadUrl
            result.addOnSuccessListener {
                val imgUri = it.toString()
                handler.onSuccess(imgUri)
            }.addOnFailureListener { e ->
                handler.onError(e)
            }

        }.addOnFailureListener { e ->
            handler.onError(e)
        }

    }
    fun deletePhoto(url: String?) {
        if (url != null){
            Log.d("@@@Url", url)
        }
        val photoRef: StorageReference = storage.getReferenceFromUrl(url!!)
        photoRef.delete().addOnSuccessListener { // File deleted successfully
            Log.d("@@@Delete", "onSuccess: deleted file")
        }.addOnFailureListener { // Uh-oh, an error occurred!
            Log.d("@@@Delete", "onFailure: did not delete file")
        }
    }
}