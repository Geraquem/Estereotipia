package com.mmfsin.estereotipia.presentation.firstaccess

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.estereotipia.base.BaseDialog
import com.mmfsin.estereotipia.databinding.DialogFirstAccessBinding
import com.mmfsin.estereotipia.presentation.firstaccess.interfaces.IFirstAccessListener
import com.mmfsin.estereotipia.utils.animateDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstAccessDialog(
    val listener: IFirstAccessListener
) : BaseDialog<DialogFirstAccessBinding>() {

    private var firstAccess = true

    override fun inflateView(inflater: LayoutInflater) = DialogFirstAccessBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

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
            llInstructions.setOnClickListener {
                listener.firstAccessOpenInstructions()
                dismiss()
            }
            llClose.setOnClickListener { dismiss() }
        }
    }

    companion object {
        fun newInstance(listener: IFirstAccessListener): FirstAccessDialog {
            return FirstAccessDialog(listener)
        }
    }
}