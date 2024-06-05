package com.mmfsin.whoami.presentation.customdecks.customdecks.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogCustomDeckBinding
import com.mmfsin.whoami.presentation.customdecks.customdecks.interfaces.ICustomDeckListener
import com.mmfsin.whoami.utils.animateDialog
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomDeckDialog(private val myDeckId: String, val listener: ICustomDeckListener) :
    BaseDialog<DialogCustomDeckBinding>() {

    private val viewModel: CustomDeckViewModel by viewModels()

    private var firstAccess = true

    override fun inflateView(inflater: LayoutInflater) = DialogCustomDeckBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        if (firstAccess) {
            firstAccess = false
            requireDialog().animateDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getCustomDeck(myDeckId)
    }

    override fun setUI() {
        isCancelable = true
    }

    override fun setListeners() {
        binding.apply {
            tvPlay.setOnClickListener { actionAndDismiss { listener.playWithCustomDeck(myDeckId) } }
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
                is CustomDeckEvent.GetDeck -> binding.tvTitle.text = event.deck.name
                is CustomDeckEvent.EditedCompleted -> {}
                is CustomDeckEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog(goBack = false)

    companion object {
        fun newInstance(myDeckId: String, listener: ICustomDeckListener): CustomDeckDialog {
            return CustomDeckDialog(myDeckId, listener)
        }
    }
}