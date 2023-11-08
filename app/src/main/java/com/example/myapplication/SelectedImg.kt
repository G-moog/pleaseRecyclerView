package com.example.myapplication

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log

data class SelectedImg(
    val id: Int,

    val imgUri : Uri,

) {
    // 더미
    companion object {
        fun createSamples(imgUriList : MutableList<Uri>) = mutableListOf<SelectedImg>().apply {
            for (i in 0 until imgUriList.size) {
                add(SelectedImg(number +i, "Uri: ${imgUriList.get(page+i)}", "폴더: ${folderNameList.get(page+i)}", imgUriList.get(page + i)))
                Log.d(TAG, "createSamples 할 때 뷰홀더에 찍은 이미지 Uri: " + imgUriList.get(page + i))
                Log.d(TAG, "createSamples 할 때 뷰홀더에 찍은 이미지 폴더명: " + folderNameList.get(page + i))
            }
        }

        fun createEmpty() = mutableListOf<SelectedImg>()
    }
}