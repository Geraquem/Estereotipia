package com.mmfsin.estereotipia.presentation.dashboard.identities.dialogs.initial

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.estereotipia.base.BaseDialog
import com.mmfsin.estereotipia.databinding.DialogInitialIdentitiesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitialIdentitiesDialog(val listener: IInitialListener) :
    BaseDialog<DialogInitialIdentitiesBinding>() {

    override fun inflateView(inflater: LayoutInflater) =
        DialogInitialIdentitiesBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerCustomViewDialog(dialog)

    override fun setUI() {
        listener.startGame()
        dismiss()
    }

    override fun setListeners() {
        binding.apply {
            tvInstructions.setOnClickListener {
                listener.openInstructions()
                dismiss()
            }
            tvContinue.setOnClickListener {
                listener.startGame()
                dismiss()
            }
        }
    }
}