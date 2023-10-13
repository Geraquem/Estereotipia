package com.mmfsin.whoami.presentation.decks.mydecks.dialogs.delete

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogDeleteMyDeckBinding
import com.mmfsin.whoami.presentation.decks.mydecks.dialogs.MyDeckEvent
import com.mmfsin.whoami.presentation.decks.mydecks.dialogs.MyDeckViewModel
import com.mmfsin.whoami.presentation.decks.mydecks.interfaces.IMyDeckListener
import com.mmfsin.whoami.utils.animateDialog
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteMyDeckDialog(private val myDeckId: String, val listener: IMyDeckListener) :
    BaseDialog<DialogDeleteMyDeckBinding>() {

    private val viewModel: MyDeckViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater) = DialogDeleteMyDeckBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getMyDeck(myDeckId)
    }

    override fun setUI() {
        isCancelable = true
        binding.apply { }
    }

    override fun setListeners() {
        binding.apply {
            btnNo.setOnClickListener { dismiss() }
            btnYes.setOnClickListener {
                listener.deleteMyDeck(myDeckId)
                dismiss()
            }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MyDeckEvent.GetDeck -> binding.tvText.text =
                    getString(R.string.my_decks_dialog_delete_confirm, event.deck.name)
                is MyDeckEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog(goBack = false)

    companion object {
        fun newInstance(myDeckId: String, listener: IMyDeckListener): DeleteMyDeckDialog {
            return DeleteMyDeckDialog(myDeckId, listener)
        }
    }
}