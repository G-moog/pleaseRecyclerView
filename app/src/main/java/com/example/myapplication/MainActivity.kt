package com.example.myapplication

import android.content.ContentValues.TAG
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sample.R
import com.example.sample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val fooListAdapter by lazy {
        FooListAdapter {

        }
    }

    val folderNameList = mutableListOf<String>()
    val imageUriList = mutableListOf<Uri>()
    val selectedImgIndexList = mutableListOf<Uri>()




    private fun findAllDeviceImage() {
        val externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media._ID)
        contentResolver.query(
            externalUri,
            projection,
            null,
            null,
            "${MediaStore.Video.VideoColumns.DATE_ADDED} DESC"
        )?.apply { // this : Cursor
            val folderNameColumn = getColumnIndexOrThrow(projection[0])
            val columnIndexID = getColumnIndexOrThrow(projection[1])
            Log.d(TAG, "findAllDeviceImage: asdfasdfasdf")
            while (moveToNext()) {
                Log.d(TAG, "findAllDeviceImage: 와일문 들어오나?")
                // 폴더명
                val folderName = getString(folderNameColumn)
                // 이미지 Uri
                val imageUri = Uri.withAppendedPath(externalUri, "" + getLong(columnIndexID))

                folderNameList.add(folderName)
                imageUriList.add(imageUri)
                Log.d(TAG, folderName)
                Log.d(TAG, imageUri.toString())
            }

            close()

        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(/* activity = */ this, /* layoutId = */ R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        findAllDeviceImage()


        // lateinit
        // fooAdapter = FooAdapter {}
        binding.recyclerView.adapter = fooListAdapter

        /*fooAdapter.addItems(PickerItem.createSamples(0, "Camera", folderNameList, imageUriList))*/


        val spinnerList = folderNameList.distinct()

        binding.spinner.adapter = ArrayAdapter(
            this, // context
            android.R.layout.simple_spinner_item, // 기본
            spinnerList // items
        )



        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = spinnerList[position]
                Log.d("응애응애", "뱉어내라 목록: $selectedItem")

                for(i in 1 until folderNameList.size){
                    var tempIndex = 0;
                    if(folderNameList.get(i) == selectedItem){
                        selectedImgIndexList.add(tempIndex, imageUriList.get(i));
                        tempIndex += 1;
                    }

                }
                fooListAdapter.clearAllItems()
                fooListAdapter.addItems(Foo.createSamples(0, selectedImgIndexList))

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}