package com.example.myapplication.recyclerview

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sample.R
import com.example.sample.databinding.ItemPickerBinding


class PickerAdapter(
    private val a: Int,
    private val onItemClick: (PickerItem) -> Unit
) : ListAdapter<PickerItem, PickerAdapter.ViewHolder>(diffCallback) {

    val dimList = mutableListOf<Uri>()



    private val selectImageAdapter by lazy {
        SelectImageAdapter {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPickerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }




    // 모든 뷰홀더를 비우는 함수
    fun clearAllViewHolders(recyclerView: RecyclerView) {

        // 세번째 비움 방법
        val emptyMutableList = mutableListOf<PickerItem>()
        submitList(emptyMutableList)
        //

    }

    fun addItems(newItems : List<PickerItem>) {
        submitList(newItems)

        // submitList 로 데이터를 넣는다
        // 데이터 순서 처리 같은 것은 알아서 다 해줌
    }

    fun removeItems(){
        try {
            Log.d(ContentValues.TAG, "removeItems: 스피너 바뀌면서 내용 비웠어요~")
            currentList.toMutableList().apply {
                removeAt(0)
            }.let ( ::submitList )
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "removeItems: 암것도 없어서 안지웠어용~")
        }

    }



    inner class ViewHolder(
        private val binding: ItemPickerBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PickerItem) {
            binding.apply {
/*                tvImgUri.text = item.imgUriName
                *//*tvFolderName.text = item.imgFolderName*//*
                tvFolderName.text = a.toString()*/
                if(item.isChecked){
                    tvNumber.visibility = View.VISIBLE
                    tvNumber.setText(item.selectedNumber.toString())
                }else{
                    tvNumber.visibility = View.GONE
                }

                Glide.with(itemView)
                    .load(item.imgUri)
                    .into(ivPicture)
                fLayout.setOnClickListener {
                    onItemClick(item)
                }


            }
        }


        // 뷰홀더를 비우는 함수
        fun onViewRecycled() {
            // 리소스 해제 또는 기타 작업 수행
        }

    }


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<PickerItem>() {
            override fun areItemsTheSame(oldItem: PickerItem, newItem: PickerItem): Boolean {
                return oldItem.imgUri == newItem.imgUri
            }

            override fun areContentsTheSame(oldItem: PickerItem, newItem: PickerItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}