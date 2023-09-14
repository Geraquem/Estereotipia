package com.mmfsin.whoami.presentation.dialogs.selected

import android.app.Dialog
import android.os.CountDownTimer
import android.view.LayoutInflater
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogWaitSelectBinding
import com.mmfsin.whoami.utils.animateDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WaitSelectDialog(val dialogFinished: () -> Unit) : BaseDialog<DialogWaitSelectBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogWaitSelectBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun setUI() {
        isCancelable = false
        binding.apply {
            tvCountdown.text = "4"
            object : CountDownTimer(100, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    tvCountdown.text = (millisUntilFinished / 1000).toString()
                }

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