package com.sisco.typicode.presentation.admin

import android.os.Bundle
import android.widget.Toast
import com.sisco.typicode.databinding.ActivityAdminBinding
import com.sisco.typicode.presentation.base.BaseVBActivity

class AdminActivity : BaseVBActivity<ActivityAdminBinding>() {

    override fun getViewBinding(): ActivityAdminBinding =
        ActivityAdminBinding.inflate(layoutInflater)

    override fun init(savedInstanceState: Bundle?) {
        Toast.makeText(this, "ini admin", Toast.LENGTH_SHORT).show()
    }
}