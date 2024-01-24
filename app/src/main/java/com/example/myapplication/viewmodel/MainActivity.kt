package com.example.myapplication.viewmodel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    private val sp = getSharedPreferences("sample", Context.MODE_PRIVATE)

    fun insert() {
        val editor = sp.edit()
            editor.putString("key", "value")
            editor.apply()
        }


    fun select() : String?{
        return sp.getString("key", "defaultValue")
    }

    fun delete(){
        val editor = sp.edit()
        editor.remove("key")
        editor.apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        binding.lifecycleOwner = this

        initView()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // StateFlow
        lifecycleScope.launch{
            viewModel.state.collectLatest{

            }
        }

        // LiveData
        viewModel.liveState.observe(this){

        }
    }

    private fun initView(){
        binding.apply{
            /*textView.text="123"
            textView.setOnClickListener{
                textView.text = "456"*/
            }
        }
    }



