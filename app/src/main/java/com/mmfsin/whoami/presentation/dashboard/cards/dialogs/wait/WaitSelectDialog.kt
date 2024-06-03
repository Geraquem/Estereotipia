package com.mmfsin.whoami.presentation.dashboard.cards.dialogs.wait

import android.app.Dialog
import android.os.CountDownTimer
import android.view.LayoutInflater
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogWaitSelectBinding
import com.mmfsin.whoami.utils.animateDialog
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
            object : CountDownTimer(750, 1000) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    dialogFinished()
                    dismiss()
                }
            }.start()
        }
    }

    override fun setListeners() {
        binding.apply {}
    }

    companion object {
        fun newInstance(dialogFinished: () -> Unit): WaitSelectDialog {
            return WaitSelectDialog(dialogFinished)
        }
    }
}