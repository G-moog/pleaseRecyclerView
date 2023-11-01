package com.example.myapplication

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log

data class Foo(
    val id: Int,
    val name: String,
    val phone: String,
    val imgUri : Uri
) {
    // 더미
    companion object {
        fun createSamples(page : Int, imgUriList : MutableList<Uri>) = mutableListOf<Foo>().apply {
            for (i in 1 until 10) {
                val number = page * 10
                add(Foo(number +i, "이름 ${number + i}", "01000000000", imgUriList.get(page + i - 1)))
                Log.d(TAG, "createSamples 할 때 뷰홀더에 찍은 이미지 Uri: " + imgUriList.get(page + 1 - 1))
            }
        }
    }
}