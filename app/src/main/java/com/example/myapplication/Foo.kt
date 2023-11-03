package com.example.myapplication

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log

data class Foo(
    val id: Int,
    val imgUriName: String,
    val imgFolderName: String,
    val imgUri : Uri,
    var isChecked : Boolean = false
) {
    // 더미
    companion object {
        fun createSamples(page : Int, imgUriList : MutableList<Uri>,  folderNameList : MutableList<String>) = mutableListOf<Foo>().apply {
            for (i in 1 until 10) {
                val number = page * 10
                add(Foo(number +i, "Uri: ${imgUriList.get(page+1-1)}", "폴더: ${folderNameList.get(page+1-1)}", imgUriList.get(page + i - 1)))
                Log.d(TAG, "createSamples 할 때 뷰홀더에 찍은 이미지 Uri: " + imgUriList.get(page + 1 - 1))
                Log.d(TAG, "createSamples 할 때 뷰홀더에 찍은 이미지 폴더명: " + folderNameList.get(page + 1 - 1))
            }
        }
    }
}