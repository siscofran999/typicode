package com.sisco.typicode.presentation.admin.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.sisco.typicode.R
import com.sisco.typicode.databinding.ActivityAdminDetailBinding
import com.sisco.typicode.domain.model.User
import com.sisco.typicode.presentation.admin.AdminViewModel
import com.sisco.typicode.presentation.base.BaseVBActivity
import com.sisco.typicode.presentation.bottomsheet.BottomSheetVerification
import com.sisco.typicode.utils.parcelable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdminDetailActivity : BaseVBActivity<ActivityAdminDetailBinding>() {

    private var dataAdmin: User? = null
    private var dataUser: User? = null
    private var bsVerification: BottomSheetVerification? = null
    private val viewModel: AdminViewModel by viewModels()

    override fun getViewBinding(): ActivityAdminDetailBinding =
        ActivityAdminDetailBinding.inflate(layoutInflater)

    override fun init(savedInstanceState: Bundle?) {
        dataAdmin = intent.parcelable<User>(EXTRA_DATA_ADMIN)
        dataUser = intent.parcelable<User>(EXTRA_DATA_USER)
        initMenu()
        initListener()
        initBs()
        binding.apply {
            textFieldUsername.editText?.setText(dataUser?.username)
            textFieldEmail.editText?.setText(dataUser?.email)
        }
    }

    private fun initBs() {
        bsVerification = BottomSheetVerification.newInstance(dataAdmin)
        bsVerification?.setOnDeleteCallback {
            dataUser?.let {
                viewModel.deleteUser(it)
            }
            onBackPressCallback()
            Toast.makeText(this, getString(R.string.success_delete), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initListener() {
        binding.apply {
            btnDelete.setOnClickListener {
                bsVerification?.build(supportFragmentManager)
            }
        }
    }

    private fun initMenu() {
        val items = listOf(getString(R.string.admin), getString(R.string.user))
        val adapter = ArrayAdapter(this, R.layout.list_role, items)
        binding.menuAutoComplete.setAdapter(adapter)
        binding.menuAutoComplete.setText(dataUser?.role, false)
    }

    companion object {
        private const val EXTRA_DATA_ADMIN = "extra_data_admin"
        private const val EXTRA_DATA_USER = "extra_data_user"
        fun newInstance(context: Context, dataUser: User, dataAdmin: User): Intent {
            val intent = Intent(context, AdminDetailActivity::class.java)
            intent.putExtra(EXTRA_DATA_ADMIN, dataAdmin)
            intent.putExtra(EXTRA_DATA_USER, dataUser)
            return intent
        }
    }
}