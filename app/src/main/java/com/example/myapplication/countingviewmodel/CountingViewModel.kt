package com.example.myapplication.countingviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CountingViewModel : ViewModel() {

    private val _state = MutableStateFlow(0)
    val state: StateFlow<Int> get() = _state.asStateFlow()

    fun plusState() {
        // it 은 state.value

        _state.update { it + 1 }
    }

    fun minusState() {
        // it 은 state.value
        _state.update { it - 1 }
    }

    // LiveData 의 value 는 nullable 이기 때문에 null 처리 필요
    private val _liveState = MutableLiveData(0)
    val liveState: LiveData<Int> get() = _liveState

    fun plusLiveState() {
        _liveState.value = (liveState.value ?: 0) + 1
    }

    fun minusLiveState() {
        _liveState.value = (liveState.value ?: 0) - 1
    }
}