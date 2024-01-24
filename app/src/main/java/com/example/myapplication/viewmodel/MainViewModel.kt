package com.example.myapplication.viewmodel

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sample.R
import com.example.sample.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel(){

    private val _liveState = MutableLiveData<String>("")
    val liveState = _liveState

    private val _state = MutableStateFlow<String>("")
    val state = _state.asStateFlow()


}