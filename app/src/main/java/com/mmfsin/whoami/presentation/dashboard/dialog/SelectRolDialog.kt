package com.mmfsin.whoami.presentation.dashboard.dialog

import android.app.Dialog
import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogSelectRolBinding
import com.mmfsin.whoami.presentation.decks.DecksFragmentDirections.Companion.actionDeckToDashboard

class SelectRolDialog(val deckId: String) : BaseDialog<DialogSelectRolBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogSelectRolBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun setUI() {
        isCancelable = true
    }

    override fun setListeners() {
        binding.apply {
            tvCaptain.setOnClickListener { dismiss() }
            tvPeople.setOnClickListener {
                findNavController().navigate(actionDeckToDashboard(deckId))
                dismiss()
            }
        }
    }
}