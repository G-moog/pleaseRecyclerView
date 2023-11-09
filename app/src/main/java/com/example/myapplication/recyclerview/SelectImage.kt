package com.example.myapplication.recyclerview

import android.net.Uri

data class SelectImage(
    val id: Int,
    val imgUri : Uri

) {
    // 더미
    companion object {

        fun createEmpty() = mutableListOf<SelectImage>()
    }
}