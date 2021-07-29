package com.example.pianoteachingassistant

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker

lateinit var imageView: ImageView
private var filePath: Uri? = null

class MainActivity : AppCompatActivity() {

    companion object {

        const val REQUEST_FROM_CAMERA = 201
        const val REQUEST_FROM_GALLERY = 202

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cameraButton = findViewById<Button>(R.id.cameraButton)
        val galleryButton = findViewById<Button>(R.id.galleryButton)
        val retrieveButton = findViewById<Button>(R.id.retrieveButton)
        val uploadButton = findViewById<Button>(R.id.uploadButton)
        val putButton = findViewById<Button>(R.id.putButton)
        val musicButton = findViewById<Button>(R.id.musicButton)
        imageView = findViewById(R.id.imageView)

        cameraButton.setOnClickListener {

            captureImageWCamera()

        }
        galleryButton.setOnClickListener {

            pickImageFromGallery()

        }

        retrieveButton.setOnClickListener {

            val name = findViewById<EditText>(R.id.nameET)
            FirebaseStorageManager.instance.retrieveImage(this, name.text.toString())

        }


        putButton.setOnClickListener {

            filePath = FirebaseStorageManager.instance.getURL()
            Toast.makeText(this, filePath.toString(), Toast.LENGTH_SHORT).show()
            imageView.setImageURI(filePath)

        }

        musicButton.setOnClickListener {

            //val intent = Intent(this, MusicManager::class.java)
            //startActivity(intent)
            val noteT = NoteThread()
            noteT.semantic = "A5"
            noteT.cont = this
            noteT.start()
        }


        uploadButton.setOnClickListener() {

            val name = findViewById<EditText>(R.id.nameET)
            filePath?.let { it1 ->
                FirebaseStorageManager.instance.uploadImage(
                    this,
                    it1, name.text.toString()
                )
            }

        }
    }

    private fun captureImageWCamera() {

        ImagePicker.with(this).cameraOnly().crop().start(REQUEST_FROM_CAMERA)

    }

    private fun pickImageFromGallery() {

        ImagePicker.with(this).galleryOnly().crop().start(REQUEST_FROM_GALLERY)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (data != null && data.data != null) {

                filePath = data.data!!

            }
            when (requestCode) {
                REQUEST_FROM_CAMERA -> {

                    imageView.setImageURI(filePath)
                    Toast.makeText(this, filePath.toString(), Toast.LENGTH_SHORT).show()

                }
                REQUEST_FROM_GALLERY -> {

                    imageView.setImageURI(filePath)
                    Toast.makeText(this, filePath.toString(), Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}
