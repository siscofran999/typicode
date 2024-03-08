package com.sisco.typicode.presentation.register

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.sisco.typicode.R
import com.sisco.typicode.databinding.ActivityRegisterBinding
import com.sisco.typicode.domain.model.User
import com.sisco.typicode.presentation.base.BaseVBActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseVBActivity<ActivityRegisterBinding>() {

    private var flagUsername: Boolean = false
    private var flagEmail: Boolean = false
    private var flagPass: Boolean = false
    private var flagRole: Boolean = false
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun getViewBinding(): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun init(savedInstanceState: Bundle?) {
        initMenu()
        initListener()
    }

    private fun initListener() {
        binding.apply {
            edtUsername.addTextChangedListener {
                flagUsername = it?.isNotEmpty() == true
                btnRegister.isEnabled = checkButton()
            }
            edtEmail.addTextChangedListener {
                flagEmail = it?.isNotEmpty() == true
                btnRegister.isEnabled = checkButton()
            }
            edtPass.addTextChangedListener {
                flagPass = it?.isNotEmpty() == true
                btnRegister.isEnabled = checkButton()
            }
            binding.menuAutoComplete.setOnItemClickListener { _, _, _, _ ->
                flagRole = binding.menuAutoComplete.text.isNotEmpty()
                btnRegister.isEnabled = checkButton()
            }
            btnRegister.setOnClickListener {
                val user = User(
                    username = edtUsername.text.toString(),
                    email = edtEmail.text.toString(),
                    password = edtPass.text.toString(),
                    role = binding.menuAutoComplete.text.toString()
                )
                registerViewModel.insertUser(user)
                Toast.makeText(this@RegisterActivity, getString(R.string.success_register), Toast.LENGTH_SHORT)
                    .show()
                onBackPressCallback()
            }
        }
    }

    private fun checkButton(): Boolean {
        return flagUsername && flagEmail && flagPass && flagRole
    }

    private fun initMenu() {
        val items = listOf(getString(R.string.user), getString(R.string.admin))
        val adapter = ArrayAdapter(this, R.layout.list_role, items)
        binding.menuAutoComplete.setAdapter(adapter)
    }
}