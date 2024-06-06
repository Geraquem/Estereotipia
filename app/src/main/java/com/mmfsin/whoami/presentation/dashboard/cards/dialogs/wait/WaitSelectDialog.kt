package com.mmfsin.whoami.presentation.dashboard.cards.dialogs.wait

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogWaitSelectBinding
import com.mmfsin.whoami.utils.animateDialog
import com.mmfsin.whoami.utils.countDown
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WaitSelectDialog(val dialogFinished: () -> Unit) : BaseDialog<DialogWaitSelectBinding>() {

    private var firstAccess = true

    override fun inflateView(inflater: LayoutInflater) = DialogWaitSelectBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerCustomViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        if (firstAccess) {
            firstAccess = false
            requireDialog().animateDialog()
        }
    }

    override fun setUI() {
        isCancelable = false
        binding.apply {
            countDown(750) {
                dialogFinished()
                dismiss()
            }
        }
    }

    companion object {
        fun newInstance(dialogFinished: () -> Unit): WaitSelectDialog {
            return WaitSelectDialog(dialogFinished)
        }
    }
}