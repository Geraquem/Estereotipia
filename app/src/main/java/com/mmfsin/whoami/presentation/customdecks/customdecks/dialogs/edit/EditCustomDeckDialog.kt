package com.mmfsin.whoami.presentation.customdecks.customdecks.dialogs.edit

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseDialog
import com.mmfsin.whoami.databinding.DialogCreateDeckNameBinding
import com.mmfsin.whoami.presentation.customdecks.customdecks.dialogs.CustomDeckEvent
import com.mmfsin.whoami.presentation.customdecks.customdecks.dialogs.CustomDeckViewModel
import com.mmfsin.whoami.presentation.customdecks.customdecks.interfaces.ICustomDeckListener
import com.mmfsin.whoami.utils.animateDialog
import com.mmfsin.whoami.utils.countDown
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCustomDeckDialog(private val customDeckId: String, val listener: ICustomDeckListener) :
    BaseDialog<DialogCreateDeckNameBinding>() {

    private val viewModel: CustomDeckViewModel by viewModels()

    private var firstAccess = true

    override fun inflateView(inflater: LayoutInflater) =
        DialogCreateDeckNameBinding.inflate(inflater)

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
        binding.apply {
            tvTitle.text = getString(R.string.custom_decks_dialog_edit_name)
            btnAccept.text = getString(R.string.custom_decks_dialog_edit)
        }
    }

    override fun setListeners() {
        binding.apply {
            btnAccept.setOnClickListener {
                val name = etName.text.toString()
                if (name.isNotEmpty() && name.isNotBlank()) {
                    countDown(300) { viewModel.editCustomDeckName(customDeckId, name) }
                } else {
                    tilName.error = getString(R.string.custom_decks_create_new_name_error)
                    tilName.isErrorEnabled = true
                }
            }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is CustomDeckEvent.GetDeck -> {
                    binding.apply {
                        val name = event.deck.name
                        etName.setText(name)
                    }
                }

                is CustomDeckEvent.EditedCompleted -> {
                    listener.editCompleted()
                    dismiss()
                }

                is CustomDeckEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog(goBack = false)

    companion object {
        fun newInstance(customDeckId: String, listener: ICustomDeckListener): EditCustomDeckDialog {
            return EditCustomDeckDialog(customDeckId, listener)
        }
    }
}