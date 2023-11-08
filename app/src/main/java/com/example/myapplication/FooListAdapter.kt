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
import android.net.Uri
import android.view.View
import android.view.View.OnLongClickListener
import com.example.sample.databinding.ItemSelectedimgBinding


class FooListAdapter(
    private val a: Int,
    private val onItemClick: (Foo) -> Unit
) : ListAdapter<Foo, FooListAdapter.ViewHolder>(diffCallback) {

    val dimList = mutableListOf<Uri>()



    private val selectedImgAdapter by lazy {
        SelectedImgAdapter {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFooBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val binding2 = ItemSelectedimgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, binding2)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }



    fun clearRecyclerView(){


        notifyDataSetChanged()
    }

    // 모든 뷰홀더를 비우는 함수
    fun clearAllViewHolders(recyclerView: RecyclerView) {
        Log.d(TAG, "clearAllViewHolders: 이거 작동함?")

        // 첫번째 비움 방법
        currentList.toMutableList().clear() // 데이터셋을 비움
        notifyDataSetChanged()
        for (item in currentList) {
            Log.d("MutableListContents", item.toString())
        }

        // 두번째 비움 방법
        currentList.toMutableList().apply {
            removeAll(this)
        }.let(::submitList)
        notifyDataSetChanged()
        for (item in currentList) {
            Log.d("MutableListContents2", item.toString())
        }

        // 세번째 비움 방법
        val emptyMutableList = mutableListOf<Foo>()
        submitList(emptyMutableList)
        notifyDataSetChanged()
        for (item in currentList) {
            Log.d("MutableListContents3", item.toString())
        }

        clearRecyclerView()



        notifyDataSetChanged() // 변경된 데이터셋을 RecyclerView에 알림

        // RecyclerView에 적용된 뷰홀더를 모두 제거
        for (i in 0 until recyclerView.childCount) {
            val viewHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i))
            if (viewHolder is ViewHolder) {
                viewHolder.onViewRecycled() // 각 뷰홀더에서 onViewRecycled()를 호출하여 리소스 해제 등의 작업 수행
            }
        }
        recyclerView.removeAllViews() // RecyclerView의 모든 뷰를 제거
    }

    fun addItems(newItems : List<Foo>) {
        // submitList 로 데이터를 넣는다
        // 데이터 순서 처리 같은 것은 알아서 다 해줌
        currentList.toMutableList().apply {
            addAll(newItems)
        }.let(::submitList)
    }

    fun removeItems(){
        try {
            Log.d(TAG, "removeItems: 스피너 바뀌면서 내용 비웠어요~")
            currentList.toMutableList().apply {
                removeAt(0)
            }.let ( ::submitList )
        } catch (e: Exception) {
            Log.d(TAG, "removeItems: 암것도 없어서 안지웠어용~")
        }

    }



    inner class ViewHolder(
        private val binding: ItemFooBinding,
        private val binding2: ItemSelectedimgBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Foo) {
            binding2.apply {
                Glide.with(itemView)
                    .load(item.imgUri)
                    .into(ivSelectedPicture)
            }

            binding.apply {
                tvImgUri.text = item.imgUriName
                /*tvFolderName.text = item.imgFolderName*/
                tvFolderName.text = a.toString()
                Glide.with(itemView)
                    .load(item.imgUri)
                    .into(ivPicture)

                ivCheckBox.setOnClickListener {
                    Log.d(TAG, "bind: 클릭이벤트 오긴 하나?")
                    onItemClick(item)
                    Log.d(TAG, "bind: 클릭이벤트 오긴 하나?22222")
                    if(item.isChecked){
                        item.isChecked = false
                        ivCheckBox.setImageResource(R.drawable.unchecked)
                        Log.d(TAG, "클릭 테스트 : 언체크드로 바껴라바껴라 바껴라")
                        dimList.remove(item.imgUri)


                    }else{
                        item.isChecked = true
                        ivCheckBox.setImageResource(R.drawable.checked)
                        Log.d(TAG, "클릭 테스트 : 체크드로 바껴라바껴라 바껴라")

                        dimList.add(item.imgUri)
                        Log.d(TAG, "선택한 이미지 유알아ㅣ 출력 : " + item.imgUri)
                        /*selectedImgAdapter.addItems(SelectedImg.createSamples(dimList))*/
                    }
                }
            }
        }




        // 뷰홀더를 비우는 함수
        fun onViewRecycled() {
            // 리소스 해제 또는 기타 작업 수행
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
