package com.example.e_commerceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val listOfProducts = mutableListOf<Product>()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewProducts)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        FirebaseDatabase.getInstance().getReference("Products")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot){
                    listOfProducts.clear()
                    for (dataSnapshot in snapshot.children) {
                        val productData = dataSnapshot.value as? HashMap<*, *>
                        if (productData != null) {
                            val product = Product(
                                productData["productName"] as? String,
                                productData["productPrice"] as? String,
                                productData["productDes"] as? String,
                                productData["productImage"] as? String
                            )
                            listOfProducts.add(product)
                        }
                    }

                        val productAdapter = ProductAdapter(this@MainActivity, listOfProducts)
                        recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)
                        recyclerView.adapter = productAdapter

                }
                override fun onCancelled(error: DatabaseError) {
                }

            })


        fab.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }
}

