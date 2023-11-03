package com.example.myapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sample.R
import com.example.sample.databinding.ItemFooBinding
import android.content.ContentValues.TAG


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
                tvImgUri.text = item.imgUriName
                tvFolderName.text = item.imgFolderName
                Glide.with(itemView)
                    .load(item.imgUri)
                    .into(ivPicture)

                itemView.setOnClickListener {
                    onItemClick(item)

                    if(item.isChecked){
                        item.isChecked = false
                        ivCheckBox.setImageDrawable(R.drawable.unchecked)
                        Log.d(TAG, "클릭 테스트 : 언체크드로 바껴라바껴라 바껴라")
                    }else{
                        item.isChecked = true
                        ivCheckBox.setImageDrawable(R.drawable.checked)
                        Log.d(TAG, "클릭 테스트 : 체크드로 바껴라바껴라 바껴라")
                    }
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

private fun ImageView.setImageDrawable(unchecked: Int) {

}
