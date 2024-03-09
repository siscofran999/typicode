package com.sisco.typicode.presentation.bottomsheet

import android.os.Bundle
import android.widget.Toast
import com.sisco.typicode.R
import com.sisco.typicode.databinding.BottomSheetVerificationBinding
import com.sisco.typicode.domain.model.User
import com.sisco.typicode.utils.parcelable

class BottomSheetVerification :
    BaseBottomSheet<BottomSheetVerification, BottomSheetVerificationBinding>() {

    companion object {
        private const val EXTRA_DATA_ADMIN = "extra_data_admin"

        fun newInstance(dataAdmin: User?) =
            BottomSheetVerification().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_DATA_ADMIN, dataAdmin)
                }
            }
    }

    private var dataAdmin: User? = null
    private var callback: OnDeleteCallback? = null

    override fun getViewBinding(): BottomSheetVerificationBinding =
        BottomSheetVerificationBinding.inflate(layoutInflater)

    override fun setup() {
        dataAdmin = arguments?.parcelable<User>(EXTRA_DATA_ADMIN)
        binding.apply {
            btnDelete.setOnClickListener {
                if (edtPass.text.toString() == dataAdmin?.password) {
                    bottomSheetDialog.dismiss()
                    callback?.onDelete()
                } else {
                    Toast.makeText(activity, getString(R.string.wrong_password), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    fun setOnDeleteCallback(onDeleteCallback: OnDeleteCallback) {
        this.callback = onDeleteCallback
    }

    fun interface OnDeleteCallback {
        fun onDelete()
    }

    override val initialize: BottomSheetVerification
        get() = this
}