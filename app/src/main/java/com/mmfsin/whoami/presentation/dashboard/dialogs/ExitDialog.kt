package com.mmfsin.whoami.presentation.dashboard.dialogs

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogExitBinding

class ExitDialog(val action: () -> Unit) : BaseDialog<DialogExitBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogExitBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun setUI() {
        isCancelable = true
    }

    override fun setListeners() {
        binding.apply {
            btnStay.setOnClickListener { dismiss() }
            btnExit.setOnClickListener {
                action()
                dismiss()
            }
        }
    }
}