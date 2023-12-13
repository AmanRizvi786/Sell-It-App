package com.example.e_commerceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class UploadProduct : AppCompatActivity() {
    private lateinit var editTextProductName: EditText
    private lateinit var editTextProductPrice: EditText
    private lateinit var editTextProductDescription: EditText
    private lateinit var imageViewProduct: ImageView
    private lateinit var buttonUpload: Button
    private lateinit var buttonSelect: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_product)

        editTextProductName = findViewById(R.id.editTextProductName)
        editTextProductPrice = findViewById(R.id.editTextProductPrice)
        editTextProductDescription = findViewById(R.id.editTextProductDescription)
        imageViewProduct = findViewById(R.id.imageViewProduct)
        buttonUpload = findViewById(R.id.buttonUpload)
        buttonSelect = findViewById(R.id.buttonSelect)
        progressBar = findViewById(R.id.progressBar)

        buttonSelect.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 101)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            val uri = data!!.data
            imageViewProduct.setImageURI(uri)


            buttonUpload.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                val productName = editTextProductName.text.toString()
                val productPrice = editTextProductPrice.text.toString()
                val productDescription = editTextProductDescription.text.toString()

                val filename = UUID.randomUUID().toString() + ".jpg"
                val storageReference =
                    FirebaseStorage.getInstance().reference.child("Product Images/$filename")
                storageReference.putFile(uri!!).addOnSuccessListener {

                    val result = it.metadata?.reference?.downloadUrl

                    result?.addOnSuccessListener {
                        progressBar.visibility = View.GONE
                        uploadProduct(
                            productName,
                            productPrice,
                            productDescription,
                            it.toString()
                        )
                    }
                }
            }
        }
    }

    private fun uploadProduct(
        name: String,
        price: String,
        description: String,
        link: String
    ) {
        Firebase.database.getReference("Products").child(name).setValue(
            Product(
                productName = name,
                productPrice = price,
                productDes = description,
                productImage = link

            )
        ).addOnSuccessListener {
            progressBar.visibility = View.GONE
            Toast.makeText(this, "Product uploaded: $name", Toast.LENGTH_SHORT).show()
        }


    }
}