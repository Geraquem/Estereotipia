package com.mmfsin.whoami.base.dialog

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogErrorBinding

class ErrorDialog : BaseDialog<DialogErrorBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogErrorBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun setListeners() {
        binding.btnAccept.setOnClickListener {
            activity?.onBackPressed()
            dismiss()
        }
    }
}