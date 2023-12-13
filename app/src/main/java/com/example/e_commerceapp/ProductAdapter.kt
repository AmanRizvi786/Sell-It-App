package com.example.e_commerceapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(private val context: Context,private val listOfProducts: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage:ImageView=itemView.findViewById(R.id.imageViewProduct)
        val productNameTextView: TextView = itemView.findViewById(R.id.textViewProductName)
        val productPriceTextView: TextView = itemView.findViewById(R.id.textViewProductPrice)
        val productDesTextView: TextView = itemView.findViewById(R.id.textViewProductDescription)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = listOfProducts[position]

        holder. productNameTextView.text = product.productName
        holder.productPriceTextView.text = product.productPrice
        holder.productDesTextView.text = product.productDes
        Glide.with(context)
            .load(product.productImage)
            .into(holder.productImage)
    }

    override fun getItemCount(): Int {
        return listOfProducts.size
    }


}
