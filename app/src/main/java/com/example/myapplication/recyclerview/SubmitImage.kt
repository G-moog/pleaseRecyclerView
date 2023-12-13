package com.example.myapplication.recyclerview

import android.net.Uri

data class SubmitImage(

    val imgUri : Uri

) {
    // 더미
    companion object {

        fun createEmpty() = mutableListOf<SubmitImage>()
    }
}