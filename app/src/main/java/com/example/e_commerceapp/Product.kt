package com.example.e_commerceapp


 data class Product(
    val productName: String?=null,
    val productPrice: String?=null,
    val productDes: String?=null,
    val productImage: String?=null

 ){
     companion object {
         // Function to parse a String and create a Product object
         fun fromString(stringData: String): Product {
             // Implement the logic to parse the string and create a Product object
             // Example: Split the string and extract values
             val parts = stringData.split(",")
             return Product(
                 parts[0],
                 parts[1],
                 parts[2],
                 parts[3]
             )
         }
     }
 }






