package com.sisco.typicode.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.sisco.typicode.R
import com.sisco.typicode.databinding.ActivityMainBinding
import com.sisco.typicode.domain.model.User
import com.sisco.typicode.presentation.base.BaseVBActivity
import com.sisco.typicode.presentation.login.LoginActivity
import com.sisco.typicode.utils.parcelable
import com.sisco.typicode.utils.showLoading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseVBActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var mainPagingAdapter: MainPagingAdapter

    override fun getViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun init(savedInstanceState: Bundle?) {
        intent.parcelable<User>(EXTRA_DATA_USER)?.let { data ->
            binding.tvUsername.text = getString(R.string.hello_admin, data.username)
            binding.tvLogout.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                onBackPressCallback()
            }
            binding.rv.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = mainPagingAdapter
            }
            requestPhotos()
        }
    }

    private fun requestPhotos() {
        lifecycleScope.launch {
            viewModel.getPhotos2.collect {
                mainPagingAdapter.addLoadStateListener { loadState ->
                    when {
                        loadState.prepend is LoadState.Error -> {
                            loadState.prepend as LoadState.Error
                        }
                        loadState.append is LoadState.Error -> {
                            loadState.append as LoadState.Error
                        }
                        loadState.refresh is LoadState.Error -> {
                            loadState.refresh as LoadState.Error
                        }
                    }
                }
                mainPagingAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainPagingAdapter.loadStateFlow.collect {
                    Log.i("TAG", "requestPhotos: source -> ${it.source}")
                    binding.progressBar.showLoading(it.source.refresh is LoadState.Loading)
                    binding.bottomProgress.showLoading(it.source.append is LoadState.Loading)
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