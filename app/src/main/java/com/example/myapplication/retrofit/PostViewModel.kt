package com.example.myapplication.retrofit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.retrofit.data.PostRepository
import com.example.myapplication.retrofit.data.Result
import com.example.myapplication.retrofit.data.RetrofitInstance
import com.example.myapplication.retrofit.data.response.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class PostViewModel : ViewModel() {

    private val _state = MutableStateFlow(mutableListOf<Post>())
    val state = _state.asStateFlow()

    fun findAll() = viewModelScope.launch{
        try{
            val response = RetrofitInstance.postService.findAll(mapOf())

            if(response.isSuccessful){
                Log.e("####", "onCreate: ${response.body()}")
                val list = response.body()?.content ?: emptyList()

                _state.value = list.toMutableList()

                //_state.update{
                  //  it.addAll(list)
                    //it
                //}
            } else {

            }

        } catch (e : Exception){
            e.printStackTrace()

        }


    }

    private val _sampleState = MutableStateFlow<Result<String>>(Result.Empty())
    val sampleState = _sampleState.asStateFlow()

    fun sample() = viewModelScope.launch{
        PostRepository().findAll().onEach{
            _sampleState.value = it
           /* when(it){
                is Result.Error -> TODO()
                is Result.Fail -> TODO()
                is Result.Loading -> TODO()
                is Result.Success -> TODO()
            }*/

        }.launchIn(this)// this = viewModelScope
    }
}