package com.example.myapplication.recyclerview

import android.content.ContentValues
import android.content.ContentValues.TAG
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

    private val selectImageList = mutableListOf<SelectImage>()

        //선택한 아이템 상단에 보이게 하기
    private fun actionOnClick(pickerItem: PickerItem){
        val curPickerItemList = pickerAdapter.currentList.toMutableList()
        val curSelectImageList = selectImageAdapter.currentList.toMutableList()

        if(pickerItem.isChecked){

            Log.d(TAG, "actionOnClick: 해제!!")

            selectImageList.remove(SelectImage(pickerItem.imgUri))
            val submitSelectImageList = curSelectImageList.apply {
                remove(SelectImage(pickerItem.imgUri))
            }.distinct()
            selectImageAdapter.addItems2(submitSelectImageList)

            if(submitSelectImageList.isNotEmpty()){
                submitSelectImageList.forEach{submitSelectImage ->
                    curPickerItemList.forEach{curPickerItem ->
                        if(submitSelectImageList.indexOf(SelectImage(curPickerItem.imgUri)) > -1){
                            curPickerItem.isChecked = true
                            curPickerItem.selectedNumber = submitSelectImageList.indexOf(submitSelectImage) + 1
                        }else if(curPickerItemList.indexOf(pickerItem) > -1){
                            curPickerItem.isChecked = false
                            curPickerItem.selectedNumber = 0
                        }
                    }
                }
            }else{
                var selectedIndex : Int = curPickerItemList.indexOf(pickerItem)
                if(selectedIndex > -1){
                    curPickerItemList[selectedIndex].isChecked = false
                    curPickerItemList[selectedIndex].selectedNumber = 0
                }
            }

            curPickerItemList.forEach{
                Log.d(TAG, "삭제할 때 아이템 출력: $it")
            }

            pickerAdapter.notifyDataSetChanged()
            pickerAdapter.addItems(curPickerItemList)
            pickerAdapter.notifyDataSetChanged()

        // 선택한 pickerItem의 imgUri가 썸네일 리사이클러뷰의 아이템에 없다면
        }else if(!pickerItem.isChecked){

            Log.d(TAG, "actionOnClick: 선택!!")

            /*선택한 pickerItem을 썸네일 리사이클러뷰에 넣는 과정 (S)*/
            val submitSelectImageList = curSelectImageList.apply {
                add(SelectImage(pickerItem.imgUri))
            }.distinct()
            selectImageAdapter.addItems2(submitSelectImageList)

            selectImageAdapter.currentList.toMutableList().forEach{
                Log.d(TAG, "썸네일 아이템 추가하고 전체 출력 : $it")
            }

            curSelectImageList.forEach{curSelectImage ->
                curPickerItemList.forEach{curPickerItem ->
                    if(curSelectImageList.indexOf(curSelectImage) > -1){
                        curPickerItem.isChecked = true
                        curPickerItem.selectedNumber = curSelectImageList.indexOf(curSelectImage) + 1
                    }
                }
            }
            pickerAdapter.addItems(curPickerItemList)
        }
            pickerAdapter.notifyDataSetChanged()
            selectImageAdapter.notifyDataSetChanged()

            pickerAdapter.currentList.forEach{
                Log.d(TAG, "피커아이템 전체 출력: $it")
            }
    }

    private val folderNameList = mutableListOf<String>()
    private var page = 0

    private val pickerItemList = mutableListOf<PickerItem>()

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
                pickerItemList.add(PickerItem(folderName, imageUri, false, 0))

                // 얘네로 PickerItem List를 생성
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

                // 선택한 폴더의 아이템 추가
                pickerAdapter.addItems(
                    pickerItemList.filter {
                        it.imgFolderName == selectedItem
                    }
                )
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}