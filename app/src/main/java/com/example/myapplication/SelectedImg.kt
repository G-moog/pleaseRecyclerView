package com.example.myapplication

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log

data class SelectedImg(
    val id: Int,
    val imgUri : Uri

) {
    // 더미
    companion object {

        fun createEmpty() = mutableListOf<SelectedImg>()
    }
}