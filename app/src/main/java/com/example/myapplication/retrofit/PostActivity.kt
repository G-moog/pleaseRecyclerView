package com.example.myapplication.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.retrofit.data.Result
import com.example.myapplication.retrofit.data.RetrofitInstance
import com.example.sample.BuildConfig
import com.example.sample.R
import com.example.sample.databinding.ActivityMainBinding
import com.example.sample.databinding.ActivityPostBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class PostActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPostBinding
    private lateinit var viewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post)

        viewModel = ViewModelProvider(this)[PostViewModel::class.java]


        viewModel.apply {
            findAll()

            lifecycleScope.launch{
                viewModel.sampleState.collect{
                    when(it){
                        is Result.Empty -> TODO()
                        is Result.Error -> TODO()
                        is Result.Fail -> TODO()
                        is Result.Loading -> TODO()
                        is Result.Success -> it.data
                    }
                }

                state.collectLatest{
                    it.forEach{ post ->
                        Log.e("####","post $post")
                    }
                }
            }
        }

        /*CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitInstance.postService.findAll(mapOf())

                withContext(Dispatchers.Main){
                    if(response.isSuccessful){
                        Log.e("####", "onCreate: ${response.body()}")
                    } else {

                    }
                }
            } catch (e : Exception){
                e.printStackTrace()

            }
        }*/

        BuildConfig.VERSION_CODE
    }
}