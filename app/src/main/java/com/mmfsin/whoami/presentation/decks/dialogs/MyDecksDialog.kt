package com.mmfsin.whoami.presentation.decks.dialogs

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogMyDecksBinding
import com.mmfsin.whoami.presentation.decks.interfaces.IMyDecksListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyDecksDialog(private val listener: IMyDecksListener) : BaseDialog<DialogMyDecksBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogMyDecksBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = bottomViewDialog(dialog)

    override fun setUI() {
        isCancelable = true
    }

    override fun setListeners() {
        binding.apply {
            tvMyDecks.setOnClickListener {
                listener.openMyDecks()
                dismiss()
            }
            tvNewDeck.setOnClickListener {
                listener.createDeck()
                dismiss()
            }
        }
    }
}