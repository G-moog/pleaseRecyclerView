package com.example.myapplication.retrofit.data.response

import com.google.gson.annotations.SerializedName

data class Pageable(
    @SerializedName("offset")
    val offset: Int,

    @SerializedName("sort")
    val sort: Sort,

    @SerializedName("pageNumber")
    val pageNumber: Int,

    @SerializedName("pageSize")
    val pageSize: Int,

    @SerializedName("paged")
    val paged: Boolean,

    @SerializedName("unpaged")
    val unpaged: Boolean
)