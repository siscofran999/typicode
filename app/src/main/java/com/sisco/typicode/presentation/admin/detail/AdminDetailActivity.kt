package com.sisco.typicode.presentation.admin.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.sisco.typicode.R
import com.sisco.typicode.databinding.ActivityAdminDetailBinding
import com.sisco.typicode.domain.model.User
import com.sisco.typicode.presentation.base.BaseVBActivity
import com.sisco.typicode.utils.parcelable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminDetailActivity : BaseVBActivity<ActivityAdminDetailBinding>() {

    override fun getViewBinding(): ActivityAdminDetailBinding =
        ActivityAdminDetailBinding.inflate(layoutInflater)

    override fun init(savedInstanceState: Bundle?) {
        intent.parcelable<User>(EXTRA_DATA_ADMIN)?.let { data ->
            binding.apply {
                initMenu(data.role)
                textFieldUsername.editText?.setText(data.username)
                textFieldEmail.editText?.setText(data.email)
            }
        }
    }

    private fun initMenu(role: String?) {
        val items = listOf(getString(R.string.admin), getString(R.string.user))
        val adapter = ArrayAdapter(this, R.layout.list_role, items)
        binding.menuAutoComplete.setAdapter(adapter)
        binding.menuAutoComplete.setText(role, false)
    }

    companion object {
        private const val EXTRA_DATA_ADMIN = "extra_data_admin"
        fun newInstance(context: Context, data: User): Intent {
            val intent = Intent(context, AdminDetailActivity::class.java)
            intent.putExtra(EXTRA_DATA_ADMIN, data)
            return intent
        }
    }
}