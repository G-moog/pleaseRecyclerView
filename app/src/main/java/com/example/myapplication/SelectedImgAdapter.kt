package com.example.myapplication

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sample.databinding.ItemSelectedimgBinding


class SelectedImgAdapter(
    private val onItemClick: (SelectedImg) -> Unit
) : ListAdapter<SelectedImg, SelectedImgAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSelectedimgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun addItems(newItem : SelectedImg) {
        // submitList 로 데이터를 넣는다
        // 데이터 순서 처리 같은 것은 알아서 다 해줌
        currentList.toMutableList().apply {
            add(newItem)
        }.let(::submitList)
    }

    inner class ViewHolder(
        private val binding: ItemSelectedimgBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SelectedImg) {
            binding.apply {
                Glide.with(itemView)
                    .load(item.imgUri)
                    .into(ivSelectedPicture)
                Log.d(TAG, "바인드 되는 이미지 Uri :" + item.imgUri)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SelectedImg>() {
            override fun areItemsTheSame(oldItem: SelectedImg, newItem: SelectedImg): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SelectedImg, newItem: SelectedImg): Boolean {
                return oldItem == newItem
            }
        }
    }
}