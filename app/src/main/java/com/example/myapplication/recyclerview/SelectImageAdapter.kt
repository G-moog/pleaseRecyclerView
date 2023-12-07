package com.example.myapplication.recyclerview

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sample.databinding.ItemSelectImageBinding


class SelectImageAdapter(
    private val onItemClick: (SelectImage) -> Unit
) : ListAdapter<SelectImage, SelectImageAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSelectImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun addItems(newItem : SelectImage) {
        // submitList 로 데이터를 넣는다
        // 데이터 순서 처리 같은 것은 알아서 다 해줌
        currentList.toMutableList().apply {
            add(newItem)
        }.distinct().let(::submitList)
    }

    fun addItems2(newItems : List<SelectImage>) {
        submitList(newItems)

        // submitList 로 데이터를 넣는다
        // 데이터 순서 처리 같은 것은 알아서 다 해줌
    }

    inner class ViewHolder(
        private val binding: ItemSelectImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SelectImage) {
            binding.apply {
                Glide.with(itemView)
                    .load(item.imgUri)
                    .into(ivSelectedPicture)
                Log.d(ContentValues.TAG, "바인드 되는 이미지 Uri :" + item.imgUri)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SelectImage>() {
            override fun areItemsTheSame(oldItem: SelectImage, newItem: SelectImage): Boolean {
                return oldItem.imgUri == newItem.imgUri
            }

            override fun areContentsTheSame(oldItem: SelectImage, newItem: SelectImage): Boolean {
                return oldItem == newItem
            }
        }
    }
}