package com.mmfsin.whoami.presentation.customdecks.customdecks.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogCustomDeckBinding
import com.mmfsin.whoami.presentation.customdecks.customdecks.interfaces.ICustomDeckListener
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomDeckDialog(private val customDeckId: String, val listener: ICustomDeckListener) :
    BaseDialog<DialogCustomDeckBinding>() {

    private val viewModel: CustomDeckViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater) = DialogCustomDeckBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = bottomViewDialog(dialog)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getCustomDeck(customDeckId)
    }

    override fun setUI() {
        isCancelable = true
    }

    override fun setListeners() {
        binding.apply {
            ivClose.setOnClickListener { dismiss() }

            tvPlay.setOnClickListener { actionAndDismiss { listener.playWithCustomDeck(customDeckId) } }

            tvEditName.setOnClickListener { actionAndDismiss { listener.editName(customDeckId) } }

            tvEditCards.setOnClickListener { }

            tvShare.setOnClickListener { }

            tvDelete.setOnClickListener {
                actionAndDismiss {
                    listener.confirmDeleteCustomDeck(
                        customDeckId
                    )
                }
            }
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
}