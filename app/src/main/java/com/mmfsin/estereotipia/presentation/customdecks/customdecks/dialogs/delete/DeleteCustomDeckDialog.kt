package com.mmfsin.estereotipia.presentation.customdecks.customdecks.dialogs.delete

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseDialog
import com.mmfsin.estereotipia.databinding.DialogDeleteCustomDeckBinding
import com.mmfsin.estereotipia.presentation.customdecks.customdecks.dialogs.CustomDeckEvent
import com.mmfsin.estereotipia.presentation.customdecks.customdecks.dialogs.CustomDeckViewModel
import com.mmfsin.estereotipia.presentation.customdecks.customdecks.interfaces.ICustomDeckListener
import com.mmfsin.estereotipia.utils.animateDialog
import com.mmfsin.estereotipia.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteCustomDeckDialog(private val customDeckId: String, val listener: ICustomDeckListener) :
    BaseDialog<DialogDeleteCustomDeckBinding>() {

    private val viewModel: CustomDeckViewModel by viewModels()

    private var firstAccess = true

    override fun inflateView(inflater: LayoutInflater) =
        DialogDeleteCustomDeckBinding.inflate(inflater)

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
        viewModel.getCustomDeck(customDeckId)
    }

    override fun setUI() {
        isCancelable = true
        binding.apply { }
    }

    override fun setListeners() {
        binding.apply {
            btnNo.setOnClickListener { dismiss() }
            btnYes.setOnClickListener {
                listener.deleteCustomDeck(customDeckId)
                dismiss()
            }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is CustomDeckEvent.GetDeck -> binding.tvText.text =
                    getString(R.string.custom_decks_dialog_delete_confirm, event.deck.name)

                is CustomDeckEvent.EditedCompleted -> {}
                is CustomDeckEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog(goBack = false)

    companion object {
        fun newInstance(customDeckId: String, listener: ICustomDeckListener): DeleteCustomDeckDialog {
            return DeleteCustomDeckDialog(customDeckId, listener)
        }
    }
}