package com.example.myapplication

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log

data class SelectedImg(
    val id: Int,
    val imgUriName: String,
    val imgFolderName: String,
    val imgUri : Uri,
    var isChecked : Boolean = false
) {
    // 더미
    companion object {
        fun createSamples(page : Int, imgUriList : MutableList<Uri>,  folderNameList : MutableList<String>) = mutableListOf<SelectedImg>().apply {
            for (i in 0 until 9) {
                val number = page * 10
                if(page+i < 0){
                    Log.d(TAG, "인덱스가 -1일 때 page 값 : " + page)
                    Log.d(TAG, "인덱스가 -1일 때 i 값 : " + i)
                }
                add(SelectedImg(number +i, "Uri: ${imgUriList.get(page+i)}", "폴더: ${folderNameList.get(page+i)}", imgUriList.get(page + i)))
                Log.d(TAG, "createSamples 할 때 뷰홀더에 찍은 이미지 Uri: " + imgUriList.get(page + i))
                Log.d(TAG, "createSamples 할 때 뷰홀더에 찍은 이미지 폴더명: " + folderNameList.get(page + i))
            }
        }

        fun createEmpty() = mutableListOf<SelectedImg>()
    }
}