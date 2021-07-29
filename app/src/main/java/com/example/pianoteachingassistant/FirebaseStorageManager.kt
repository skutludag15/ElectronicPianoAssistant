package com.example.pianoteachingassistant

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File


class FirebaseStorageManager private constructor(){

    private val mStorageRef = FirebaseStorage.getInstance().reference
    private val db = FirebaseFirestore.getInstance()
    private lateinit var mProgressDialog: ProgressDialog
    private val TAG = "FirebaseStoreManager"
    private var fileURI: Uri? = null

    fun uploadImage(mContext: Context, imageURI: Uri, name: String) {

        mProgressDialog = ProgressDialog(mContext)
        mProgressDialog.setMessage("Image being uploading, Please wait!")
        mProgressDialog.show()

        val fileName = name + ".jpg"
        val uploadTask = mStorageRef.child("musicalScores/" + fileName).putFile(imageURI)

        uploadTask.addOnSuccessListener {

            Log.e(TAG, "Image uploaded successfully")
            val downloadURLTask = mStorageRef.child("musicalScores/" + fileName).downloadUrl
            downloadURLTask.addOnSuccessListener {
                Log.e(TAG, "Image Path: $it")
                mProgressDialog.dismiss()

            }.addOnFailureListener {

                Log.e(TAG, "failed to get download URL: $it")
                mProgressDialog.dismiss()

            }
        }.addOnFailureListener {

            Log.e(TAG, "failed to upload $it")
            mProgressDialog.dismiss()

        }

        uploadDB()

    }


    fun getURL(): Uri? {

        return fileURI

    }
    fun uploadDB() {

        val score = hashMapOf(
            "name" to "Alan",
            "artist" to "Mathison",
            "semantic" to "Turing"
        )

// Add a new document with a generated ID
        db.collection("musicalScores")
            .add(score)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

    fun retrieveDB(){

        db.collection("musicalScores")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.e(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting documents.", exception)
            }

    }

    fun retrieveImage(mContext: Context, name: String) {

        mProgressDialog = ProgressDialog(mContext)
        mProgressDialog.setMessage("Image being downloading, Please wait!")
        mProgressDialog.show()


        val mRef2= mStorageRef.child("musicalScores/$name.jpg")
        val localFile = File.createTempFile("images", "jpg")

        mRef2.getFile(localFile).addOnSuccessListener{

            mStorageRef.child("musicalScores/$name.jpg").downloadUrl.addOnSuccessListener {

                fileURI = Uri.fromFile(localFile)
                Log.e(TAG, "Image Path: $fileURI")
                Toast.makeText(mContext, "Successful", Toast.LENGTH_SHORT).show()
                mProgressDialog.dismiss()

            }.addOnFailureListener {

                Log.e(TAG, "$it")
                mProgressDialog.dismiss()

            }
        }.addOnFailureListener{

            Log.e(TAG,"$it")
            mProgressDialog.dismiss()

        }
        retrieveDB()
    }


    /**
     * Makes a deep copy of any Java object that is passed.
     */
    private object HOLDER {
        val INSTANCE = FirebaseStorageManager()
    }

    companion object {
        val instance: FirebaseStorageManager by lazy { HOLDER.INSTANCE }
    }

}



