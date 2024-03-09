package com.sisco.typicode.presentation.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sisco.typicode.R
import com.sisco.typicode.databinding.ActivityAdminBinding
import com.sisco.typicode.domain.model.User
import com.sisco.typicode.presentation.admin.detail.AdminDetailActivity
import com.sisco.typicode.presentation.base.BaseVBActivity
import com.sisco.typicode.utils.parcelable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AdminActivity : BaseVBActivity<ActivityAdminBinding>() {

    private val viewModel: AdminViewModel by viewModels()

    @Inject
    lateinit var adminAdapter: AdminAdapter

    override fun getViewBinding(): ActivityAdminBinding =
        ActivityAdminBinding.inflate(layoutInflater)

    override fun init(savedInstanceState: Bundle?) {
        initListener()
        intent.parcelable<User>(EXTRA_DATA_ADMIN)?.let { data ->
            binding.tvUsername.text = getString(R.string.hello_admin, data.username)
            binding.rv.apply {
                layoutManager = LinearLayoutManager(this@AdminActivity)
                adapter = adminAdapter
            }
            lifecycleScope.launch {
                viewModel.getUserForAdmin(data.email ?: "").flowWithLifecycle(lifecycle)
                    .collect { list ->
                        adminAdapter.submitList(list)
                    }
            }
        }
    }

    private fun initListener() {
        adminAdapter.setOnItemClickCallback { user ->
            startActivity(AdminDetailActivity.newInstance(this, user))
        }
    }

    companion object {
        private const val EXTRA_DATA_ADMIN = "extra_data_admin"
        fun newInstance(context: Context, data: User): Intent {
            val intent = Intent(context, AdminActivity::class.java)
            intent.putExtra(EXTRA_DATA_ADMIN, data)
            return intent
        }
    }
}