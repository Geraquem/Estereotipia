package com.mmfsin.whoami.presentation.dialogs.instructions

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogInstQuestionsBinding
import com.mmfsin.whoami.utils.animateDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstQuestionsDialog : BaseDialog<DialogInstQuestionsBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogInstQuestionsBinding.inflate(inflater)

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
        fun newInstance(): InstQuestionsDialog {
            return InstQuestionsDialog()
        }
    }
}