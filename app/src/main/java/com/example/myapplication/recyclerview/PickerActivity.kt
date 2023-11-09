package com.example.myapplication.recyclerview

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.databinding.ActivityPickerBinding


class PickerActivity : AppCompatActivity() {

    lateinit var binding: ActivityPickerBinding

    private val pickerAdapter by lazy {
        PickerAdapter(3) { pickerItem: PickerItem -> actionOnClick(pickerItem) }
    }


    private val selectImageAdapter by lazy {
        SelectImageAdapter {

        }
    }

    private fun actionOnClick(pickerItem: PickerItem){
        Log.d(ContentValues.TAG, "actionOnClick: 여기서 클릭이벤트시 할 액션을 만든다.")
        selectImageAdapter.addItems(SelectImage(pickerItem.id, pickerItem.imgUri))

    }




    val folderNameList = mutableListOf<String>()
    val imageUriList = mutableListOf<Uri>()
    val selectedImgIndexList = mutableListOf<Uri>()
    val selectedFolderList = mutableListOf<String>()
    val dimList = mutableListOf<Uri>()
    var page = 0

    var isChecked :Boolean = false


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
            Log.d(ContentValues.TAG, "findAllDeviceImage: asdfasdfasdf")
            while (moveToNext()) {
                Log.d(ContentValues.TAG, "findAllDeviceImage: 와일문 들어오나?")
                // 폴더명
                val folderName = getString(folderNameColumn)
                // 이미지 Uri
                val imageUri = Uri.withAppendedPath(externalUri, "" + getLong(columnIndexID))

                folderNameList.add(folderName)
                imageUriList.add(imageUri)
                Log.d(ContentValues.TAG, folderName)
                Log.d(ContentValues.TAG, imageUri.toString())
            }
            close()
        }
    }
    fun RecyclerView.onScrollAction(
        doSomething: () -> Unit,
    ) {
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = (recyclerView.adapter?.itemCount ?: 0) - 1

                if (itemTotalCount > 0 &&
                    lastVisibleItemPosition == itemTotalCount &&
                    !recyclerView.canScrollVertically(1)
                ) {
                    doSomething()
                }
            }
        })
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(/* activity = */ this, /* layoutId = */
            R.layout.activity_picker
        )

        val layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.layoutManager = layoutManager


        findAllDeviceImage()

        // lateinit
        // fooAdapter = FooAdapter {}
        binding.recyclerView.adapter = pickerAdapter
        binding.selectedImgRecyclerView.adapter = selectImageAdapter

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
                        selectedFolderList.add(tempIndex, folderNameList.get(i))
                        tempIndex += 1;
                    }
                }
                pickerAdapter.clearRecyclerView()
                pickerAdapter.notifyDataSetChanged()
                pickerAdapter.clearAllViewHolders(binding.recyclerView)
                pickerAdapter.removeItems()


                pickerAdapter.addItems(PickerItem.createSamples(page, selectedImgIndexList, selectedFolderList))
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.recyclerView.onScrollAction {
            page += 1
            pickerAdapter.addItems(PickerItem.createSamples(page,selectedImgIndexList, selectedFolderList))
        }

    }
}