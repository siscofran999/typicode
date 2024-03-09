package com.sisco.typicode.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sisco.typicode.R
import com.sisco.typicode.databinding.ActivityMainBinding
import com.sisco.typicode.domain.model.User
import com.sisco.typicode.presentation.base.BaseVBActivity
import com.sisco.typicode.utils.DataState
import com.sisco.typicode.utils.parcelable
import com.sisco.typicode.utils.showLoading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseVBActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var mainAdapter: MainAdapter

    override fun getViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun init(savedInstanceState: Bundle?) {
        intent.parcelable<User>(EXTRA_DATA_USER)?.let { data ->
            binding.tvUsername.text = getString(R.string.hello_admin, data.username)
            binding.rv.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = mainAdapter
            }
            requestPhotos()
        }
    }

    private fun requestPhotos() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getPhotos.collect { state ->
                        when(state) {
                            is DataState.Loading -> {
                                binding.progressBar.showLoading(true)
                            }
                            is DataState.Success -> {
                                binding.progressBar.showLoading(false)
                                mainAdapter.submitList(state.data)
                            }
                            is DataState.Error -> {
                                binding.progressBar.showLoading(false)
                                Toast.makeText(this@MainActivity, "error -> ${state.errorMessage}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val EXTRA_DATA_USER = "extra_data_user"
        fun newInstance(context: Context, data: User): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(EXTRA_DATA_USER, data)
            return intent
        }
    }
}