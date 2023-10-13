package com.mmfsin.whoami.presentation.decks.mydecks.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogMyDeckBinding
import com.mmfsin.whoami.presentation.decks.mydecks.interfaces.IMyDeckListener
import com.mmfsin.whoami.utils.animateDialog
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyDeckDialog(private val myDeckId: String, val listener: IMyDeckListener) :
    BaseDialog<DialogMyDeckBinding>() {

    private val viewModel: MyDeckViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater) = DialogMyDeckBinding.inflate(inflater)

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
            tvPlay.setOnClickListener { }

            tvEditName.setOnClickListener { actionAndDismiss { listener.editName(myDeckId) } }
            tvEditCards.setOnClickListener { }
            tvShare.setOnClickListener { }
            tvDelete.setOnClickListener { actionAndDismiss { listener.confirmDeleteMyDeck(myDeckId) } }
        }
    }

    private fun actionAndDismiss(action: () -> Unit) {
        action()
        dismiss()
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MyDeckEvent.GetDeck -> binding.tvTitle.text = event.deck.name
                is MyDeckEvent.EditedCompleted -> {}
                is MyDeckEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog(goBack = false)

    companion object {
        fun newInstance(myDeckId: String, listener: IMyDeckListener): MyDeckDialog {
            return MyDeckDialog(myDeckId, listener)
        }
    }
}