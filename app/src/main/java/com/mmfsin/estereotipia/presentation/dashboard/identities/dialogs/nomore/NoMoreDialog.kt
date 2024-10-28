package com.mmfsin.estereotipia.presentation.dashboard.identities.dialogs.nomore

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.estereotipia.base.BaseDialog
import com.mmfsin.estereotipia.databinding.DialogNoMoreIdentitiesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoMoreDialog(private val endGame: () -> Unit) : BaseDialog<DialogNoMoreIdentitiesBinding>() {

    override fun inflateView(inflater: LayoutInflater) =
        DialogNoMoreIdentitiesBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerCustomViewDialog(dialog)

    override fun setUI() {
        isCancelable = false
    }

    override fun setListeners() {
        binding.btnAccept.setOnClickListener {
            endGame()
            dismiss()
        }
    }
}