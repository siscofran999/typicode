package com.sisco.typicode.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sisco.typicode.R
import com.sisco.typicode.utils.DataState
import com.sisco.typicode.databinding.ActivityLoginBinding
import com.sisco.typicode.presentation.admin.AdminActivity
import com.sisco.typicode.presentation.base.BaseVBActivity
import com.sisco.typicode.presentation.main.MainActivity
import com.sisco.typicode.presentation.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseVBActivity<ActivityLoginBinding>() {

    private var flagEmail: Boolean = false
    private var flagPass: Boolean = false
    private val viewModel: LoginViewModel by viewModels()

    override fun getViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun init(savedInstanceState: Bundle?) {
        initListener()
        lifecycleScope.launch {
            viewModel.login.flowWithLifecycle(lifecycle).collectLatest { state ->
                when(state) {
                    is DataState.Loading -> {}
                    is DataState.Error -> {
                        Toast.makeText(this@LoginActivity, getString(R.string.failed_login), Toast.LENGTH_SHORT).show()
                    }
                    is DataState.Success -> {
                        if (state.data.role?.lowercase() == getString(R.string.admin).lowercase()) {
                            startActivity(AdminActivity.newInstance(this@LoginActivity, state.data))
                            finish()
                        }else {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun initListener() {
        binding.apply {
            edtEmail.addTextChangedListener {
                flagEmail = it?.isNotEmpty() == true
                btnLogin.isEnabled = checkButton()
            }
            edtPass.addTextChangedListener {
                flagPass = it?.isNotEmpty() == true
                btnLogin.isEnabled = checkButton()
            }
            btnLogin.setOnClickListener {
                viewModel.setLogin(edtEmail.text.toString(), edtPass.text.toString())
            }
            tvRegister.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun checkButton(): Boolean {
        return flagEmail && flagPass
    }
}