package com.sisco.typicode.presentation.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.annotation.StyleRes
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sisco.typicode.R

abstract class BaseBottomSheet<T : BottomSheetDialogFragment, VB : ViewBinding>(
    @StyleRes private val customTheme: Int = R.style.CustomBottomSheetDialogTheme,
    private val bottomSheetBehaviorState: Int = BottomSheetBehavior.STATE_EXPANDED
) : BottomSheetDialogFragment() {
    protected lateinit var bottomSheetDialog: BottomSheetDialog

    protected lateinit var binding: VB
    private lateinit var behaviour: BottomSheetBehavior<*>

    protected abstract fun getViewBinding(): VB
    protected abstract fun setup()
    protected abstract val initialize: T

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        context?.let {
            bottomSheetDialog = BottomSheetDialog(it, customTheme)
            binding = getViewBinding()
            bottomSheetDialog.setContentView(binding.root)
            behaviour = BottomSheetBehavior.from(binding.root.parent as View)
        }
        return bottomSheetDialog
    }

    override fun onStart() {
        super.onStart()
        behaviour.state = bottomSheetBehaviorState
        setup()
    }

    fun build(fragmentManager: FragmentManager): T {
        showNow(fragmentManager, this.tag)
        return this.initialize
    }
}