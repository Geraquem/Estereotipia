package com.mmfsin.estereotipia.presentation.exit

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.estereotipia.base.BaseDialog
import com.mmfsin.estereotipia.databinding.DialogExitBinding

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