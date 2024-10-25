package com.mmfsin.estereotipia.presentation.dashboard.whoiswho.cards.dialogs.wait

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.estereotipia.base.BaseDialog
import com.mmfsin.estereotipia.databinding.DialogWaitSelectBinding
import com.mmfsin.estereotipia.utils.animateDialog
import com.mmfsin.estereotipia.utils.countDown
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
            countDown(1200) {
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