package com.example.myapplication.retrofit.data.response

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("id")
    val id : Int,
    val title : String,
    val content : String,
    val writer : String
)
