package com.mmfsin.whoami.presentation.dashboard.tpm.dialog

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogTpmSelectBinding
import com.mmfsin.whoami.utils.animateDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TpmSelectDialog : BaseDialog<DialogTpmSelectBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogTpmSelectBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun setUI() {
        isCancelable = false
        binding.apply { }
    }

    override fun setListeners() {
        binding.apply {
            btnContinue.setOnClickListener { dismiss() }
        }
    }

    companion object {
        fun newInstance(): TpmSelectDialog {
            return TpmSelectDialog()
        }
    }
}