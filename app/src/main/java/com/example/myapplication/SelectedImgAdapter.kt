package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.databinding.ItemSelectedImgBinding

class SelectedImgAdapter(
    private val onItemClick: (SelectedImg) -> Unit
) : ListAdapter<SelectedImg, SelectedImgAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSelectedImgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun addItems(newItems : List<SelectedImg>) {
        // submitList 로 데이터를 넣는다
        // 데이터 순서 처리 같은 것은 알아서 다 해줌
        currentList.toMutableList().apply {
            addAll(newItems)
        }.let(::submitList)
    }

    inner class ViewHolder(
        private val binding: ItemSelectedImgBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SelectedImg) {
            binding.apply {

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