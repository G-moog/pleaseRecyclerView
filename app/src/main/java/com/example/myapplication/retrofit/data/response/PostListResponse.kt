package com.example.myapplication.retrofit.data.response

import com.google.gson.annotations.SerializedName

data class PostListResponse(
    @SerializedName("totalPages")
    val totalPages: Int,

    @SerializedName("totalElements")
    val totalElements: Int,

    @SerializedName("first")
    val first: Boolean,

    @SerializedName("last")
    val last: Boolean,

    @SerializedName("size")
    val size: Int,

    @SerializedName("content")
    val content: List<Post>,

    @SerializedName("number")
    val number: Int,

    @SerializedName("sort")
    val sort: Sort,

    @SerializedName("pageable")
    val pageable: Pageable,

    @SerializedName("numberOfElements")
    val numberOfElements: Int,

    @SerializedName("empty")
    val empty: Boolean
)