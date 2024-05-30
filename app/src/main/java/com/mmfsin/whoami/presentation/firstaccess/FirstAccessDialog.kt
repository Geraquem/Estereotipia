package com.mmfsin.whoami.presentation.firstaccess

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogCardSelectBinding
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.utils.animateDialog
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstAccessDialog : BaseDialog<DialogCardSelectBinding>() {

    private var firstAccess = true

    override fun inflateView(inflater: LayoutInflater) = DialogCardSelectBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = bottomViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        if (firstAccess) {
            firstAccess = false
            requireDialog().animateDialog()
        }
    }

    override fun setUI() {
        isCancelable = true
    }

    override fun setListeners() {
        binding.apply {
            btnOk.setOnClickListener { dismiss() }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(): FirstAccessDialog {
            return FirstAccessDialog()
        }
    }
}