package com.sisco.typicode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.ArrayAdapter
import com.sisco.typicode.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMenu()
    }

    private fun initMenu() {
        val items = listOf(getString(R.string.user), getString(R.string.admin))
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        binding.menuAutoComplete.setAdapter(adapter)
    }
}