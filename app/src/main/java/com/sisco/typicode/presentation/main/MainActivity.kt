package com.sisco.typicode.presentation.main

import android.os.Bundle
import android.widget.Toast
import com.sisco.typicode.databinding.ActivityMainBinding
import com.sisco.typicode.presentation.base.BaseVBActivity

class MainActivity : BaseVBActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun init(savedInstanceState: Bundle?) {
        Toast.makeText(this, "ini user", Toast.LENGTH_SHORT).show()
    }
}