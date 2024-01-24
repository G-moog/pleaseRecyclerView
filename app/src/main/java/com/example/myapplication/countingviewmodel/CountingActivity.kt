package com.example.myapplication.countingviewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.databinding.ActivityCountingBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CountingActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCountingBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[CountingViewModel::class.java]
    }

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_counting)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        initView()

        // xml에서 선언해놔서 주석해도 됨
        // /*initViewModel()*/
    }

    private fun initView() {
        binding.apply {
/*            bLocalMinus.setOnClickListener { tvLocalCount.text = "${count--}" }
            bLocalPlus.setOnClickListener { tvLocalCount.text = "${count++}" }*/

            bFlowMinus.setOnClickListener { viewModel.minusState() }
            bFlowPlus.setOnClickListener { viewModel.plusState() }

            bLiveMinus.setOnClickListener { viewModel.minusLiveState() }
            bLivePlus.setOnClickListener { viewModel.plusLiveState() }
        }
    }

    private fun initViewModel() {
        viewModel.apply {
            // stateFlow 데이터 수신, 코루틴 안에서 수신한다.
            lifecycleScope.launch { // 코루틴 스코프
                state.collectLatest {
                    binding.tvFlowCount.text = "$it"
                }
            }

            // liveData 데이터 수신
            // fragment 에서는 this@CountingActivity 대신 viewLifecycleOwner
            liveState.observe(this@CountingActivity) {
                binding.tvLiveCount.text = "$it"
            }
        }
    }
}