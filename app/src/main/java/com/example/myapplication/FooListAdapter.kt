package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.databinding.ItemFooBinding


class FooListAdapter(
    private val onItemClick: (Foo) -> Unit
) : ListAdapter<Foo, FooListAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFooBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun addItems(newItems : List<Foo>) {
        // submitList 로 데이터를 넣는다
        // 데이터 순서 처리 같은 것은 알아서 다 해줌
        currentList.toMutableList().apply {
            addAll(newItems)
        }.let(::submitList)
    }

    inner class ViewHolder(
        private val binding: ItemFooBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Foo) {
            binding.apply {
                tvName.text = item.name
                tvPhone.text = item.phone

                itemView.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Foo>() {
            override fun areItemsTheSame(oldItem: Foo, newItem: Foo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Foo, newItem: Foo): Boolean {
                return oldItem == newItem
            }
        }
    }
}
