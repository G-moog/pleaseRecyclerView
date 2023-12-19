package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myapplication.recyclerview.PickerActivity
import com.example.myapplication.webview.WebViewActivity
import com.example.sample.R
import com.example.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(/* activity = */ this, /* layoutId = */
            R.layout.activity_main
        )

        binding.btnRecyclerview.setOnClickListener{
            val intent = Intent(this, PickerActivity::class.java)
            startActivity(intent)
        }
        binding.btnFragment.setOnClickListener{
            val intent = Intent(this, PickerActivity::class.java)
            startActivity(intent)
        }
        binding.btnViewpage2.setOnClickListener{
            val intent = Intent(this, PickerActivity::class.java)
            startActivity(intent)
        }
        binding.btnWebview.setOnClickListener{
            val intent = Intent(this, WebViewActivity::class.java)
            startActivity(intent)
        }

    }
}